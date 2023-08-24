package ma.odc.fablabback.entities.equipments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
public class RejectedState extends ReservationStates{




    // Static variable reference of single_instance

    private static RejectedState single_instance = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself


    public static synchronized RejectedState getInstance()
    {
        if (single_instance == null)
            single_instance = new RejectedState();

        return single_instance;
    }




    private RejectedState() {

       super("Rejected State");
    }

    @Override
    public void delete(Reservation res){
        // delete the rejected reservation from DB

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
    public void end(Reservation reservation) throws RuntimeException {

        throw new RuntimeException("changement de state invalide");
    }


    //@Override
   // public void printState() {
      //  System.out.println("this Reservation is Rejected");
   // }

}
