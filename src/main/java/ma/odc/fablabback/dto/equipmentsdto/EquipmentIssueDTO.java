package ma.odc.fablabback.dto.equipmentsdto;

import java.time.LocalDate;
import lombok.Data;
import ma.odc.fablabback.enums.EquipmentIssueState;

@Data
public class EquipmentIssueDTO {

  private String issueId;

  private EquipmentIssueState equipmentIssueState;

  private EquipmentReservationDTO equipmentReservationDTO;

  //  private String equipmentReservationId;

  private String description;

  private LocalDate reportedAt;
}
