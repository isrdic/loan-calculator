package rs.leanpay.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.leanpay.application.dto.LoanTermType;
import rs.leanpay.application.dto.SimpleLoanRequest;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.exception.InterestRateLessThenZeroException;
import rs.leanpay.application.exception.LoanCalculatorErrorCode;
import rs.leanpay.application.repository.SimpleLoanRepository;
import rs.leanpay.application.service.SimpleLoanService;

import java.text.DecimalFormat;

import static java.lang.Math.pow;
import static java.lang.Math.round;
//import rs.leanpay.model.SimpleLoanEntity;

@Service
public class SimpleLoanServiceImpl implements SimpleLoanService {

//    private SimpleLoanRepository simpleLoanRepository;

//    @Autowired
//    public SimpleLoanServiceImpl(SimpleLoanRepository simpleLoanRepository) {
//        this.simpleLoanRepository = simpleLoanRepository;
//    }

    @Override
    public SimpleLoanResponse calculate(SimpleLoanRequest simpleLoanRequest) {

        if (simpleLoanRequest.getInterestRate() < 0) {
            throw new InterestRateLessThenZeroException(LoanCalculatorErrorCode.ERR_001);
        }

        DecimalFormat df = new DecimalFormat("0.00");

        double amount = simpleLoanRequest.getLoanAmount();
        double interestRate = simpleLoanRequest.getInterestRate();
        int loanTerm = simpleLoanRequest.getLoanTerm();
        LoanTermType type = simpleLoanRequest.getLoanTermType();

        int n = type.equals(LoanTermType.years) ? loanTerm * 12 : loanTerm;


//        if (type.equals(LoanTermType.years)) {
//
//            // numer of months
//            int n = loanTerm * 12;

            // interest rate per 1 month
            double i = interestRate / 100 / 12;

            // monthlyInstalment
            double monthlyInstalment = (amount * i * pow((1 + i), n)) / (pow((1 + i), n ) - 1);

            // total interest
            double interest = monthlyInstalment * n - amount;

            return new SimpleLoanResponse(Double.valueOf(df.format(monthlyInstalment)), Double.valueOf(df.format(interest)));
//        } if (type.equals(LoanTermType.years) {
//
//        }

//        return null;

//        return simpleLoanRepository.save(simpleLoanEntity);
    }
}
