package rs.leanpay.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class MonthlyAmortizationEntity {

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

    public MonthlyAmortizationEntity(double paymentAmount, double principalAmount, double interestAmount, double balanceOwed) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }
}
