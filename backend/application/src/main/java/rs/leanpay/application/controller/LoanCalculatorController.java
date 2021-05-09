package rs.leanpay.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.leanpay.application.dto.*;
import rs.leanpay.application.service.LoanCalculatorService;
import rs.leanpay.model.enumeration.LoanTermType;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import java.util.List;

@RestController
@RequestMapping(value = "/loan")
public class LoanCalculatorController {

    private final LoanCalculatorService loanCalculatorService;

    @Autowired
    public LoanCalculatorController(LoanCalculatorService loanCalculatorService) {
        this.loanCalculatorService = loanCalculatorService;
    }

    @GetMapping(value = "/simple-loan-calculator")
    public SimpleLoanResponse simpleLoanCalculator(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("interestRate") double interestRate,
            @RequestParam("loanTerm") int loanTerm,
            @RequestParam("loanTermType") LoanTermType loanTermType) {
        return loanCalculatorService.simpleLoanCalculator(loanAmount, interestRate, loanTerm, loanTermType);
    }

    @GetMapping(value = "/simple-loan-calculator/{graterThen}")
    public List<SimpleLoanResponse> findByLoanAmountGraterThen(
            @PathVariable("graterThen") double graterThen) {
        return loanCalculatorService.findByLoanAmountGraterThen(graterThen);
    }

    @GetMapping(value = "/amortization-schedule-calculator")
    public AmortizationScheduleResponse amortizationScheduleCalculator(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("interestRate") double interestRate,
            @RequestParam("numberOfPayments") int numberOfPayments,
            @RequestParam("paymentFrequency") PaymentFrequencyType paymentFrequencyType) {
        return loanCalculatorService.amortizationScheduleCalculator(loanAmount, interestRate, numberOfPayments, paymentFrequencyType);
    }
}
