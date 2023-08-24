package ma.odc.fablabback.entities.states;

import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
public class RejectedState extends ReservationState {

  public RejectedState(Reservation reservation) {
    super(reservation);
  }

  @Override
  public void printState() {
    System.out.println("this Reservation is Rejected");
  }

  @Override
  void confirmReservation() {
      System.out.println("Action unauthorized");

  }

  @Override
  void rejectReservation() {
      System.out.println("Action unauthorized");

  }

  @Override
  void cancleReservation() {
      System.out.println("Action unauthorized");

  }
}
