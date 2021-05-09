package rs.leanpay.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.leanpay.model.MonthlyAmortization;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class AmortizationScheduleResponse {

    @JsonIgnore
    private String templateMessage =
            "Amortization Schedule $%f at %f % \\interest";
//            "with %d %s payments\n" +
//            "Total Payments: $ %f Total Interest: $ %f";

//    private double loanAmount;
//    private double interestRate;
//    private int numberOfPayments;
//    private PaymentFrequencyType paymentFrequency;

//    private double loanAmount;
//    private double interestRate;
//    private int numberOfPayments;
//    private String numberOfPaymentsFrequency;

    private String message;

    private double totalPayments;
    private double totalInterest;

    private List<MonthlyAmortization> amortizations;

    public AmortizationScheduleResponse(
            String message, double totalPayments, double totalInterest, List<MonthlyAmortization> amortizations) {
        this.message = message;
        this.totalPayments = totalPayments;
        this.totalInterest = totalInterest;
        this.amortizations = amortizations;
    }

}
