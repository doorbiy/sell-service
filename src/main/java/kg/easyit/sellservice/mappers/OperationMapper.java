package kg.easyit.sellservice.mappers;

import kg.easyit.sellservice.models.dtos.OperationDto;
import kg.easyit.sellservice.models.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    Operation mapToOperation(OperationDto operationDto);

    OperationDto mapToOperationDto(Operation operation);
}