package ma.odc.fablabback.repositories.equipments;

import ma.odc.fablabback.entities.equipments.EquipmentIssue;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentIssueRepository extends JpaRepository<EquipmentIssue, String> {
  List<EquipmentIssue> findByEquipmentReservation_Reservation(Reservation reservation);
}
