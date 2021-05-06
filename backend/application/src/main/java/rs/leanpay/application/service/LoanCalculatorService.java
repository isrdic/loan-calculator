package rs.leanpay.application.service;

import rs.leanpay.application.dto.*;

import java.util.List;
import java.util.Map;

public interface LoanCalculatorService {

    SimpleLoanResponse simpleLoanCalculator(double loanAmount, double interestRate, int loanTerm, LoanTermType loanTermType);
    Map<String, List<AmortizationScheduleResponse>> amortizationScheduleCalculator(double loanAmount, double interestRate, int numberOfPayments, PaymentFrequencyType paymentFrequencyType);

}
