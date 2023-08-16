package ma.odc.fablabback.dto.equipments;

import java.util.Date;
import lombok.Data;

@Data
public class ReservationDTO {
  private String reservationId;
  private Date startDate;
  private Date endDate;
}
