package ma.odc.fablabback.requests;

import java.util.Date;
import java.util.List;
import lombok.Data;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;

@Data
public class ReservationRequest {
  private Date startDate;
  private Date endDate;
  private MemberDTO member;
  private List<EquipmentReservationDTO> equipmentReservationList;
}
