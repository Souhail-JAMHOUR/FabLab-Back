package ma.odc.fablabback.repositories.equipments;

import java.time.LocalDate;
import java.util.List;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.enums.EReservationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentReservationRepository
    extends JpaRepository<EquipmentReservation, String> {
  List<EquipmentReservation> findEquipmentReservationByEquipment(Equipment equipment);

  List<EquipmentReservation>
      findAllByEquipmentAndReservation_EndDateBetweenOrEquipmentAndReservation_EndDateBetween(
          Equipment equipment1,
          LocalDate startDate,
          LocalDate endDate,
          Equipment equipment2,
          LocalDate startDate2,
          LocalDate endDate2);

  List<EquipmentReservation>
      findAllByEquipmentAndReservation_ReservationStateInAndReservation_StartDateBetweenOrEquipmentAndReservation_ReservationStateInAndReservation_EndDateBetween(
          Equipment equipment1,
          List<EReservationState> states1,
          LocalDate startDate1,
          LocalDate endDate1,
          Equipment equipment2,
          List<EReservationState> states2,
          LocalDate startDate2,
          LocalDate endDate2);

  Page<EquipmentReservation>
      findAllByEquipmentAndReservation_ReservationStateInAndReservation_StartDateBetweenOrEquipmentAndReservation_ReservationStateInAndReservation_EndDateBetween(
          Equipment equipment1,
          List<EReservationState> states1,
          LocalDate startDate1,
          LocalDate endDate1,
          Equipment equipment2,
          List<EReservationState> states2,
          LocalDate startDate2,
          LocalDate endDate2,
          Pageable pageable);

  Page<EquipmentReservation> findAllByEquipmentOrderByReservation_StartDateAsc(
      Equipment equipment, Pageable pageable);

  List<EquipmentReservation>
      findAllByEquipmentAndReservation_ReservationStateInAndReservation_StartDateLessThanEqualAndReservation_EndDateGreaterThanEqual(
          Equipment equipment,
          List<EReservationState> states,
          LocalDate startDate,
          LocalDate endDate);
}
