package rs.leanpay.application.service;

import rs.leanpay.application.dto.SimpleLoanRequest;
import rs.leanpay.application.dto.SimpleLoanResponse;

public interface SimpleLoanService {

    SimpleLoanResponse calculate(SimpleLoanRequest simpleLoanRequest);

}
