package rs.leanpay.application.integration.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.leanpay.application.integration.AbstractIntegrationTest;
import rs.leanpay.application.repository.SimpleLoanRepository;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimpleLoanRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private SimpleLoanRepository simpleLoanRepository;

    private SimpleLoanEntity entity1600;

    @Before
    public void setUp() {
        entity1600 = simpleLoanCalculatorTestDataUtil.simpleLoanEntity16000();
        simpleLoanCalculatorTestDataUtil.simpleLoanEntityDB20000();
        simpleLoanCalculatorTestDataUtil.simpleLoanEntityDB28000();
        simpleLoanCalculatorTestDataUtil.simpleLoanEntityDB1000();
    }

    @Test
    public void saveSimpleLoanEntityTest() {
        SimpleLoanEntity entityDB = simpleLoanRepository.save(entity1600);

        assertNotNull(entityDB);
        assertNotNull(entityDB.getId());
        assertEquals(entity1600.getLoanAmount(), entityDB.getLoanAmount());
        assertEquals(entity1600.getInterestRate(), entityDB.getInterestRate());
        assertEquals(entity1600.getLoanTerm(), entityDB.getLoanTerm());
        assertEquals(entity1600.getLoanTermType(), entityDB.getLoanTermType());
        assertEquals(entity1600.getLoanTermType(), entityDB.getLoanTermType());
        assertEquals(entity1600.getMonthlyPayment(), entityDB.getMonthlyPayment());
    }

    @Test
    public void findByLoanAmountGreaterThanSimpleLoanEntityTest() {
        List<SimpleLoanEntity> entities = simpleLoanRepository.findByLoanAmountGreaterThan(18000.00);
        assertEquals(2, entities.size());
    }

    @Test
    public void findByLoanTermTypeSimpleLoanEntityTest() {
        List<SimpleLoanEntity> entities = simpleLoanRepository.findByLoanTermType(LoanTermType.months);
        assertEquals(2, entities.size());
        entities = simpleLoanRepository.findByLoanTermType(LoanTermType.years);
        assertEquals(1, entities.size());
    }

}
