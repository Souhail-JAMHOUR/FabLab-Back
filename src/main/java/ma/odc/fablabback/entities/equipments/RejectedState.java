package ma.odc.fablabback.entities.equipments;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RejectedState extends ReservationStates{

    public RejectedState() {

        this.setStateName("rejected");
    }

    void delete(Reservation res){
        // delete the rejected reservation from DB

    }
    @Override
    public void printState() {
        System.out.println("this Reservation is Rejected");
    }

}
