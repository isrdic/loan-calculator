package rs.leanpay.model;

import lombok.*;
import rs.leanpay.model.enumeration.LoanTermType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class SimpleLoanEntity {

    @Id
    @GeneratedValue
    private Long id;

    // Request parameters
    private Double loanAmount;
    private Double interestRate;
    private Integer loanTerm;

    @Enumerated(EnumType.STRING)
    private LoanTermType loanTermType;

    // Response attributes
    private Double monthlyPayment;
    private Double totalInteresPaid;

    public SimpleLoanEntity(Double loanAmount, Double interestRate, Integer loanTerm, LoanTermType loanTermType, Double monthlyPayment, Double totalInteresPaid) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.loanTermType = loanTermType;
        this.monthlyPayment = monthlyPayment;
        this.totalInteresPaid = totalInteresPaid;
    }
}
