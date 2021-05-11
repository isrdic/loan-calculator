package rs.leanpay.model;

import com.sun.istack.NotNull;
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

    @NotNull
    private Double loanAmount;

    @NotNull
    private Double interestRate;

    @NotNull
    private Integer loanTerm;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanTermType loanTermType;

    @NotNull
    private Double monthlyPayment;

    @NotNull
    private Double totalInterestPaid;

    public SimpleLoanEntity(Double loanAmount, Double interestRate, Integer loanTerm, LoanTermType loanTermType, Double monthlyPayment, Double totalInterestPaid) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.loanTermType = loanTermType;
        this.monthlyPayment = monthlyPayment;
        this.totalInterestPaid = totalInterestPaid;
    }
}
