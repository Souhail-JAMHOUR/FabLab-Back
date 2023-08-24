package ma.odc.fablabback.entities.equipments;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.EReservationState;
import org.springframework.stereotype.Component;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String reservationId;

  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Temporal(TemporalType.DATE)
  private Date endDate;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Admin admin;

    @OneToMany(mappedBy = "reservation")
    private List<EquipmentReservation> equipmentReservationList;



    ////// states



    @Embedded
    private ReservationStates state= OnHoldState.getInstance();
  @OneToMany(mappedBy = "reservation")
  private List<EquipmentReservation> equipmentReservationList;
  @Enumerated(EnumType.STRING)
  private EReservationState stateName = EReservationState.ONHOLD;

    public  void start() throws RuntimeException{
        try {
            state.start(this);


        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }

    }

    public  void cancel() throws RuntimeException{
        try {

            state.cancel(this);
        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }

    }

    public  void confirm() throws RuntimeException{
        try {
            state.confirm(this);


        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }

    }

    public  void reject() throws RuntimeException{
        try {
            state.reject(this);

        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }

    }
    
    public  void delete() throws RuntimeException{

        try {
            state.delete(this);

        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }


    }

    public  void end()throws RuntimeException{
        try {
            state.end(this);

        }catch (RuntimeException r)
        {
            System.out.println(r.getMessage());
        }

    }
}
