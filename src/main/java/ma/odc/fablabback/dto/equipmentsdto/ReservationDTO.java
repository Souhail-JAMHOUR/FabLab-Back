package ma.odc.fablabback.dto.equipmentsdto;

import java.util.Date;
import lombok.Data;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;

@Data
public class ReservationDTO {
  private String reservationId;
  private Date startDate;
  private Date endDate;
  private Admin admin;
  private Member member;
}
