package ma.odc.fablabback.enums;


import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.UnAuthorizedReservationAction;

public enum EReservationState {
  ONHOLD,
  IN_PROGRESS,
  CANCELED,
  CONFIRMED,
  REJECTED,
  ENDED;

  public void setConfirmed(Reservation reservation) throws UnAuthorizedReservationAction {
    switch (this){
      case ENDED -> throw new UnAuthorizedReservationAction("cannot confirm ended Reservation");
      case CONFIRMED -> throw new UnAuthorizedReservationAction("cannot confirm confirmed Reservation");
      case ONHOLD -> reservation.setReservationState(CONFIRMED);
      case CANCELED -> throw new UnAuthorizedReservationAction("cannot confirm a canceled Reservation");
      case REJECTED -> throw new UnAuthorizedReservationAction("cannot confirm a rejected Reservation");
      case IN_PROGRESS -> throw new UnAuthorizedReservationAction("cannot confirm a started Reservation");
      default -> throw new UnAuthorizedReservationAction("cannot perform action");
    }
  }

  public void setInProgress(Reservation reservation) throws UnAuthorizedReservationAction {
    switch (this){
      case ENDED -> throw new UnAuthorizedReservationAction("cannot confirm ended Reservation");
      case CONFIRMED -> reservation.setReservationState(IN_PROGRESS);
      case ONHOLD -> throw new UnAuthorizedReservationAction("cannot start an ended Reservation");
      case REJECTED -> throw new UnAuthorizedReservationAction("cannot start a rejected Reservation");
      case IN_PROGRESS -> throw new UnAuthorizedReservationAction("Reservation already in progress");
      case CANCELED -> throw new UnAuthorizedReservationAction("cannot start a canceled Reservation");
      default -> throw new UnAuthorizedReservationAction("cannot perform action");
    }
  }

  public void setRejected(Reservation reservation) throws UnAuthorizedReservationAction {
    switch (this){
      case ENDED -> throw new UnAuthorizedReservationAction("cannot reject a completed Reservation");
      case CONFIRMED -> throw new UnAuthorizedReservationAction("cannot reject a confirmed Reservation");
      case ONHOLD -> reservation.setReservationState(REJECTED);
      case REJECTED -> throw new UnAuthorizedReservationAction("Reservation already rejected");
      case IN_PROGRESS -> throw new UnAuthorizedReservationAction("cannot reject a started Reservation");
      default -> throw new UnAuthorizedReservationAction("cannot perform action");
    }
  }

  public void setCanceled(Reservation reservation) throws UnAuthorizedReservationAction {
    switch (this){
      case ENDED -> throw new UnAuthorizedReservationAction("cannot cancel a completed Reservation");
      case CONFIRMED -> reservation.setReservationState(CANCELED);
      case ONHOLD -> throw new UnAuthorizedReservationAction("cannot cancel a pending Reservation ");
      case REJECTED -> throw new UnAuthorizedReservationAction("cannot cancel a rejected Reservation");
      case IN_PROGRESS -> throw new UnAuthorizedReservationAction("cannot cancel a reservation while in progress");
      default -> throw new UnAuthorizedReservationAction("cannot perform action");
    }
  }


  public void setEnded(Reservation reservation) throws UnAuthorizedReservationAction {
    switch (this){
      case ENDED -> throw new UnAuthorizedReservationAction("Reservation already ended");
      case CONFIRMED -> throw new UnAuthorizedReservationAction("cannot end a confirmed Reservation");
      case ONHOLD -> throw new UnAuthorizedReservationAction("cannot end a pending reservation");
      case REJECTED -> throw new UnAuthorizedReservationAction("cannot end a rejected Reservation");
      case IN_PROGRESS -> reservation.setReservationState(ENDED);
      default -> throw new UnAuthorizedReservationAction("cannot perform action");
    }
  }

}
