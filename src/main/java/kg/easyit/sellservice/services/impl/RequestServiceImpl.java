package kg.easyit.sellservice.services.impl;

import kg.easyit.sellservice.dao.RequestRepo;
import kg.easyit.sellservice.mappers.CodeMapper;
import kg.easyit.sellservice.models.dtos.CodeDto;
import kg.easyit.sellservice.models.entities.Request;
import kg.easyit.sellservice.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepo requestRepo;

    @Override
    public void saveRequest(CodeDto checkUserCode, boolean value) {

        Request saveRequest = new Request();
        saveRequest
                .setCode(
                        CodeMapper
                                .INSTANCE
                                .mapToCode(checkUserCode));
        saveRequest.setSuccess(value);
        requestRepo.save(saveRequest);
    }

    @Override
    public int countFailedAttempts(CodeDto codeDto) {

        return requestRepo
                .countByCodeAndSuccess(
                        CodeMapper
                                .INSTANCE
                                .mapToCode(codeDto)
                        , false);
    }
}
