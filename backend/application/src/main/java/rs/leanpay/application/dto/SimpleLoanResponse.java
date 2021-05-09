package rs.leanpay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleLoanResponse {

    private double monthlyPayment;
    private double totalInterestPaid;

}
