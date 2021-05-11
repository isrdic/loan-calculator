package rs.leanpay.application.util;

import org.springframework.stereotype.Component;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import java.util.ArrayList;

@SuppressWarnings({"SameParameterValue", "unused"})
@Component
public class AmortizationScheduleCalculatorTestDataUtil {

    public AmortizationScheduleEntity amortizationScheduleEntityWithId16000() {
        return buildAmortizatiobScheduleEntityWithId(1L, 16000d, 9d, 12, PaymentFrequencyType.Monthly, 616d, 35d);
    }

    private AmortizationScheduleEntity buildAmortizatiobScheduleEntity(
            Double loanAmount, Double interestRate, Integer numberOfPayments, PaymentFrequencyType paymentFrequencyType, Double totalPayments, Double totalInterest) {
        return AmortizationScheduleEntity
                .builder()
                .loanAmount(loanAmount)
                .interestRate(interestRate)
                .numberOfPayments(numberOfPayments)
                .paymentFrequencyType(paymentFrequencyType)
                .totalPayments(totalPayments)
                .totalInterest(totalInterest)
                .build();
    }

    private AmortizationScheduleEntity buildAmortizatiobScheduleEntityWithId(
            Long id, Double loanAmount, Double interestRate, Integer numberOfPayments, PaymentFrequencyType paymentFrequencyType, Double totalPayments, Double totalInterest) {
        return AmortizationScheduleEntity
                .builder()
                .id(id)
                .loanAmount(loanAmount)
                .interestRate(interestRate)
                .numberOfPayments(numberOfPayments)
                .paymentFrequencyType(paymentFrequencyType)
                .totalPayments(totalPayments)
                .totalInterest(totalInterest)
                .amortizationList(new ArrayList<>())
                .build();
    }

}
