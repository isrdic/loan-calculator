package rs.leanpay.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleLoanResponse {

    private Double monthlyPayment;
    private Double totalInterestPaid;

}
