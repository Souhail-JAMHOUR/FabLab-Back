package ma.odc.fablabback.repositories.Users;

import java.util.List;
import java.util.Optional;
import ma.odc.fablabback.entities.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByAppUsersname(String username);

    @Query("select a from Admin a where a.appUsersname like :kw")
    List<Admin> searchAdmins(@Param("kw") String keyword);
}
