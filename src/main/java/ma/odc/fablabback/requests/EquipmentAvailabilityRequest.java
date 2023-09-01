package ma.odc.fablabback.requests;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentAvailabilityRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private long equipmentId;
}
