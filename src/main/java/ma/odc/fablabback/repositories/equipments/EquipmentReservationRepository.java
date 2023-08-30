package ma.odc.fablabback.repositories.equipments;

import java.time.LocalDate;
import java.util.List;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentReservationRepository extends JpaRepository<EquipmentReservation, Long> {
  List<EquipmentReservation> findEquipmentReservationByEquipment(Equipment equipment);

  List<EquipmentReservation>
      findAllByEquipmentAndReservation_EndDateBetweenOrEquipmentAndReservation_EndDateBetween(
          Equipment equipment1,
          LocalDate startDate,
          LocalDate endDate,
          Equipment equipment2,
          LocalDate startDate2,
          LocalDate endDate2);
}
