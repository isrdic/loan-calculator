package rs.leanpay.application.service;

import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.model.enumeration.PaymentFrequencyType;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.model.enumeration.LoanTermType;

import java.util.List;

public interface LoanCalculatorService {

    SimpleLoanResponse simpleLoanCalculator(double loanAmount, double interestRate, int loanTerm, LoanTermType loanTermType);
    List<SimpleLoanResponse> findByLoanAmountGraterThen(double amount);

    AmortizationScheduleResponse amortizationScheduleCalculator(double loanAmount, double interestRate, int numberOfPayments, PaymentFrequencyType paymentFrequencyType);

}
