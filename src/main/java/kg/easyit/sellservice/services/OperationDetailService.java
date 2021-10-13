package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.OperationDetailDto;

import java.util.List;

public interface OperationDetailService {

    void saveOperationDetails(List<OperationDetailDto> operationDetailDtoList);
}