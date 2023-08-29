package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.Exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.Exceptions.ReservationNotFoundException;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.requests.ReservationRequest;

public interface IReservationService {

  ReservationDTO addNewReservation(ReservationRequest reservationRequest);

  ReservationDTO getReservation(String id) throws ReservationNotFoundException;

  ReservationDTO approveReservation(String id) throws ReservationNotFoundException, AppUsersNotFoundException;

  ReservationDTO rejectReservation(String id) throws ReservationNotFoundException;

  List<ReservationDTO> getAllReservations();
}
