package ma.odc.fablabback.repositories.Users;

import java.util.List;
import java.util.Optional;
import ma.odc.fablabback.entities.Users.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByAppUsersname(String username);

  @Query("select m from Member m where m.appUsersname like :kw")
  List<Member> searchMember(@Param("kw") String keyword);
}
