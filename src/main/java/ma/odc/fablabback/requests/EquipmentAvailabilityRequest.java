package ma.odc.fablabback.requests;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EquipmentAvailabilityRequest {
  private LocalDate startDate;
  private LocalDate endDate;

  private long equipmenId;
}
