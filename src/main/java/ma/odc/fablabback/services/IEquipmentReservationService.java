package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.exceptions.UnsatisfiedRequirementException;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.requests.EquipmentReservationRequest;

public interface IEquipmentReservationService {
  List<EquipmentReservationDTO> getAllEquipmentReservations();

  List<EquipmentReservationDTO> getEquipmentReservationByEquipment(EquipmentDTO equipmentDTO);

  List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDatesAndState(
          EquipmentAvailabilityRequest request) throws EquipmentNotFoundException;

  List<EquipmentReservationDTO> getEquipmentReservationByEquipmentAndDates(EquipmentAvailabilityRequest request) throws EquipmentNotFoundException;

    EquipmentReservationDTO createEquipmentReservation(
            EquipmentReservationRequest equipmentReservationRequest, Reservation reservation)
        throws EquipmentNotFoundException, UnsatisfiedRequirementException;

  EquipmentReservationDTO saveEquipmentReservation(
          EquipmentReservationDTO equipmentReservationDTO);
}
