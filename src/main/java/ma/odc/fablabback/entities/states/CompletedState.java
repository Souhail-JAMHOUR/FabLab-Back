package ma.odc.fablabback.entities.states;

import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
public class CompletedState extends ReservationState {
    public CompletedState(Reservation reservation) {
        super(reservation);
    }

    @Override
    void printState() {
        System.out.println("this is an old reservation");

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
