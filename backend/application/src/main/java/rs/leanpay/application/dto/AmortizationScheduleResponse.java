package rs.leanpay.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class AmortizationScheduleResponse {

    private String templateMessage =
            "Amortization Schedule\n" +
            "$ %f at %f % interest"; //\n" +
//            "with %d %s payments\n" +
//            "Total Payments: $ %f Total Interest: $ %f";

//    private double loanAmount;
//    private double interestRate;
//    private int numberOfPayments;
//    private PaymentFrequencyType paymentFrequency;

    private double loanAmount;
    private double interestRate;
    private int numberOfPayments;
    private String numberOfPaymentsFrequency;

    private double totalPayments;
    private double totalInterest;

    private String message;

    public AmortizationScheduleResponse(
            double loanAmount,
            double interestRate,
            int numberOfPayments,
            String numberOfPaymentsFrequency,
            double totalPayments,
            double totalInterest) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.numberOfPayments = numberOfPayments;
        this.numberOfPaymentsFrequency = numberOfPaymentsFrequency;
        this.totalPayments = totalPayments;
        this.totalInterest = totalInterest;
        this.message =
                String.format(templateMessage, loanAmount, interestRate
                );
    }

}
