package rs.leanpay.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    @NotNull
    private Double paymentAmount;

    @NotNull
    private Double principalAmount;

    @NotNull
    private Double interestAmount;

    @NotNull
    private Double balanceOwed;

    private LocalDate instalmentDay;

    @ManyToOne(
            targetEntity = AmortizationScheduleEntity.class,
            fetch = FetchType.LAZY,
            optional = false)
    private AmortizationScheduleEntity amortizationScheduleEntity;

    public MonthlyAmortizationEntity(Double paymentAmount, Double principalAmount, Double interestAmount, Double balanceOwed, LocalDate instalmentDay) {
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
        this.instalmentDay = instalmentDay;
    }
}
