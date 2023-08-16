package ma.odc.fablabback.repositories;

import ma.odc.fablabback.entities.equipments.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
