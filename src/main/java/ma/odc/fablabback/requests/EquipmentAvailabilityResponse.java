package ma.odc.fablabback.requests;

import java.time.LocalDate;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EquipmentAvailabilityResponse {
  private EquipmentDTO equipmentDTO;
  private Map<LocalDate,Integer> reservedQuantity;
}
