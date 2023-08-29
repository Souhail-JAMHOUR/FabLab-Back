package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByAppUsersname(String username);
}
