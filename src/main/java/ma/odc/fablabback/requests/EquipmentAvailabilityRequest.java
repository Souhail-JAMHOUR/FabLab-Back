package ma.odc.fablabback.requests;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentAvailabilityRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private long equipmentId;
}
