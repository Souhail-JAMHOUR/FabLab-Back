package ma.odc.fablabback.entities.equipments;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class OnHoldState extends ReservationStates{

    private   RejectedState rej;
    private ConfirmedState cnf;

    public OnHoldState() {
        super("On Hold ");

    }

    void confirm(Reservation res){
        res.setState(cnf);
    }
    void reject(Reservation res){
        res.setState(rej);
    }
    @Override
    void printState() {
        System.out.println("this Reservation is On Hold");
    }
}
