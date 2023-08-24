package ma.odc.fablabback.entities.equipments;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class InProgressState extends ReservationStates{

   private  RejectedState rej;
    private  AlreadyPassedState alr;
    public InProgressState(){

    }


    void end(Reservation res){
        res.setState(alr);
    }
    void cancel(Reservation res){
        res.setState(rej);
    }

    @Override
    public void printState() {
        System.out.println("this Reservation is In Progress");

    }
}
