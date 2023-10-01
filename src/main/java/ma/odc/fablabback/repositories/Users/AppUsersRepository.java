package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUsersRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByAppUsersname(String username);

  Optional<AppUser> findByEmail(String email);

  @Query("select u from AppUser u where u.appUsersname like :kw")
  Page<AppUser> searchUser(@Param("kw") String keyword, Pageable pageable);
}
