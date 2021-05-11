package rs.leanpay.application.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.leanpay.application.exception.*;
import rs.leanpay.application.exception.util.LoanCalculatorError;
import rs.leanpay.application.exception.util.SystemError;
import rs.leanpay.application.exception.util.SystemErrorCode;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final Environment env;

    @Autowired
    public RestExceptionHandler(Environment env) {
        this.env = env;
    }

    @ExceptionHandler(InterestRateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public LoanCalculatorError interestRateExceptionThrown(InterestRateException e) {
        LOGGER.error(env.getProperty(e.getErrorCode().getMessageKey().name()), e);
        return new LoanCalculatorError(e.getErrorCode().getMessageKey().name(), env.getProperty(e.getErrorCode().getMessageKey().name()));
    }

    @ExceptionHandler(NumberOfPeriodException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public LoanCalculatorError NumberOfPeriodExceptionThrown(NumberOfPeriodException e) {
        LOGGER.error(env.getProperty(e.getErrorCode().getMessageKey().name()), e);
        return new LoanCalculatorError(e.getErrorCode().getMessageKey().name(), env.getProperty(e.getErrorCode().getMessageKey().name()));
    }

    @ExceptionHandler(PaymentFrequencyTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public LoanCalculatorError PaymentFrequencyTypeExceptionThrown(PaymentFrequencyTypeException e) {
        LOGGER.error(env.getProperty(e.getErrorCode().getMessageKey().name()), e);
        return new LoanCalculatorError(e.getErrorCode().getMessageKey().name(), env.getProperty(e.getErrorCode().getMessageKey().name()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public SystemError notHandledException(Exception ex) {
        LOGGER.error("System error ocured", ex);
        return new SystemError(SystemErrorCode.SYS_001.name(), env.getProperty(SystemErrorCode.SYS_001.getMessageKey().name()));
    }

}
