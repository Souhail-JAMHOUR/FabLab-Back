package ma.odc.fablabback.repositories.equipments;

import ma.odc.fablabback.entities.equipments.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

}