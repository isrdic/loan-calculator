package rs.leanpay.application.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"code", "messageCode", "message"})
public class LoanCalculatorError {

    private String code;
    private String messageCode;
    private String message;

    public LoanCalculatorError(String message) {
        this.message = message;
    }

    public LoanCalculatorError(String code, String messageCode, String message) {
        this.code = code;
        this.messageCode = messageCode;
        this.message = message;
    }

}
