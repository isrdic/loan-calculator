package rs.leanpay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(
            targetEntity=MonthlyAmortization.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "amortizationScheduleEntity")
    private List<MonthlyAmortization> amortizationList = new ArrayList<>();

    public AmortizationScheduleEntity(
            Double loanAmount,
            Double interestRate,
            Integer numberOfPayments,
            PaymentFrequencyType paymentFrequencyType,
            Double totalPayments,
            Double totalInterest) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.numberOfPayments = numberOfPayments;
        this.paymentFrequencyType = paymentFrequencyType;
        this.totalPayments = totalPayments;
        this.totalInterest = totalInterest;
    }

    public void addMonthlyAmortization(MonthlyAmortization monthlyAmortization){
        amortizationList.add(monthlyAmortization);
        monthlyAmortization.setAmortizationScheduleEntity(this);
    }

    public void addMonthlyAmortizationList(List<MonthlyAmortization> monthlyAmortizationList) {
        for (MonthlyAmortization monthlyAmortization : monthlyAmortizationList) {
            this.addMonthlyAmortization(monthlyAmortization);
        }
    }

    public void removeMonthlyAmortization(MonthlyAmortization monthlyAmortization){
        amortizationList.remove(monthlyAmortization);
        monthlyAmortization.setAmortizationScheduleEntity(null);
    }
}
