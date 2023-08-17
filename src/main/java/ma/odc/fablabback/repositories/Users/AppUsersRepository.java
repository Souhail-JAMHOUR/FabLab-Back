package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUsersRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByAppUsersname(String username);

  Optional<AppUser> findByEmail(String email);
}
