package ma.odc.fablabback.entities.states;

import jakarta.persistence.*;
import ma.odc.fablabback.entities.equipments.Reservation;

public interface ReservationState {

  void start(Reservation reservation);

  void cancel(Reservation reservation);

  void confirm(Reservation reservation);

  void reject(Reservation reservation);

  void delete(Reservation reservation);

  void end(Reservation reservation);
}
