package ma.odc.fablabback.repositories.equipments;

import java.util.List;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,String> {
    List<Reservation> findAllByMember(Member member);
}
