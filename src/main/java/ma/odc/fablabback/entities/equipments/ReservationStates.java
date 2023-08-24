package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.enums.EReservationState;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public abstract class ReservationStates {
    @Enumerated(EnumType.STRING)
    private EReservationState stateName = EReservationState.ONHOLD;

    public abstract void start(Reservation reservation) throws RuntimeException;
    public abstract void cancel(Reservation reservation) throws RuntimeException;
    public abstract void confirm(Reservation reservation) throws RuntimeException;
    public abstract void reject(Reservation reservation) throws RuntimeException;
    public abstract void delete(Reservation reservation) throws RuntimeException;
    public abstract void end(Reservation reservation)throws RuntimeException;


    //abstract void  printState();
}