package ma.odc.fablabback.repositories.equipments;

import ma.odc.fablabback.entities.equipments.EquipmentIssue;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentIssueRepository extends JpaRepository<EquipmentIssue, String> {
  Page<EquipmentIssue> findByEquipmentReservation_Reservation(
      Reservation reservation, Pageable pageable);
}
