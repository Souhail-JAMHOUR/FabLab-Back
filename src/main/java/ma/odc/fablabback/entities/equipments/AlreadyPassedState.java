package ma.odc.fablabback.entities.equipments;

import org.springframework.stereotype.Component;

@Component
public class AlreadyPassedState extends ReservationStates{




    // Static variable reference of single_instance

    private static AlreadyPassedState single_instance = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private AlreadyPassedState()
    {
        super("Already Passed State");

    }


    public static synchronized AlreadyPassedState getInstance()
    {
        if (single_instance == null)
            single_instance = new AlreadyPassedState();

        return single_instance;
    }

    @Override
    public void start(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");
    }

    @Override
    public void cancel(Reservation reservation) throws RuntimeException {
        throw new RuntimeException("changement de state invalide");

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


//    @Override
//    void printState() {
//
//    }
}
