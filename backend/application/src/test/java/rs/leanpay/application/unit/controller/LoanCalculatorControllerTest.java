package rs.leanpay.application.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rs.leanpay.application.controller.LoanCalculatorController;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.mapper.SimpleLoanMapper;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.application.util.ApplicationContextProvider;
import rs.leanpay.application.util.SimpleLoanCalculatorTestDataUtil;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebMvcTest(value = LoanCalculatorController.class)
public class LoanCalculatorControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    LoanCalculatorService loanCalculatorService;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
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
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "rs.leanpay.application", excludeFilters = {
            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
    static class TestConfig {
    }

}
