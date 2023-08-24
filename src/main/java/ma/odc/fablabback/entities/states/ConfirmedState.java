package ma.odc.fablabback.entities.states;


import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ConfirmedState extends ReservationState {
    public ConfirmedState(Reservation reservation) {
        super(reservation);
    }

    @Override
    public void printState() {
        System.out.println("this Reservation ois confirmed");

    }

    @Override
    void confirmReservation() {
    System.out.println("This Reservation is already confirmed");
    }

    @Override
    void rejectReservation() {
    System.out.println("Try to cancel this reservation instead");
    }

    @Override
    void cancleReservation() {
    reservation.setState(new RejectedState(reservation));
    }

}
