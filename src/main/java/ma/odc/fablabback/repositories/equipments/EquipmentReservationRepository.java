package ma.odc.fablabback.repositories.equipments;

import java.time.LocalDate;
import java.util.List;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentReservationRepository extends JpaRepository<EquipmentReservation, Long> {
  List<EquipmentReservation>
      findEquipmentReservationByEquipmentAndReservation_StartDateAndReservation_EndDate(
          Equipment equipment, LocalDate startDate, LocalDate endDate);

  List<EquipmentReservation> findByReservation_EndDate(LocalDate endDate);

  List<EquipmentReservation> findEquipmentReservationByEquipment(Equipment equipment);
}
