package rs.leanpay.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import rs.leanpay.application.dto.AmortizationScheduleResponse;
import rs.leanpay.application.dto.MonthlyAmortization;
import rs.leanpay.model.AmortizationScheduleEntity;
import rs.leanpay.model.MonthlyAmortizationEntity;

import java.util.List;

@Mapper
public interface AmortizationScheduleMapper {

    AmortizationScheduleMapper INSTANCE = Mappers.getMapper(AmortizationScheduleMapper.class);

    @Mapping(target = "amortizations", source = "amortizationList")
    AmortizationScheduleResponse toAmortizationScheduleResponse(AmortizationScheduleEntity amortizationScheduleEntity);
    List<AmortizationScheduleResponse> toAmortizationScheduleResponseList(List<AmortizationScheduleEntity> amortizationScheduleEntityList);

    MonthlyAmortization toMonthlyAmortization(MonthlyAmortizationEntity monthlyAmortizationEntity);
    List<MonthlyAmortization> toMonthlyAmortizationList(List<MonthlyAmortizationEntity> monthlyAmortizationEntityList);
}
