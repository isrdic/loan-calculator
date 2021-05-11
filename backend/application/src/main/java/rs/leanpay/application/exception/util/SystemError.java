package rs.leanpay.application.exception.util;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"messageCode", "message"})
public class SystemError {

    private String messageCode;
    private String message;

    public SystemError(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

}
