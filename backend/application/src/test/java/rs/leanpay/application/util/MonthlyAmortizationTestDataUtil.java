package rs.leanpay.application.util;

import org.springframework.stereotype.Component;
import rs.leanpay.model.MonthlyAmortizationEntity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SameParameterValue"})
@Component
public class MonthlyAmortizationTestDataUtil {

    public List<MonthlyAmortizationEntity> monthlyAmortizationEntityWithIdList(int n) {
        List<MonthlyAmortizationEntity> monthlyAmortizationEntityList = new ArrayList<>();
        while (n > 0) {
            monthlyAmortizationEntityList.add(buildMonthlyAmortizationEntityWithId(
                    1L, 1000d+n, 800d-n, 200d+n, 500d+n));
            n--;
        }
        return monthlyAmortizationEntityList;
    }

    private MonthlyAmortizationEntity buildMonthlyAmortizationEntity(
            Double paymentAmount, Double principalAmount, Double interestAmount, Double balanceOwed) {
        return MonthlyAmortizationEntity
                .builder()
                .paymentAmount(paymentAmount)
                .principalAmount(principalAmount)
                .interestAmount(interestAmount)
                .balanceOwed(balanceOwed)
                .build();
    }

    private MonthlyAmortizationEntity buildMonthlyAmortizationEntityWithId(
            Long id, Double paymentAmount, Double principalAmount, Double interestAmount, Double balanceOwed) {
        return MonthlyAmortizationEntity
                .builder()
                .id(id)
                .paymentAmount(paymentAmount)
                .principalAmount(principalAmount)
                .interestAmount(interestAmount)
                .balanceOwed(balanceOwed)
                .build();
    }

}
