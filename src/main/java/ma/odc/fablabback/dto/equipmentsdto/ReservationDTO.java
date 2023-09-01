package ma.odc.fablabback.dto.equipmentsdto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.enums.EReservationState;

@Data
public class ReservationDTO {
  private String reservationId;
  private EReservationState reservationState;
  private LocalDate startDate;
  private LocalDate endDate;
  private AdminDTO admin;
  private MemberDTO member;
  private List<EquipmentReservationDTO> equipmentReservationListDTO;
}
