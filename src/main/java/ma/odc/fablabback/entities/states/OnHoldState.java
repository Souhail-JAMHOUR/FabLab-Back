package ma.odc.fablabback.entities.states;


import lombok.AllArgsConstructor;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OnHoldState extends ReservationState {

    public OnHoldState(Reservation reservation) {
        super(reservation);
    }

    @Override
    void printState() {
        System.out.println("this Reservation is On Hold");
    }

    @Override
    void confirmReservation() {
    reservation.setState(new ConfirmedState(reservation));
    }

    @Override
    void rejectReservation() {
    reservation.setState(new RejectedState(reservation));
    }

    @Override
    void cancleReservation() {
    System.out.println("Cannot cancel a pending reservation");
    }
    
}
