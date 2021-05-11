package rs.leanpay.application.unit.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.exception.InterestRateException;
import rs.leanpay.application.exception.util.LoanCalculatorErrorCode;
import rs.leanpay.application.exception.NumberOfPeriodException;
import rs.leanpay.application.repository.AmortizationScheduleRepository;
import rs.leanpay.application.repository.SimpleLoanRepository;
import rs.leanpay.application.service.impl.LoanCalculatorServiceImpl;
import rs.leanpay.application.util.AmortizationScheduleCalculatorTestDataUtil;
import rs.leanpay.application.util.MonthlyAmortizationTestDataUtil;
import rs.leanpay.application.util.SimpleLoanCalculatorTestDataUtil;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanCalculatorServiceUnitTest {

    @Mock
    private SimpleLoanRepository simpleLoanRepository;

    @Mock
    private AmortizationScheduleRepository amortizationScheduleRepository;

    @InjectMocks
    private LoanCalculatorServiceImpl loanCalculatorService;

    SimpleLoanCalculatorTestDataUtil simpleLoanCalculatorTestDataUtil;
    AmortizationScheduleCalculatorTestDataUtil amortizationScheduleCalculatorTestDataUtil;
    MonthlyAmortizationTestDataUtil monthlyAmortizationTestDataUtil;

    @Before
    public void setUp() {
        simpleLoanCalculatorTestDataUtil = new SimpleLoanCalculatorTestDataUtil();
        amortizationScheduleCalculatorTestDataUtil = new AmortizationScheduleCalculatorTestDataUtil();
        monthlyAmortizationTestDataUtil = new MonthlyAmortizationTestDataUtil();
    }

    @Test
    public void simpleLoanCalculatorTest() {
        SimpleLoanEntity simpleLoanEntityWithId = simpleLoanCalculatorTestDataUtil.simpleLoanEntity1000();

        when(simpleLoanRepository.save(any(SimpleLoanEntity.class))).thenReturn(simpleLoanEntityWithId);

        SimpleLoanResponse simpleLoanResponse = loanCalculatorService.simpleLoanCalculator(1000.00, 3.00, 3, LoanTermType.months);

        verify(simpleLoanRepository, times(1)).save(any(SimpleLoanEntity.class));
        assertThat(simpleLoanResponse).isNotNull();
        Assert.assertEquals(335.00, simpleLoanResponse.getMonthlyPayment(), 0.00);
        Assert.assertEquals(5.00, simpleLoanResponse.getTotalInterestPaid(), 0.00);
    }

    @Test(expected = InterestRateException.class)
    public void simpleLoanCalculatorTestInterestRateException() {
        loanCalculatorService.simpleLoanCalculator(1000.00, -3.00, 3, LoanTermType.months);
    }

    @Test(expected = NumberOfPeriodException.class)
    public void simpleLoanCalculatorTestNumberOfPeriodException() {
        loanCalculatorService.simpleLoanCalculator(1000.00, 3.00, -3, LoanTermType.months);
    }

    @Test
    public void amortizationShceduleCalculatorTest() {
        AmortizationScheduleEntity amortizationScheduleEntity = amortizationScheduleCalculatorTestDataUtil.amortizationScheduleEntityWithId16000();
        amortizationScheduleEntity.addMonthlyAmortizationList(monthlyAmortizationTestDataUtil.monthlyAmortizationEntityWithIdList(12));

        when(amortizationScheduleRepository.save(any(AmortizationScheduleEntity.class))).thenReturn(amortizationScheduleEntity);

        AmortizationScheduleResponse amortizationScheduleResponse = loanCalculatorService.amortizationScheduleCalculator(16000d, 9d, 12, PaymentFrequencyType.Monthly);

        verify(amortizationScheduleRepository, times(1)).save(any(AmortizationScheduleEntity.class));
        assertThat(amortizationScheduleResponse).isNotNull();
        Assert.assertEquals(16790.68, amortizationScheduleResponse.getTotalPayments(), 0.00);
        Assert.assertEquals(790.68, amortizationScheduleResponse.getTotalInterest(), 0.00);
        Assert.assertEquals(12, amortizationScheduleResponse.getAmortizations().size());
    }

    @Test
    public void amortizationShceduleCalculatorTestInterestRateException() {
        InterestRateException ex = assertThrows(InterestRateException.class, () -> loanCalculatorService.amortizationScheduleCalculator(1000.00, -3.00, 3, PaymentFrequencyType.Monthly));
        assertEquals(LoanCalculatorErrorCode.ERR_001.name(), ex.getErrorCode().name());
    }

    @Test
    public void amortizationShceduleCalculatorTestNumberOfPeriodException() {
        NumberOfPeriodException ex = assertThrows(NumberOfPeriodException.class, () -> loanCalculatorService.amortizationScheduleCalculator(1000.00, 3.00, -3, PaymentFrequencyType.Monthly));
        assertEquals(LoanCalculatorErrorCode.ERR_002.name(), ex.getErrorCode().name());
    }

}
