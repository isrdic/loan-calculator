package rs.leanpay.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.model.enumeration.LoanTermType;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @Test
    void contextLoads() {
    }

    @Test
    void calculate() {
        SimpleLoanResponse simpleLoanResponse = loanCalculatorService.simpleLoanCalculator(20000, 5, 5, LoanTermType.years);
        Assertions.assertEquals(simpleLoanResponse.getMonthlyPayment(), 377.42);

        SimpleLoanResponse simpleLoanResponseM = loanCalculatorService.simpleLoanCalculator(20000, 5, 5, LoanTermType.months);
        Assertions.assertEquals(simpleLoanResponseM.getMonthlyPayment(), 4050.14);

        SimpleLoanResponse simpleLoanResponseMi = loanCalculatorService.simpleLoanCalculator(-20000, 5, 5, LoanTermType.months);
        Assertions.assertEquals(simpleLoanResponseMi.getMonthlyPayment(), -4050.14);
    }

}
