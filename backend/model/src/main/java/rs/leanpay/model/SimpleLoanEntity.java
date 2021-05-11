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

    // Request parameters
    @NotNull
    private Double loanAmount;

    @NotNull
    private Double interestRate;

    @NotNull
    private Integer loanTerm;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanTermType loanTermType;

    // Response attributes
    @NotNull
    private Double monthlyPayment;

    @NotNull
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
