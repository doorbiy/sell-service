package kg.easyit.sellservice.dao;

import kg.easyit.sellservice.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long > {

    User findByLogin(String login);
}