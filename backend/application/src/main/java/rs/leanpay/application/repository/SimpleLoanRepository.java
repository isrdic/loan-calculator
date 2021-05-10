package rs.leanpay.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.leanpay.model.SimpleLoanEntity;

import java.util.List;

public interface SimpleLoanRepository extends JpaRepository<SimpleLoanEntity, Long> {

    List<SimpleLoanEntity> findByLoanAmountGreaterThan(Double amount);
}
