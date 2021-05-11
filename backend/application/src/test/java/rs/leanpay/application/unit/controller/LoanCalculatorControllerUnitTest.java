package rs.leanpay.application.unit.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rs.leanpay.application.controller.LoanCalculatorController;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.dto.MonthlyAmortization;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.exception.InterestRateException;
import rs.leanpay.application.exception.util.LoanCalculatorErrorCode;
import rs.leanpay.application.exception.NumberOfPeriodException;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.application.util.AmortizationScheduleCalculatorTestDataUtil;
import rs.leanpay.application.util.MonthlyAmortizationTestDataUtil;
import rs.leanpay.model.enumeration.LoanTermType;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:messages.properties"})
@ContextConfiguration
@WebMvcTest(value = LoanCalculatorController.class)
public class LoanCalculatorControllerUnitTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Environment env;

    @MockBean
    LoanCalculatorService loanCalculatorService;

    MockMvc mockMvc;

    AmortizationScheduleCalculatorTestDataUtil amortizationScheduleCalculatorTestDataUtil;
    MonthlyAmortizationTestDataUtil monthlyAmortizationTestDataUtil;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        amortizationScheduleCalculatorTestDataUtil = new AmortizationScheduleCalculatorTestDataUtil();
        monthlyAmortizationTestDataUtil = new MonthlyAmortizationTestDataUtil();
    }

    @Test
    public void simpleLoanCalculatorTest() throws Exception {
        SimpleLoanResponse simpleLoanResponse =
                SimpleLoanResponse.builder().monthlyPayment(336.99).totalInterestPaid(1011.33).build();

        when(loanCalculatorService.simpleLoanCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class)))
                .thenReturn(simpleLoanResponse);

        this.mockMvc.perform(get("/loan/simple-loan-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "6.00")
                .param("loanTerm", "36")
                .param("loanTermType", LoanTermType.years.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("monthlyPayment").isNumber())
                .andExpect(jsonPath("monthlyPayment").value(simpleLoanResponse.getMonthlyPayment()))
                .andExpect(jsonPath("totalInterestPaid").isNumber())
                .andExpect(jsonPath("totalInterestPaid").value(simpleLoanResponse.getTotalInterestPaid()));

        verify(loanCalculatorService, times(1)).simpleLoanCalculator(any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class));
    }

    @Test
    public void simpleLoanCalculatorTestInterestRateException() throws Exception {

        when(loanCalculatorService.simpleLoanCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class)))
                .thenThrow(new InterestRateException(LoanCalculatorErrorCode.ERR_001));

        this.mockMvc.perform(get("/loan/simple-loan-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "-6.00")
                .param("loanTerm", "36")
                .param("loanTermType", LoanTermType.years.name()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageCode").value(LoanCalculatorErrorCode.ERR_001.name()))
                .andExpect(jsonPath("message").value(env.getProperty(LoanCalculatorErrorCode.ERR_001.getMessageKey().name())));

        verify(loanCalculatorService, times(1)).simpleLoanCalculator(any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class));
    }

    @Test
    public void simpleLoanCalculatorTestNumberOfPeriodException() throws Exception {

        when(loanCalculatorService.simpleLoanCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class)))
                .thenThrow(new NumberOfPeriodException(LoanCalculatorErrorCode.ERR_002));

        this.mockMvc.perform(get("/loan/simple-loan-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "6.00")
                .param("loanTerm", "-36")
                .param("loanTermType", LoanTermType.years.name()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageCode").value(LoanCalculatorErrorCode.ERR_002.name()))
                .andExpect(jsonPath("message").value(env.getProperty(LoanCalculatorErrorCode.ERR_002.getMessageKey().name())));

        verify(loanCalculatorService, times(1)).simpleLoanCalculator(any(Double.class), any(Double.class), any(Integer.class), any(LoanTermType.class));
    }

    @Test
    public void findSimpleLoanByLoanTermTypeTest() throws Exception {
        List<SimpleLoanResponse> simpleLoanResponseList = new ArrayList<>();
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(336.99).totalInterestPaid(1011.01).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(337.99).totalInterestPaid(1011.02).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(338.99).totalInterestPaid(1011.03).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(339.99).totalInterestPaid(1011.04).build());

        when(loanCalculatorService.findSimpleLoanByLoanTermType(any(LoanTermType.class)))
                .thenReturn(simpleLoanResponseList);

        this.mockMvc.perform(get("/loan/simple-loan-calculator/loanTerm")
                .param("loanTermType", "years"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$[0].monthlyPayment", is(simpleLoanResponseList.get(0).getMonthlyPayment())))
                .andExpect(jsonPath("$[0].totalInterestPaid", is(simpleLoanResponseList.get(0).getTotalInterestPaid())))
                .andExpect(jsonPath("$[1].monthlyPayment", is(simpleLoanResponseList.get(1).getMonthlyPayment())))
                .andExpect(jsonPath("$[1].totalInterestPaid", is(simpleLoanResponseList.get(1).getTotalInterestPaid())))
                .andExpect(jsonPath("$[2].monthlyPayment", is(simpleLoanResponseList.get(2).getMonthlyPayment())))
                .andExpect(jsonPath("$[2].totalInterestPaid", is(simpleLoanResponseList.get(2).getTotalInterestPaid())))
                .andExpect(jsonPath("$[3].monthlyPayment", is(simpleLoanResponseList.get(3).getMonthlyPayment())))
                .andExpect(jsonPath("$[3].totalInterestPaid", is(simpleLoanResponseList.get(3).getTotalInterestPaid())));

        verify(loanCalculatorService, times(1)).findSimpleLoanByLoanTermType(any(LoanTermType.class));
    }

    @Test
    public void findByLoanAmountGraterThen() throws Exception {
        List<SimpleLoanResponse> simpleLoanResponseList = new ArrayList<>();
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(336.99).totalInterestPaid(1011.01).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(337.99).totalInterestPaid(1011.02).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(338.99).totalInterestPaid(1011.03).build());
        simpleLoanResponseList.add(SimpleLoanResponse.builder().monthlyPayment(339.99).totalInterestPaid(1011.04).build());

        when(loanCalculatorService.findSimpleLoanByLoanAmountGraterThen(any(Double.class)))
                .thenReturn(simpleLoanResponseList);

        this.mockMvc.perform(get("/loan/simple-loan-calculator/1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$[0].monthlyPayment", is(simpleLoanResponseList.get(0).getMonthlyPayment())))
                .andExpect(jsonPath("$[0].totalInterestPaid", is(simpleLoanResponseList.get(0).getTotalInterestPaid())))
                .andExpect(jsonPath("$[1].monthlyPayment", is(simpleLoanResponseList.get(1).getMonthlyPayment())))
                .andExpect(jsonPath("$[1].totalInterestPaid", is(simpleLoanResponseList.get(1).getTotalInterestPaid())))
                .andExpect(jsonPath("$[2].monthlyPayment", is(simpleLoanResponseList.get(2).getMonthlyPayment())))
                .andExpect(jsonPath("$[2].totalInterestPaid", is(simpleLoanResponseList.get(2).getTotalInterestPaid())))
                .andExpect(jsonPath("$[3].monthlyPayment", is(simpleLoanResponseList.get(3).getMonthlyPayment())))
                .andExpect(jsonPath("$[3].totalInterestPaid", is(simpleLoanResponseList.get(3).getTotalInterestPaid())));

        verify(loanCalculatorService, times(1)).findSimpleLoanByLoanAmountGraterThen(any(Double.class));
    }

    @Test
    public void amortizationScheduleCalculatorTest() throws Exception {

        AmortizationScheduleResponse amortizationScheduleResponse =
                AmortizationScheduleResponse.builder().totalPayments(33336.99).totalInterest(3336.99).build();

        List<MonthlyAmortization> monthlyAmortizationList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            monthlyAmortizationList.add(MonthlyAmortization.builder()
                    .paymentAmount(400d - i)
                    .principalAmount(330d + i)
                    .interestAmount(70d - i)
                    .balanceOwed(330d - i).build());
        }

        amortizationScheduleResponse.setAmortizations(monthlyAmortizationList);

        when(loanCalculatorService.amortizationScheduleCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class)))
                .thenReturn(amortizationScheduleResponse);

        this.mockMvc.perform(get("/loan/amortization-schedule-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "6.00")
                .param("numberOfPayments", "36")
                .param("paymentFrequency", PaymentFrequencyType.Monthly.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalPayments").isNumber())
                .andExpect(jsonPath("totalPayments").value(amortizationScheduleResponse.getTotalPayments()))
                .andExpect(jsonPath("totalInterest").isNumber())
                .andExpect(jsonPath("totalInterest").value(amortizationScheduleResponse.getTotalInterest()))
                .andExpect(jsonPath("amortizations").isArray())
                .andExpect(jsonPath("amortizations", hasSize(monthlyAmortizationList.size())));

        verify(loanCalculatorService, times(1)).amortizationScheduleCalculator(any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class));
    }

    @Test
    public void amortizationScheduleCalculatorTestInterestRateException() throws Exception {

        when(loanCalculatorService.amortizationScheduleCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class)))
                .thenThrow(new InterestRateException(LoanCalculatorErrorCode.ERR_001));

        this.mockMvc.perform(get("/loan/amortization-schedule-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "6.00")
                .param("numberOfPayments", "36")
                .param("paymentFrequency", PaymentFrequencyType.Monthly.name()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageCode").value(LoanCalculatorErrorCode.ERR_001.name()))
                .andExpect(jsonPath("message").value(env.getProperty(LoanCalculatorErrorCode.ERR_001.getMessageKey().name())));

        verify(loanCalculatorService, times(1)).amortizationScheduleCalculator(any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class));
    }

    @Test
    public void amortizationScheduleCalculatorTestNumberOfPeriodException() throws Exception {

        when(loanCalculatorService.amortizationScheduleCalculator(
                any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class)))
                .thenThrow(new NumberOfPeriodException(LoanCalculatorErrorCode.ERR_002));

        this.mockMvc.perform(get("/loan/amortization-schedule-calculator")
                .param("loanAmount", "20000.00")
                .param("interestRate", "6.00")
                .param("numberOfPayments", "36")
                .param("paymentFrequency", PaymentFrequencyType.Monthly.name()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageCode").value(LoanCalculatorErrorCode.ERR_002.name()))
                .andExpect(jsonPath("message").value(env.getProperty(LoanCalculatorErrorCode.ERR_002.getMessageKey().name())));

        verify(loanCalculatorService, times(1)).amortizationScheduleCalculator(any(Double.class), any(Double.class), any(Integer.class), any(PaymentFrequencyType.class));
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "rs.leanpay.application", excludeFilters = {
            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
    static class TestConfig {
    }

}
