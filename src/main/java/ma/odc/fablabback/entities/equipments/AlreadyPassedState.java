package ma.odc.fablabback.entities.equipments;

import org.springframework.stereotype.Component;

@Component
public class AlreadyPassedState extends ReservationStates{
    public AlreadyPassedState() {
        this.setStateName("Already Passed");
    }

    @Override
    void printState() {

    }
}
