package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.requests.ReservationRequest;

public interface IReservationService {

  ReservationDTO addNewReservation(ReservationRequest reservationRequest)
      throws EquipmentNotFoundException, UnsatisfiedRequirementException, AppUsersNotFoundException;

  List<ReservationDTO> getMemberReservations(MemberDTO memberDTO);

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
  ReservationDTO rejectReservation(String id)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;

  ReservationDTO rejectReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;

  // ! Cancel
  ReservationDTO cancelReservation(String id)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;

  ReservationDTO cancelReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;
  // ! End
  ReservationDTO endReservation(ReservationDTO reservationDTO)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;

  ReservationDTO endReservation(String id)
      throws ReservationNotFoundException, UnAuthorizedReservationAction, AppUsersNotFoundException;

  // ! Start

  ReservationDTO startReservation(String id)
      throws ReservationNotFoundException,
          EquipmentNotFoundException,
          UnsatisfiedRequirementException,
          AppUsersNotFoundException,
          UnAuthorizedReservationAction;

  ReservationDTO startReservation(ReservationDTO reservationDTO) throws UnAuthorizedReservationAction, AppUsersNotFoundException, ReservationNotFoundException;
}
