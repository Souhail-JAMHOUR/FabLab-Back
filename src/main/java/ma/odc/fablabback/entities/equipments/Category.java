package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class Category {
    @Id
    private String id;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Equipment> labeledEquipments;

}
