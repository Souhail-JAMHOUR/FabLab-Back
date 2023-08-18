package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    private int requestedQuantity;
    @ManyToOne
    @JoinColumn(name = "EQUIPMENT_ID")
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;
}
