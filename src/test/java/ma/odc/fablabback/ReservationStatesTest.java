package ma.odc.fablabback;

import ma.odc.fablabback.entities.equipments.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.availability.ReadinessState;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ReservationStatesTest {

    @Test
    public void testStates() throws RuntimeException {
        Reservation reservation =  new Reservation();
        Reservation reservation1 =  new Reservation();
        Reservation reservation2=  new Reservation();
        Reservation reservation3 =  new Reservation();



        System.out.println("----------------------------Reservation 0 ------------------");

        System.out.println(reservation.getState() instanceof ConfirmedState );
        System.out.println(reservation.getState() instanceof OnHoldState );
    reservation.confirm();
   reservation.delete();

        System.out.println(reservation.getState() instanceof ConfirmedState );
        reservation.end();

        System.out.println(reservation.getState() instanceof AlreadyPassedState );

        System.out.println("----------------------------Reservation 1 ------------------");

        System.out.println(reservation1.getState() instanceof ConfirmedState );
        System.out.println(reservation1.getState() instanceof OnHoldState );
        reservation1.reject();
        System.out.println(reservation1.getState() instanceof RejectedState );

        reservation1.start();
        System.out.println(reservation1.getState() instanceof RejectedState );


        System.out.println("----------------------------Reservation 2 ------------------");

        System.out.println(reservation2.getState() instanceof ConfirmedState );
        System.out.println(reservation2.getState() instanceof OnHoldState );
        reservation2.confirm();
        System.out.println(reservation2.getState() instanceof ConfirmedState );
        reservation2.start();
        System.out.println(reservation2.getState() instanceof InProgressState );
        reservation2.start();
        System.out.println(reservation2.getState() instanceof RejectedState );
        reservation2.end();
        System.out.println(reservation2.getState() instanceof AlreadyPassedState );



    }
}
