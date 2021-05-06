package rs.leanpay.application.exception;

import lombok.Getter;

@Getter
public enum LoanCalculatorErrorCode {

    ERR_001("InterestRate", MessageKey.ERR_001),
    ERR_002("LoanTerm", MessageKey.ERR_001);

    private final String atribute;
    private final MessageKey messageKey;

    LoanCalculatorErrorCode(String atribute, MessageKey messageKey) {
        this.atribute = atribute;
        this.messageKey = messageKey;
    }

}
