package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class Category {
    @Id
    private String id;

    private String description;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Equipment> labeledEquipments;

}
