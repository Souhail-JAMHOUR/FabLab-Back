package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;

  @Column(name = "image_url")
  private String imageUrl;
  
  private int quantity;
  @ManyToOne private Category category;
}
