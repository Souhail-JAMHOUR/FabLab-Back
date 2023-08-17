package ma.odc.fablabback.repositories.equipments;

import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,String> {
}
