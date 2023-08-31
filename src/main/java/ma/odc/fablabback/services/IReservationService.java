package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.exceptions.ReservationNotFoundException;
import ma.odc.fablabback.exceptions.UnsatisfiedRequirementException;
import ma.odc.fablabback.requests.ReservationRequest;

public interface IReservationService {

  ReservationDTO addNewReservation(ReservationRequest reservationRequest);

  ReservationDTO getReservation(String id) throws ReservationNotFoundException;

  ReservationDTO approveReservation(String id)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException;

  ReservationDTO approveReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException, AppUsersNotFoundException, EquipmentNotFoundException, UnsatisfiedRequirementException;

  ReservationDTO rejectReservation(String id) throws ReservationNotFoundException;

  List<ReservationDTO> getAllReservations();

  boolean verifyReservationEquipment(ReservationDTO reservationDTO) throws EquipmentNotFoundException;

  ReservationDTO cancelReservation(String id);

  ReservationDTO cancelReservation(ReservationDTO reservationDTO);
}
