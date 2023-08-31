package ma.odc.fablabback.requests;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ReservationRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private List<EquipmentReservationRequest> equipmentReservationRequests;
}
