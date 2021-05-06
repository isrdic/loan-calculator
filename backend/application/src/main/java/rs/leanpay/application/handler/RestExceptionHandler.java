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
import rs.leanpay.application.exception.InterestRateLessThenZeroException;
import rs.leanpay.application.exception.LoanCalculatorError;
import rs.leanpay.application.exception.LoanCalculatorErrorCode;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private Environment env;

    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(InterestRateLessThenZeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<LoanCalculatorError> interestRateLessThenZeroExceptionThrown(InterestRateLessThenZeroException e) {
        LoanCalculatorErrorCode errorCode = e.getErrorCode();
        return buildBadRequestResponse(new LoanCalculatorError(errorCode.getMessageKey().name(), env.getProperty(e.getErrorCode().getMessageKey().name())));
    }

    private <T> List<T> buildBadRequestResponse(T error){
        return Collections.singletonList(error);
    }
}
