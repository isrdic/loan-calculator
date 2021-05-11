package rs.leanpay.application.exception.util;

import lombok.Getter;

@Getter
public enum SystemErrorCode {

    SYS_001("System error occured", MessageKey.SYS_OO1);

    private final String atribute;
    private final MessageKey messageKey;

    SystemErrorCode(String atribute, MessageKey messageKey) {
        this.atribute = atribute;
        this.messageKey = messageKey;
    }

}
