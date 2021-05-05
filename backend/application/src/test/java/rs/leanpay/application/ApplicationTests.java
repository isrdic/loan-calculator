package rs.leanpay.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rs.leanpay.application.dto.LoanTermType;
import rs.leanpay.application.dto.SimpleLoanRequest;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.service.SimpleLoanService;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private SimpleLoanService simpleLoanService;

    @Test
    void contextLoads() {
    }

    @Test
    void calculate() {
        SimpleLoanRequest simpleLoanRequest = new SimpleLoanRequest(20000, 5, 5, LoanTermType.years);
        SimpleLoanResponse simpleLoanResponse = simpleLoanService.calculate(simpleLoanRequest);
        System.out.println();
        Assertions.assertEquals(simpleLoanResponse.getMonthlyPayment(), 377.42);

        SimpleLoanRequest simpleLoanRequestM = new SimpleLoanRequest(20000, 5, 5, LoanTermType.months);
        SimpleLoanResponse simpleLoanResponseM = simpleLoanService.calculate(simpleLoanRequestM);
        System.out.println();
        Assertions.assertEquals(simpleLoanResponseM.getMonthlyPayment(), 4050.14);

        SimpleLoanRequest simpleLoanRequestMi = new SimpleLoanRequest(-20000, 5, 5, LoanTermType.months);
        SimpleLoanResponse simpleLoanResponseMi = simpleLoanService.calculate(simpleLoanRequestMi);
        System.out.println();
        Assertions.assertEquals(simpleLoanResponseMi.getMonthlyPayment(), -4050.14);

        SimpleLoanRequest simpleLoanRequestEx = new SimpleLoanRequest(20000, -5, 5, LoanTermType.years);
        SimpleLoanResponse simpleLoanResponseEx = simpleLoanService.calculate(simpleLoanRequestEx);
        System.out.println();
        Assertions.assertEquals(simpleLoanResponseEx.getMonthlyPayment(), 377.42);
    }

}
