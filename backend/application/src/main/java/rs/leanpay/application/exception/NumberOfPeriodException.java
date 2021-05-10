package rs.leanpay.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumberOfPeriodException extends RuntimeException {

    private LoanCalculatorErrorCode errorCode;

}
