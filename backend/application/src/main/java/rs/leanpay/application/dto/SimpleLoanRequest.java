package rs.leanpay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleLoanRequest {

    private double loanAmount;
    private double interestRate;
    private int loanTerm;
    private LoanTermType loanTermType;

}
