package rs.leanpay.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleLoanResponse {

    private double monthlyPayment;
    private double totalInterestPaid;

}
