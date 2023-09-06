package ma.odc.fablabback.repositories.Users;

import java.util.List;
import java.util.Optional;
import ma.odc.fablabback.entities.Users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUsersRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByAppUsersname(String username);

  Optional<AppUser> findByEmail(String email);

  @Query("select u from AppUser u where u.appUsersname like :kw")
  List<AppUser> searchUser(@Param("kw") String keyword);
}
