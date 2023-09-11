package ma.odc.fablabback.dto.equipmentsdto;

import lombok.Data;

@Data
public class EquipmentReservationDTO {
  private String Id;

  private int requestedQuantity;

  private EquipmentDTO equipmentDTO;
}
