package entrance.repository;

import entrance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
