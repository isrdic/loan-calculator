package rs.leanpay.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmortizationScheduleResponse {

    private Double totalPayments;
    private Double totalInterest;

    private List<MonthlyAmortization> amortizations;

}
