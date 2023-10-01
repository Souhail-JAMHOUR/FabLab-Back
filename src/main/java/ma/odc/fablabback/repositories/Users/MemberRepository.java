package ma.odc.fablabback.repositories.Users;

import java.util.Optional;
import ma.odc.fablabback.entities.Users.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByAppUsersname(String username);

  @Query("select m from Member m where m.appUsersname like :kw")
  Page<Member> searchMember(@Param("kw") String keyword, Pageable pageable);
}
