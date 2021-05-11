package rs.leanpay.model;

import com.sun.istack.NotNull;
import lombok.*;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class AmortizationScheduleEntity {

    @Id
    @GeneratedValue
    private Long id;

    // Request parameters
    @NotNull
    private Double loanAmount;

    @NotNull
    private Double interestRate;

    @NotNull
    private Integer numberOfPayments;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentFrequencyType paymentFrequencyType;

    // Response attributes
    @NotNull
    private Double totalPayments;

    @NotNull
    private Double totalInterest;

    @OneToMany(
            targetEntity= MonthlyAmortizationEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "amortizationScheduleEntity")
    private List<MonthlyAmortizationEntity> amortizationList = new ArrayList<>();

    public AmortizationScheduleEntity(
            Double loanAmount,
            Double interestRate,
            Integer numberOfPayments,
            PaymentFrequencyType paymentFrequencyType,
            Double totalPayments,
            Double totalInterest,
            List<MonthlyAmortizationEntity> monthlyAmortizationEntityList) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.numberOfPayments = numberOfPayments;
        this.paymentFrequencyType = paymentFrequencyType;
        this.totalPayments = totalPayments;
        this.totalInterest = totalInterest;
        for (MonthlyAmortizationEntity monthlyAmortizationEntity : monthlyAmortizationEntityList) {
            this.addMonthlyAmortization(monthlyAmortizationEntity);
        }
    }

    private void addMonthlyAmortization(MonthlyAmortizationEntity monthlyAmortizationEntity){
        amortizationList.add(monthlyAmortizationEntity);
        monthlyAmortizationEntity.setAmortizationScheduleEntity(this);
    }

    public void addMonthlyAmortizationList(List<MonthlyAmortizationEntity> monthlyAmortizationEntityList) {
        for (MonthlyAmortizationEntity monthlyAmortizationEntity : monthlyAmortizationEntityList) {
            this.addMonthlyAmortization(monthlyAmortizationEntity);
        }
    }

}
