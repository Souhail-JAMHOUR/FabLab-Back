package ma.odc.fablabback.entities.equipments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public abstract class ReservationStates {
     private  String stateName;

    public ReservationStates() {
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    abstract void  printState();
}
