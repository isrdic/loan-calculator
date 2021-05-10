package rs.leanpay.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.exception.NumberOfPeriodException;
import rs.leanpay.application.mapper.AmortizationScheduleMapper;
import rs.leanpay.application.repository.AmortizationScheduleRepository;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.MonthlyAmortizationEntity;
import rs.leanpay.model.enumeration.PaymentFrequencyType;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.exception.InterestRateException;
import rs.leanpay.application.exception.LoanCalculatorErrorCode;
import rs.leanpay.application.mapper.SimpleLoanMapper;
import rs.leanpay.application.repository.SimpleLoanRepository;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static org.apache.commons.math3.util.Precision.round;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService {

    private final SimpleLoanRepository simpleLoanRepository;
    private final AmortizationScheduleRepository amortizationScheduleRepository;

    @Autowired
    public LoanCalculatorServiceImpl(SimpleLoanRepository simpleLoanRepository, AmortizationScheduleRepository amortizationScheduleRepository) {
        this.simpleLoanRepository = simpleLoanRepository;
        this.amortizationScheduleRepository = amortizationScheduleRepository;
    }

    // -- SIMPLE LOAN CALCULATOR --
    @Override
    public SimpleLoanResponse simpleLoanCalculator(
            double loanAmount, double interestRate, int loanTerm, LoanTermType loanTermType) {

        validateParameteres(interestRate, loanTerm);
        return calculateSimpleLoan(loanAmount, interestRate, loanTerm, loanTermType);
    }

    @Override
    public List<SimpleLoanResponse> findSimpleLoanByLoanAmountGraterThen(double amount) {
        return SimpleLoanMapper.INSTANCE.toSimpleLoanResponseList(
                simpleLoanRepository.findByLoanAmountGreaterThan(amount));
    }

    @Override
    public List<SimpleLoanResponse> findSimpleLoanByByLoanTermType(LoanTermType loanTermType) {
        return SimpleLoanMapper.INSTANCE.toSimpleLoanResponseList(
                simpleLoanRepository.findByLoanTermType(loanTermType));
    }

    private SimpleLoanResponse calculateSimpleLoan(
            double amount, double interestRate, int loanTerm, LoanTermType loanTermType) {
        if (loanTermType.equals(LoanTermType.years)) {
            loanTerm *= 12;
        }
        // Instalment interest - monthly
        double i = interestRate / 100 / 12;
        // Instalment amount - monthly
        double monthlyPayment = (amount * i * pow((1 + i), loanTerm)) / (pow((1 + i), loanTerm) - 1);
        // total interest
        double totalInterestPaid = monthlyPayment * loanTerm - amount;

        simpleLoanRepository.save(
                new SimpleLoanEntity(amount, interestRate, loanTerm, loanTermType, monthlyPayment, totalInterestPaid));

        return new SimpleLoanResponse(round(monthlyPayment, 2), round(totalInterestPaid, 2));
    }

    // -- AMORTIZATION SCHEDULE CALCULATOR --
    @Override
    public AmortizationScheduleResponse amortizationScheduleCalculator (
            double loanAmount, double interestRate, int numberOfPayments, PaymentFrequencyType paymentFrequencyType) {
        validateParameteres(interestRate, numberOfPayments);

        double periodInterestRate = interestRate / 100 / paymentFrequencyType.getNumberOfPaymentsPerYear();
        return calculateAmortizationSchedule(loanAmount, periodInterestRate, numberOfPayments);
    }

    @Override
    public List<AmortizationScheduleResponse> findAmortizationScheduleByLoanAmountGraterThen(double amount) {
        return AmortizationScheduleMapper.INSTANCE.toAmortizationScheduleResponseList(
                amortizationScheduleRepository.findByLoanAmountGreaterThan(amount));
    }

    @SuppressWarnings("CommentedOutCode")
    private AmortizationScheduleResponse calculateAmortizationSchedule(double loanAmount, double periodInterestRate, int numberOfPayments) {

        double periodPayment = calculatePeriodPayment(loanAmount, periodInterestRate, numberOfPayments);

        double totalPayments = periodPayment * numberOfPayments;
        double totalInterest = totalPayments - loanAmount;

        double loanBalance = loanAmount;
//        double interestBalance = totalInterest;
//        double totalBalance = totalPayments;

        double interestPaid, amountPaid;
        List<MonthlyAmortizationEntity> amortizationEntities = new ArrayList<>();

        for (int period = 1; period <= numberOfPayments; period++) {
            // Na preostali dug ide kamata za period
            interestPaid = loanBalance * periodInterestRate;
            amountPaid = periodPayment - interestPaid;

            // KOLIKO JE UKUPNO OSTALO
//            totalBalance -= periodPayment;

            // KOLIKO JE OSTALO GLAVNOG DUGA I KAMATE
            loanBalance -= amountPaid;
//            interestBalance -= interestPaid;

            amortizationEntities.add(new MonthlyAmortizationEntity(
                    round(periodPayment, 2),
                    round(amountPaid, 2),
                    round(interestPaid, 2),
                    Math.abs(round(loanBalance, 2))));
        }

        AmortizationScheduleEntity amortizationScheduleEntity = new AmortizationScheduleEntity(
                loanAmount, periodInterestRate, numberOfPayments, PaymentFrequencyType.Monthly, totalPayments, totalInterest, amortizationEntities);
        amortizationScheduleRepository.save(amortizationScheduleEntity);

        return AmortizationScheduleMapper.INSTANCE.toAmortizationScheduleResponse(amortizationScheduleEntity);
    }

    private double calculatePeriodPayment(double totalAmount, double periodInterestRate, int numberOfPayments) {
        return (totalAmount * periodInterestRate) / (1 - Math.pow(1 + periodInterestRate, -numberOfPayments));
    }

    private void validateParameteres(double interestRate, int loanTerm) {
        if (interestRate < 0) {
            throw new InterestRateException(LoanCalculatorErrorCode.ERR_001);
        }
        if (loanTerm <= 0) {
            throw new NumberOfPeriodException(LoanCalculatorErrorCode.ERR_002);
        }
    }

}
