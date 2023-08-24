package ma.odc.fablabback.entities.states;


import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
public class InProgressState extends ReservationState {

    public InProgressState(Reservation reservation){
        super(reservation);
    }
    @Override
    public void printState() {
        System.out.println("this Reservation is In Progress");

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
    reservation.setState(new RejectedState(reservation));
    }

}
