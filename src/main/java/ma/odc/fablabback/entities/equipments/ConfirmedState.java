package ma.odc.fablabback.entities.equipments;


import org.springframework.stereotype.Component;

@Component
public class ConfirmedState extends ReservationStates{





    @Override
    public void start(Reservation res ){
            res.setState(InProgressState.getInstance());

    }
    @Override
    public void cancel(Reservation res){
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

    @Override
    public void end(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");
    }


    // Static variable reference of single_instance

    private static ConfirmedState single_instance = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private ConfirmedState()
    {
        super("Confirmed State");

    }


    public static synchronized ConfirmedState getInstance()
    {
        if (single_instance == null)
            single_instance = new ConfirmedState();

        return single_instance;
    }





//    @Override
//    public void printState() {
//        System.out.println("this Reservation ois confirmed");
//
//    }
}
