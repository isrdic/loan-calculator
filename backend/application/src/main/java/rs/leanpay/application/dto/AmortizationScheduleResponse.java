package rs.leanpay.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AmortizationScheduleResponse {

    private double totalPayments;
    private double totalInterest;

    private List<MonthlyAmortization> amortizations;

}
