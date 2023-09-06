package ma.odc.fablabback.requests;

import lombok.Data;

@Data
public class EquipmentReservationRequest {
  private long equipmentId;
  private int requestedQuantity;
}
