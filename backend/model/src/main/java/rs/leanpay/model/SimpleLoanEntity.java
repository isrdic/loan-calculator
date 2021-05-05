package rs.leanpay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "simple_loan_request")
public class SimpleLoanEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Double loanAmount;
    private Double interestRate;

}
