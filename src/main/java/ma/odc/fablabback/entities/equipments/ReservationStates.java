package ma.odc.fablabback.entities.equipments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReservationStates {
     private  String stateName;

     public abstract void start(Reservation reservation) throws RuntimeException;
     public abstract void cancel(Reservation reservation) throws RuntimeException;
     public abstract void confirm(Reservation reservation) throws RuntimeException;
     public abstract void reject(Reservation reservation) throws RuntimeException;
     public abstract void delete(Reservation reservation) throws RuntimeException;
     public abstract void end(Reservation reservation)throws RuntimeException;


     //abstract void  printState();
}
