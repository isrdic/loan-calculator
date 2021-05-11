package rs.leanpay.application.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.dto.MonthlyAmortization;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.MonthlyAmortizationEntity;

import java.util.List;

import static org.apache.commons.math3.util.Precision.round;

@Mapper
public interface AmortizationScheduleMapper {

    AmortizationScheduleMapper INSTANCE = Mappers.getMapper(AmortizationScheduleMapper.class);

    @Mappings({
            @Mapping(target = "amortizations", source = "amortizationList"),
            @Mapping(target = "totalPayments", source = "totalPayments", qualifiedByName = "roundTotalPayments"),
            @Mapping(target = "totalInterest", source = "totalInterest", qualifiedByName = "roundTotalInterest")
    })
    AmortizationScheduleResponse toAmortizationScheduleResponse(AmortizationScheduleEntity amortizationScheduleEntity);

    @Named("roundTotalPayments")
    static double roundTotalPayments(double totalPayments) {
        return round(totalPayments, 2);
    }
    @Named("roundTotalInterest")
    static double roundTotalInterest(double totalInterest) {
        return round(totalInterest, 2);
    }

    List<AmortizationScheduleResponse> toAmortizationScheduleResponseList(List<AmortizationScheduleEntity> amortizationScheduleEntityList);

    MonthlyAmortization toMonthlyAmortization(MonthlyAmortizationEntity monthlyAmortizationEntity);
    List<MonthlyAmortization> toMonthlyAmortizationList(List<MonthlyAmortizationEntity> monthlyAmortizationEntityList);
}
