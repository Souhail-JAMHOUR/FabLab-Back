package ma.odc.fablabback.requests;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;

@Data
public class ReservationRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private MemberDTO member;
  private List<EquipmentReservationDTO> equipmentReservationList;
}
