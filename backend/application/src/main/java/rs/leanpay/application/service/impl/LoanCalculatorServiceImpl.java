package rs.leanpay.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.repository.AmortizationScheduleRepository;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.MonthlyAmortization;
import rs.leanpay.model.enumeration.PaymentFrequencyType;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.exception.InterestRateLessThenZeroException;
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

    private SimpleLoanRepository simpleLoanRepository;
    private AmortizationScheduleRepository amortizationScheduleRepository;

    @Autowired
    public LoanCalculatorServiceImpl(SimpleLoanRepository simpleLoanRepository, AmortizationScheduleRepository amortizationScheduleRepository) {
        this.simpleLoanRepository = simpleLoanRepository;
        this.amortizationScheduleRepository = amortizationScheduleRepository;
    }

    @Override
    public SimpleLoanResponse simpleLoanCalculator(
            double loanAmount, double interestRate, int loanTerm, LoanTermType loanTermType) {

        if (interestRate < 0) {
            throw new InterestRateLessThenZeroException(LoanCalculatorErrorCode.ERR_001);
        }
        if (loanTerm <= 0) {
            throw new InterestRateLessThenZeroException(LoanCalculatorErrorCode.ERR_002);
        }

        return calculateSimpleLoan(loanAmount, interestRate, loanTerm, loanTermType);
    }

    @Override
    public List<SimpleLoanResponse> findByLoanAmountGraterThen(double amount) {
        List<SimpleLoanEntity> entities = simpleLoanRepository.findByLoanAmountGreaterThan(amount);
        return SimpleLoanMapper.INSTANCE.toSimpleLoanResponseList(entities);
    }

    private SimpleLoanResponse calculateSimpleLoan(double amount, double interestRate, int loanTerm, LoanTermType loanTermType) {

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

    public AmortizationScheduleResponse amortizationScheduleCalculator (
            double loanAmount, double interestRate, int numberOfPayments, PaymentFrequencyType paymentFrequencyType) {
        double periodInterestRate = interestRate / 100 / paymentFrequencyType.getNumberOfPaymentsPerYear();
        return displayAmortizationTable(loanAmount, periodInterestRate, numberOfPayments);

    }



    private AmortizationScheduleResponse displayAmortizationTable(double amount, double rate, int numberOfPayments) {

        List<MonthlyAmortization> amortizations = new ArrayList<>();

        double balance = amount;
        double payment = calculateMonthlyPayment(amount, rate, numberOfPayments);
        double irPaid, amountPaid, newBalance;

        // treba prikazati
//        private double paymentAmount;
//        private double principalAmount;
//        private double interestAmount;
//        private double balanceOwed;

        double totalPayment = 0;
        double totalInterest = 0;

        for (int period = 1; period <= numberOfPayments; period++) {
//        while ()
            irPaid = balance * rate;
            totalInterest += irPaid;
            amountPaid = payment - irPaid;
            newBalance = balance - amountPaid;
            balance = newBalance;

            amortizations.add(new MonthlyAmortization(irPaid+amountPaid, amountPaid, irPaid, balance));
        }


        totalPayment = amount + totalInterest;

//        private Double loanAmount;
//        private Double interestRate;
//        private Integer numberOfPayments;
//        private PaymentFrequencyType paymentFrequencyType;

//        private Double totalPayments;
//        private Double totalInterest;
        AmortizationScheduleEntity amortizationScheduleEntity = new AmortizationScheduleEntity(
                amount,
                rate,
                numberOfPayments,
                PaymentFrequencyType.Monthly,
                totalPayment,
                totalInterest
        );
        amortizationScheduleEntity.addMonthlyAmortizationList(amortizations);
        amortizationScheduleRepository.save(amortizationScheduleEntity);

        return new AmortizationScheduleResponse("POZDRAV", totalPayment, totalInterest, amortizations);
    }

    private double calculateMonthlyPayment(double totalAmount, double periodInterestRate, int numberOfPayments) {
        return (totalAmount * periodInterestRate) / (1 - Math.pow(1 + periodInterestRate, -numberOfPayments));
    }


}
