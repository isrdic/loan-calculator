package rs.leanpay.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyAmortization {

    private Double paymentAmount;
    private Double principalAmount;
    private Double interestAmount;
    private Double balanceOwed;

    private LocalDate instalmentDay;

}
