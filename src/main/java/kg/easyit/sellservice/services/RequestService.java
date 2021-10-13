package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.CodeDto;

public interface RequestService {

    void saveRequest(CodeDto codeDto, boolean value);

    int countFailedAttempts(CodeDto codeDto);
}