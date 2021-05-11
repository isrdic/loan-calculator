package rs.leanpay.application.integration;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.leanpay.application.ApplicationTest;
import rs.leanpay.application.util.AmortizationScheduleCalculatorTestDataUtil;
import rs.leanpay.application.util.HibernateTransactionUtil;
import rs.leanpay.application.util.MonthlyAmortizationTestDataUtil;
import rs.leanpay.application.util.SimpleLoanCalculatorTestDataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected HibernateTransactionUtil hibernateTransactionUtil;

    @Autowired
    protected SimpleLoanCalculatorTestDataUtil simpleLoanCalculatorTestDataUtil;

    @Autowired
    protected AmortizationScheduleCalculatorTestDataUtil amortizationScheduleCalculatorTestDataUtil;

    @Autowired
    protected MonthlyAmortizationTestDataUtil monthlyAmortizationTestDataUtil;

    @After
    public void tearDown() {
        hibernateTransactionUtil.clearDatabase();
    }

}
