package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAppUsersname(String username);
}
