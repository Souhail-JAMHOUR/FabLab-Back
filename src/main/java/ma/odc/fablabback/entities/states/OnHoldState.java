//package ma.odc.fablabback.entities.states;
//
//import ma.odc.fablabback.entities.equipments.Reservation;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OnHoldState implements ReservationState {
//
//
//  @Override
//  public void start(Reservation reservation) {
//    System.out.println("Action unauthorized");
//  }
//
//  @Override
//  public void cancel(Reservation reservation) {
//    System.out.println("Action unauthorized");
//
//  }
//
//  @Override
//  public void confirm(Reservation reservation) {
//    reservation.setReservationState(new ConfrimedState());
//  }
//
//  @Override
//  public void reject(Reservation reservation) {
//    reservation.setReservationState(new RejectedState());
//  }
//
//  @Override
//  public void delete(Reservation reservation) {
//    System.out.println("Action unauthorized");
//
//  }
//
//  @Override
//  public void end(Reservation reservation) {
//    System.out.println("Action unauthorized");
//
//  }
//}
