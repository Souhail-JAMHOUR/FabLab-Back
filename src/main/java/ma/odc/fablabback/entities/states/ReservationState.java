package ma.odc.fablabback.entities.states;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
@NoArgsConstructor
public abstract class ReservationState {

  protected Reservation reservation;

  abstract void printState();

  abstract void confirmReservation();

  abstract void rejectReservation();

  abstract void cancleReservation();
}
