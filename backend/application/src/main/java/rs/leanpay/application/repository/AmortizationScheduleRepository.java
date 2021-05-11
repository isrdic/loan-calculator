package rs.leanpay.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.enumeration.PaymentFrequencyType;

import java.util.List;

@Repository
public interface AmortizationScheduleRepository extends JpaRepository<AmortizationScheduleEntity, Long> {

    List<AmortizationScheduleEntity> findByLoanAmountGreaterThan(Double amount);
    List<AmortizationScheduleEntity> findByPaymentFrequencyType(PaymentFrequencyType paymentFrequencyType);
}
