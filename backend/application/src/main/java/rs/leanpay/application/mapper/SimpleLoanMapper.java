package rs.leanpay.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import rs.leanpay.application.dto.SimpleLoanResponse;
import rs.leanpay.model.SimpleLoanEntity;

import java.util.List;

@Mapper
public interface SimpleLoanMapper {

    SimpleLoanMapper INSTANCE = Mappers.getMapper(SimpleLoanMapper.class);

    SimpleLoanEntity toSimpleLoanEntity(SimpleLoanResponse simpleLoanResponse);
    SimpleLoanResponse toSimpleLoanResponse(SimpleLoanEntity simpleLoanEntity);

    List<SimpleLoanEntity> toSimpleLoanEntityList(List<SimpleLoanResponse> simpleLoanResponses);
    List<SimpleLoanResponse> toSimpleLoanResponseList(List<SimpleLoanEntity> simpleLoanEntities);

}
