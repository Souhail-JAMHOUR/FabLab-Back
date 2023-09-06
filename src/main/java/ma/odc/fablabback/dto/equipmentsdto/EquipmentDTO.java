package ma.odc.fablabback.dto.equipmentsdto;

import lombok.Data;

@Data
public class EquipmentDTO {
  private long equipmentId;
  private String name;
  private int quantity;
  private CategoryDTO categoryDTO;
}
