package rs.leanpay.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import rs.leanpay.application.service.LoanCalculatorService;

@SpringBootApplication(scanBasePackages = {"rs.leanpay.application"})
@EntityScan(basePackages = "rs.leanpay.model")
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @Test
    void contextLoads() {
    }

}
