package ma.odc.fablabback.entities.equipments;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class InProgressState extends ReservationStates{





    @Override
    public void end(Reservation res) throws RuntimeException{
        res.setState(AlreadyPassedState.getInstance());
    }

    @Override
    public void start(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

    }

    @Override
    public void cancel(Reservation res) throws RuntimeException{
        res.setState(RejectedState.getInstance());
    }

    @Override
    public void confirm(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

    }

    @Override
    public void reject(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

    }

    @Override
    public void delete(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

    }


    // Static variable reference of single_instance

    private static InProgressState single_instance = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private InProgressState()
    {
        super("In Progress State");

    }


    public static synchronized InProgressState getInstance()
    {
        if (single_instance == null)
            single_instance = new InProgressState();

        return single_instance;
    }



//    @Override
//    public void printState() {
//        System.out.println("this Reservation is In Progress");
//
//    }
}
