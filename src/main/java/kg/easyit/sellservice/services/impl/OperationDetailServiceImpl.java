package kg.easyit.sellservice.services.impl;

import kg.easyit.sellservice.dao.OperationDetailRepo;
import kg.easyit.sellservice.mappers.OperationDetailMapper;
import kg.easyit.sellservice.models.dtos.OperationDetailDto;
import kg.easyit.sellservice.services.OperationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationDetailServiceImpl implements OperationDetailService {
    @Autowired
    private OperationDetailRepo operationDetailRepo;

    @Override
    public void saveOperationDetails(List<OperationDetailDto> operationDetailDtoList) {

        for (OperationDetailDto element: operationDetailDtoList) {

            operationDetailRepo
                    .save(
                            OperationDetailMapper
                                    .INSTANCE
                                    .mapToOperationDetail(element)
                    );
        }
    }
}
