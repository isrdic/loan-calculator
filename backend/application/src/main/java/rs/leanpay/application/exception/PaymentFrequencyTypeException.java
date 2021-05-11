package rs.leanpay.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.leanpay.application.exception.util.LoanCalculatorErrorCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFrequencyTypeException extends RuntimeException {

    private LoanCalculatorErrorCode errorCode;

}