package ma.odc.fablabback.repositories.Users;

import ma.odc.fablabback.entities.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
