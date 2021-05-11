package rs.leanpay.application.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.leanpay.application.repository.SimpleLoanRepository;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;

@SuppressWarnings({"SameParameterValue", "UnusedReturnValue", "unused"})
@Component
public class SimpleLoanCalculatorTestDataUtil {

    @Autowired
    private SimpleLoanRepository simpleLoanRepository;

    public SimpleLoanEntity simpleLoanEntity16000() {
        return buildSimpleLoanEntity(16000.00, 9.00, 2, LoanTermType.years, 616.99, 35.00);
    }

    public SimpleLoanEntity simpleLoanEntityDB20000() {
        return simpleLoanRepository.save(buildSimpleLoanEntity(20000.00, 6.00, 36, LoanTermType.months, 336.99, 1322.55));
    }

    public SimpleLoanEntity simpleLoanEntityDB28000() {
        return simpleLoanRepository.save(buildSimpleLoanEntity(28000.00, 9.00, 72, LoanTermType.months, 216.99, 2922.55));
    }

    public SimpleLoanEntity simpleLoanEntityDB1000() {
        return simpleLoanRepository.save(buildSimpleLoanEntity(1000.00, 9.00, 2, LoanTermType.years, 616.99, 35.00));
    }

    public SimpleLoanEntity simpleLoanEntity1000() {
        return buildSimpleLoanEntity(1000.00, 3.00, 3, LoanTermType.months, 616.99, 35.00);
    }

    public SimpleLoanEntity simpleLoanEntity1000WithID() {
        return buildSimpleLoanEntityWithId(1L, 1000.00, 3.00, 3, LoanTermType.months, 616.99, 35.00);
    }

    private SimpleLoanEntity buildSimpleLoanEntity(
            Double loanAmount, Double interestRate, Integer loanTerm, LoanTermType loanTermType, Double monthlyPayment, Double totalInterestPaid) {
        return SimpleLoanEntity
                .builder()
                .loanAmount(loanAmount)
                .interestRate(interestRate)
                .loanTerm(loanTerm)
                .loanTermType(loanTermType)
                .monthlyPayment(monthlyPayment)
                .totalInteresPaid(totalInterestPaid)
                .build();
    }

    private SimpleLoanEntity buildSimpleLoanEntityWithId(
            Long id, Double loanAmount, Double interestRate, Integer loanTerm, LoanTermType loanTermType, Double monthlyPayment, Double totalInterestPaid) {
        return SimpleLoanEntity
                .builder()
                .id(id)
                .loanAmount(loanAmount)
                .interestRate(interestRate)
                .loanTerm(loanTerm)
                .loanTermType(loanTermType)
                .monthlyPayment(monthlyPayment)
                .totalInteresPaid(totalInterestPaid)
                .build();
    }

}
