package rs.leanpay.application.service.impl;

import org.springframework.stereotype.Service;
import rs.leanpay.application.dto.*;
import rs.leanpay.application.exception.InterestRateLessThenZeroException;
import rs.leanpay.application.exception.LoanCalculatorErrorCode;
import rs.leanpay.application.service.LoanCalculatorService;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.round;
//import rs.leanpay.model.SimpleLoanEntity;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService {

//    private SimpleLoanRepository simpleLoanRepository;

//    @Autowired
//    public SimpleLoanServiceImpl(SimpleLoanRepository simpleLoanRepository) {
//        this.simpleLoanRepository = simpleLoanRepository;
//    }

    @Override
    public SimpleLoanResponse simpleLoanCalculator(
            double loanAmount, double interestRate, int loanTerm, LoanTermType loanTermType) {
        if (interestRate < 0) {
            throw new InterestRateLessThenZeroException(LoanCalculatorErrorCode.ERR_001);
        }
        if (loanTerm <= 0) {
            throw new InterestRateLessThenZeroException(LoanCalculatorErrorCode.ERR_002);
        }
        // Monthly / Yearly
//        int numerOfInstalments = loanTermType.equals(LoanTermType.years) ? loanTerm * 12 : loanTerm;

        if (loanTermType.equals(LoanTermType.years)) {
            loanTerm *= 12;
        }

        return calculateSimpleLoan(loanAmount, interestRate, loanTerm);
    }

    private SimpleLoanResponse calculateSimpleLoan(double amount, double interestRate, int n) {
        DecimalFormat df = new DecimalFormat("0.00");
        // Instalment interest MONTHLY
        double i = interestRate / 100 / 12;
        // Instalment amount
        double monthlyInstalment = (amount * i * pow((1 + i), n)) / (pow((1 + i), n ) - 1);
        // total interest
        double interest = monthlyInstalment * n - amount;
        return new SimpleLoanResponse(Double.parseDouble(df.format(monthlyInstalment)), Double.parseDouble(df.format(interest)));
    }

    public Map<String, List<AmortizationScheduleResponse>> amortizationScheduleCalculator(
            double loanAmount, double interestRate, int numberOfPayments, PaymentFrequencyType paymentFrequencyType) {

        SimpleLoanResponse simpleLoanResponse = calculateSimpleLoan(loanAmount, interestRate, numberOfPayments);

        AmortizationScheduleResponse amortizationScheduleResponse = new AmortizationScheduleResponse(
                loanAmount,
                interestRate,
                numberOfPayments,
                paymentFrequencyType.name(),
                simpleLoanResponse.getMonthlyPayment()*numberOfPayments,
                simpleLoanResponse.getInterest());

        Map<String, List<AmortizationScheduleResponse>> response = new HashMap<>();
        List<AmortizationScheduleResponse> list = new ArrayList<>();
        list.add(amortizationScheduleResponse);
        response.put(String.valueOf(Year.now().getValue()), list);

        return response;
    }


}
