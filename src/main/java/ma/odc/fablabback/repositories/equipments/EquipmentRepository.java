package ma.odc.fablabback.repositories.equipments;

import java.util.List;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.enums.EquipmentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
  Page<Equipment> findAllByCategory(EquipmentCategory category, Pageable pageable);

  @Query("select e from Equipment e where e.name like :kw")
  List<Equipment> searchEquipment(@Param("kw") String keyword);
}
