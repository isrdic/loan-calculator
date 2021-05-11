package rs.leanpay.application.integration.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.integration.AbstractIntegrationTest;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.model.enumeration.LoanTermType;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanCalculatorServiceIntegrationTest extends AbstractIntegrationTest  {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @Test
    public void simpleLoanCalculatorTest() {
        /*
            Loan Term: Months
            Results from https://www.calculatorsoup.com/calculators/financial/loan-calculator-simple.php:
            Monthly Payment: $165.73
            Total Interest: $1932.48
        */
        SimpleLoanResponse simpleLoanResponseMonths = loanCalculatorService.simpleLoanCalculator(10000d, 6d, 72, LoanTermType.months);
        assertEquals(simpleLoanResponseMonths.getMonthlyPayment(), 165.73);
        assertEquals(simpleLoanResponseMonths.getTotalInterestPaid(), 1932.48);

        /*
            Loan Term: Years
            Results from https://www.calculatorsoup.com/calculators/financial/loan-calculator-simple.php:
            Monthly Payment: $849.95
            Total Interest: $199.36
        */
        SimpleLoanResponse simpleLoanResponseYears = loanCalculatorService.simpleLoanCalculator(10000d, 3.66, 1, LoanTermType.years);
        assertEquals(simpleLoanResponseYears.getMonthlyPayment(), 849.95);
        assertEquals(simpleLoanResponseYears.getTotalInterestPaid(), 199.36);
    }

    @Test
    public void amortizationScheduleCalculator() {

        /*
            Frequency: Daily("Daily 365/yr", 365)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $10,380.59
            Total Interest: $380.59
        */
        AmortizationScheduleResponse amortizationScheduleResponseDaily = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 360, PaymentFrequencyType.Daily);
        assertAmortizationScheduleResponse(amortizationScheduleResponseDaily,10380.59,380.59,  360);

        /*
            Frequency: Weekly("Weekly 52/yr", 52)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,380.36
            Total Interest: $1,380.36
        */
        AmortizationScheduleResponse amortizationScheduleResponseWeekly = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 180, PaymentFrequencyType.Weekly);
        assertAmortizationScheduleResponse(amortizationScheduleResponseWeekly,11380.36, 1380.36, 180);

        /*
            Frequency: Biweekly("Biweekly 26/yr", 26)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,387.49
            Total Interest: $1,387.49
        */
        AmortizationScheduleResponse amortizationScheduleResponseBiweekly = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 90, PaymentFrequencyType.Biweekly);
        assertAmortizationScheduleResponse(amortizationScheduleResponseBiweekly,11387.49, 1387.49, 90);

        /*
            Frequency: SemiMonth("Semi-Month 24/yr", 24)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $10,995.86
            Total Interest: $995.86
        */
        AmortizationScheduleResponse amortizationScheduleResponseSemiMonth = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 60, PaymentFrequencyType.SemiMonth);
        assertAmortizationScheduleResponse(amortizationScheduleResponseSemiMonth,10995.86, 995.86, 60);

        /*
            Frequency: Monthly("Monthly 12/yr", 12)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,011.63
            Total Interest: $1,011.63
        */
        AmortizationScheduleResponse amortizationScheduleResponseMonthly = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 30, PaymentFrequencyType.Monthly);
        assertAmortizationScheduleResponse(amortizationScheduleResponseMonthly,11011.63, 1011.63, 30);

        /*
            Frequency: Bimonthly("Bimonthly 6/yr", 6)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,043.07
            Total Interest: $1,043.07
        */
        AmortizationScheduleResponse amortizationScheduleResponseBimonthly = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 15, PaymentFrequencyType.Bimonthly);
        assertAmortizationScheduleResponse(amortizationScheduleResponseBimonthly,11043.07, 1043.07, 15);

        /*
            Frequency: Quarterly("Quarterly 4/yr", 4)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,277.58
            Total Interest: $1,277.58
        */
        AmortizationScheduleResponse amortizationScheduleResponseQuarterly = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 12, PaymentFrequencyType.Quarterly);
        assertAmortizationScheduleResponse(amortizationScheduleResponseQuarterly,11277.58, 1277.58, 12);

        /*
            Frequency: SemiAnnual("Semi-Annual 2/yr", 2)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,371.30
            Total Interest: $1,371.30
        */
        AmortizationScheduleResponse amortizationScheduleResponseSemiAnnual = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 6, PaymentFrequencyType.SemiAnnual);
        assertAmortizationScheduleResponse(amortizationScheduleResponseSemiAnnual,11371.30, 1371.30, 6);

        /*
            Frequency: Annual("Annual 1/yr", 1)
            Results from https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php:
            Total Payments: $11,557.08
            Total Interest: $1,557.08
        */
        AmortizationScheduleResponse amortizationScheduleResponseAnnual = loanCalculatorService.amortizationScheduleCalculator(10000, 7.6, 3, PaymentFrequencyType.Annual);
        assertAmortizationScheduleResponse(amortizationScheduleResponseAnnual,11557.08, 1557.08, 3);
    }

    private void assertAmortizationScheduleResponse(AmortizationScheduleResponse amortizationScheduleResponse, double totalPayments, double totalInterest, int amortizationListSize) {
        assertEquals(totalPayments, amortizationScheduleResponse.getTotalPayments(), 0.5);
        assertEquals(totalInterest, amortizationScheduleResponse.getTotalInterest(), 0.3);
        assertEquals(amortizationListSize, amortizationScheduleResponse.getAmortizations().size());
    }

}
