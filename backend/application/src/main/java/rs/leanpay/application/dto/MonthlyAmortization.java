package rs.leanpay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAmortization {

    private double paymentAmount;
    private double principalAmount;
    private double interestAmount;
    private double balanceOwed;

}
