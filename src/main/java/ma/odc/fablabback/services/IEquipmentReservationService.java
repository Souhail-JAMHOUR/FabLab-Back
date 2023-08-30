package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;

public interface IEquipmentReservationService {
  List<EquipmentReservationDTO> getAllEquipmentReservations();

  List<EquipmentReservationDTO> getEquipmentReservationByEquipment(EquipmentDTO equipmentDTO);

  List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDates(EquipmentAvailabilityRequest request) throws EquipmentNotFoundException;
}
