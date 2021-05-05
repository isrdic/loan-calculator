package rs.leanpay.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.leanpay.application.dto.LoanTermType;
import rs.leanpay.application.dto.SimpleLoanRequest;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.application.service.SimpleLoanService;

@RestController
@RequestMapping(value = "/loan")
public class LoanController {

    private final SimpleLoanService simpleLoanService;

    @Autowired
    public LoanController(SimpleLoanService simpleLoanService) {
        this.simpleLoanService = simpleLoanService;
    }

    @GetMapping(value = "/simple")
    public SimpleLoanResponse calculate(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("interestRate") double interestRate,
            @RequestParam("loanTerm") int loanTerm,
            @RequestParam("loanTermType") LoanTermType loanTermType) {
        SimpleLoanRequest simpleLoanRequest = new SimpleLoanRequest(loanAmount, interestRate, loanTerm, loanTermType);
        return simpleLoanService.calculate(simpleLoanRequest);
    }
}
