package rs.leanpay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "amortization_schedule")
public class AmortizationScheduleEntity {

    @Id
    @GeneratedValue
    private Long id;

    // Request parameters
    private Double loanAmount;
    private Double interestRate;
    private Integer numberOfPayments;

    @Enumerated(EnumType.STRING)
    private PaymentFrequencyType paymentFrequencyType;

    // Response attributes
    private Double totalPayments;
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

    public void addMonthlyAmortization(MonthlyAmortizationEntity monthlyAmortizationEntity){
        amortizationList.add(monthlyAmortizationEntity);
        monthlyAmortizationEntity.setAmortizationScheduleEntity(this);
    }

}
