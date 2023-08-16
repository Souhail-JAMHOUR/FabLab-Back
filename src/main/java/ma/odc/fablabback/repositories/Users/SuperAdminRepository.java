package ma.odc.fablabback.repositories.Users;

import ma.odc.fablabback.entities.Users.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin,Long> {
}
