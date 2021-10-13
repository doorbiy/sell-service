package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.CodeDto;
import kg.easyit.sellservice.models.dtos.UserDto;
import kg.easyit.sellservice.models.entities.Code;

public interface CodeService {

    void saveCode(CodeDto codeDto);

    Code findLastCode(UserDto userDto);

    void sendCode(UserDto userDto);
}