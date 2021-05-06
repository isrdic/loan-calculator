package rs.leanpay.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.leanpay.application.dto.*;
import rs.leanpay.application.service.LoanCalculatorService;

import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/amortization-schedule-calculator")
    public Map<String, List<AmortizationScheduleResponse>> amortizationScheduleCalculator(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("interestRate") double interestRate,
            @RequestParam("numberOfPayments") int numberOfPayments,
            @RequestParam("paymentFrequency") PaymentFrequencyType paymentFrequencyType) {
        return loanCalculatorService.amortizationScheduleCalculator(loanAmount, interestRate, numberOfPayments, paymentFrequencyType);
    }
}
