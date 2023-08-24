package ma.odc.fablabback.services;

import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.Exceptions.ReservationNotFoundExcetion;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.enums.EReservationState;
import ma.odc.fablabback.repositories.equipments.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private final ReservationRepository reservationRepository;

  Reservation confirmReservation(String id) throws ReservationNotFoundExcetion {
    Reservation reservation =
        reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundExcetion());

//    reservation.setState(new ConfirmedState(reservation));
    reservation.setStateName(EReservationState.CONFIRMED);
    return reservation;
  }
}
