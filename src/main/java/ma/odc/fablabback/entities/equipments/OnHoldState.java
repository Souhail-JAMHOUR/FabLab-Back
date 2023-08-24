package ma.odc.fablabback.entities.equipments;

import ma.odc.fablabback.enums.EReservationState;
import org.springframework.stereotype.Component;

@Component
public class OnHoldState extends ReservationStates {

  private static OnHoldState single_instance = null;

  // Constructor
  // Here we will be creating private constructor
  // restricted to this class itself
  private OnHoldState() {
    super(EReservationState.ONHOLD);
  }

  public static synchronized OnHoldState getInstance() {
    if (single_instance == null) single_instance = new OnHoldState();

    return single_instance;
  }

  @Override
  public void start(Reservation reservation) throws RuntimeException {
    throw new RuntimeException("changement de state invalide");
  }

  @Override
  public void cancel(Reservation reservation) throws RuntimeException {
    throw new RuntimeException("changement de state invalide");
  }

  @Override
  public void confirm(Reservation res) {
    res.setState(ConfirmedState.getInstance());
  }

  // Static variable reference of single_instance

  @Override
  public void reject(Reservation res) {
    res.setState(RejectedState.getInstance());
  }

  @Override
  public void delete(Reservation reservation) throws RuntimeException {

    throw new RuntimeException("changement de state invalide");
  }

  @Override
  public void end(Reservation reservation) throws RuntimeException {
    throw new RuntimeException("changement de state invalide");
  }

  // @Override
  // void printState() {
  //  System.out.println("this Reservation is On Hold");
  // }
}
