package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;

public interface IReservationService {
    ReservationDTO addNewReservation(MemberDTO memberDTO, AdminDTO adminDTO, List<EquipmentReservationDTO> equipmentReservationDTOList);
    ReservationDTO getReservation(Long id);
}
