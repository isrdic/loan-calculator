package rs.leanpay.application.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rs.leanpay.application.exception.RepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class HibernateTransactionUtil {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @FunctionalInterface
    public interface EntityManagerVoidCallable {
        void execute(EntityManager entityManager) throws RepositoryException;
    }

    @FunctionalInterface
    public interface TransactionVoidCallable extends EntityManagerVoidCallable {
    }

    public void doInTransaction(TransactionVoidCallable callable) {
        transactionTemplate.execute(status -> {
            try {
                callable.execute(entityManager);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public void clearDatabase() {
        doInTransaction(entityManager -> {
            entityManager.createQuery("delete from SimpleLoanEntity").executeUpdate();
            entityManager.createQuery("delete from MonthlyAmortizationEntity ").executeUpdate();
            entityManager.createQuery("delete from AmortizationScheduleEntity").executeUpdate();
        });
    }

}
