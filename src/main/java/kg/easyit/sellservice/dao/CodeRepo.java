package kg.easyit.sellservice.dao;

import kg.easyit.sellservice.models.entities.Code;
import kg.easyit.sellservice.models.entities.User;
import kg.easyit.sellservice.models.enums.CodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Long> {

    Code findByUserAndCodeStatus(User user, CodeStatus status);
}