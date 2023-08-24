package ma.odc.fablabback.dto.equipments;

import java.util.Date;
import lombok.Data;
import ma.odc.fablabback.entities.states.ReservationState;

@Data
public class ReservationDTO {
  private String reservationId;
  private Date startDate;
  private Date endDate;
  private ReservationState state;
}
