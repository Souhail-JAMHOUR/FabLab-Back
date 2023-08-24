package ma.odc.fablabback.entities.equipments;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class OnHoldState extends ReservationStates{





    @Override
    public void start(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");
    }

    @Override
    public void cancel(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");
    }

    @Override
    public void confirm(Reservation res){
        res.setState(ConfirmedState.getInstance());
    }
    @Override
    public void reject(Reservation res){
        res.setState(RejectedState.getInstance());
    }

    @Override
    public void delete(Reservation reservation) throws RuntimeException {

            throw new RuntimeException("changement de state invalide");



    }

    @Override
    public void end(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

    }


    // Static variable reference of single_instance

    private static OnHoldState single_instance = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private OnHoldState()
    {
        super("On Hold State");

    }


    public static synchronized OnHoldState getInstance()
    {
        if (single_instance == null)
            single_instance = new OnHoldState();

        return single_instance;
    }



    //@Override
   // void printState() {
      //  System.out.println("this Reservation is On Hold");
   // }
}
