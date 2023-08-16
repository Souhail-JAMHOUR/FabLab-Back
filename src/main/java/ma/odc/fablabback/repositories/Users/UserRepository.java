package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
