package ma.odc.fablabback.requests;

import lombok.Data;

@Data
public class EquipmentIssueRequest {

  private String equipmentReservationId;
  private String description;
}
