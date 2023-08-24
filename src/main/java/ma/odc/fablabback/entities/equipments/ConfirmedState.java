package ma.odc.fablabback.entities.equipments;


import org.springframework.stereotype.Component;

@Component
public class ConfirmedState extends ReservationStates{
    private  RejectedState rej;
    private  InProgressState inp;




    public void start(Reservation res ){
            res.setState(inp);

    }
    public void cancel(Reservation res){
        res.setState(rej);
    }

    @Override
    public void printState() {
        System.out.println("this Reservation ois confirmed");

    }
}
