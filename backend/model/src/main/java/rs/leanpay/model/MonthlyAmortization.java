package rs.leanpay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "monthly_amortization")
public class MonthlyAmortization {

    @Id
    @GeneratedValue
    private Long id;

    private double paymentAmount;
    private double principalAmount;
    private double interestAmount;
    private double balanceOwed;


    @ManyToOne(
            targetEntity = AmortizationScheduleEntity.class,
            fetch = FetchType.LAZY)
    private AmortizationScheduleEntity amortizationScheduleEntity;

    public MonthlyAmortization(double paymentAmount, double principalAmount, double interestAmount, double balanceOwed) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }
}
