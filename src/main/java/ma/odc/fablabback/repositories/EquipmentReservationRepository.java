package ma.odc.fablabback.repositories;

import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentReservationRepository extends JpaRepository<EquipmentReservation,Long> {
}
