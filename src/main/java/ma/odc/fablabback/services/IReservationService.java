package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.requests.ReservationRequest;

public interface IReservationService {

  ReservationDTO addNewReservation(ReservationRequest reservationRequest)
      throws EquipmentNotFoundException, UnsatisfiedRequirementException;

  ReservationDTO getReservationDto(String id) throws ReservationNotFoundException;

  Reservation getReservation(String id) throws ReservationNotFoundException;

  Reservation setReservationAdmin(Reservation reservation) throws AppUsersNotFoundException;

  List<ReservationDTO> getAllReservations();

  boolean verifyReservationEquipment(ReservationDTO reservationDTO)
      throws EquipmentNotFoundException;

  // ! Approve

  ReservationDTO approveReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          UnAuthorizedReservationAction;

  ReservationDTO approveReservation(String id)
      throws ReservationNotFoundException,
          AppUsersNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          UnAuthorizedReservationAction;

  // ! Reject
  ReservationDTO rejectReservation(String id) throws ReservationNotFoundException;

  ReservationDTO rejectReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException;

  // ! Cancel
  ReservationDTO cancelReservation(String id);

  ReservationDTO cancelReservation(ReservationDTO reservationDTO);
  // ! End
  ReservationDTO endReservation(ReservationDTO reservationDTO);

  ReservationDTO endReservation(String id);

  // ! Start

  ReservationDTO startReservation(String id)
      throws ReservationNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          AppUsersNotFoundException,
          UnAuthorizedReservationAction;

  ReservationDTO startReservation(ReservationDTO reservationDTO);
}
