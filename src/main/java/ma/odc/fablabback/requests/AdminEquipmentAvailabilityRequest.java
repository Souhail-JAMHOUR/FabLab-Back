package ma.odc.fablabback.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEquipmentAvailabilityRequest {
  private long equipmentId;
}
