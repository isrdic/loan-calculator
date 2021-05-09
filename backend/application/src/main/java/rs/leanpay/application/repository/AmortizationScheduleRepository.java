package rs.leanpay.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.leanpay.model.AmortizationScheduleEntity;

@Repository
public interface AmortizationScheduleRepository extends JpaRepository<AmortizationScheduleEntity, Long> {
}
