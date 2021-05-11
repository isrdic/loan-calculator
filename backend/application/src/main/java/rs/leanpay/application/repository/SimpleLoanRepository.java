package rs.leanpay.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.leanpay.model.SimpleLoanEntity;
import rs.leanpay.model.enumeration.LoanTermType;

import java.util.List;

@Repository
public interface SimpleLoanRepository extends JpaRepository<SimpleLoanEntity, Long> {

    List<SimpleLoanEntity> findByLoanAmountGreaterThan(Double amount);
    List<SimpleLoanEntity> findByLoanTermType(LoanTermType loanTermType);
}
