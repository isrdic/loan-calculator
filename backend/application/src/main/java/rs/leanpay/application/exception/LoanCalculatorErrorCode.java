package rs.leanpay.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum LoanCalculatorErrorCode {

    ERR_001("InterestRate", MessageKey.ERR_001);

    private final String atribute;
    private final MessageKey messageKey;

    LoanCalculatorErrorCode(String atribute, MessageKey messageKey) {
        this.atribute = atribute;
        this.messageKey = messageKey;
    }

}
