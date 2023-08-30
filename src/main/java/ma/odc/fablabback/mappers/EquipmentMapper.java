package ma.odc.fablabback.mappers;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentMapper implements IEquipmentMapper {
  private final UsersMapperImpl usersMapper;
  @Override
  public EquipmentDTO equipmentToDTO(Equipment equipment) {
    EquipmentDTO equipmentDTO = new EquipmentDTO();
    BeanUtils.copyProperties(equipment, equipmentDTO);
    return equipmentDTO;
  }

  @Override
  public Equipment dtoToEquipment(EquipmentDTO equipmentDTO) {
    Equipment equipment = new Equipment();
    BeanUtils.copyProperties(equipmentDTO, equipment);
    return equipment;
  }

  @Override
  public EquipmentReservationDTO equipmentReservationToDTO(
      EquipmentReservation equipmentReservation) {
    EquipmentReservationDTO equipmentReservationDTO = new EquipmentReservationDTO();
    BeanUtils.copyProperties(equipmentReservation, equipmentReservationDTO);
    Equipment equipment = equipmentReservation.getEquipment();
    EquipmentDTO equipmentDTO = equipmentToDTO(equipment);
    equipmentReservationDTO.setEquipmentDTO(equipmentDTO);
    return equipmentReservationDTO;
  }

  @Override
  public EquipmentReservation dtoToEquipmentReservation(
      EquipmentReservationDTO equipmentReservationDTO) {
    EquipmentReservation equipmentReservation = new EquipmentReservation();
    BeanUtils.copyProperties(equipmentReservationDTO, equipmentReservation);
    return equipmentReservation;
  }

  @Override
  public ReservationDTO reservationToDTO(Reservation reservation) {
    ReservationDTO reservationDTO = new ReservationDTO();
    List<EquipmentReservation> equipmentReservationList = reservation.getEquipmentReservationList();
    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    BeanUtils.copyProperties(reservation, reservationDTO);
    for(EquipmentReservation er : equipmentReservationList){
      EquipmentReservationDTO equipmentReservationDTO = equipmentReservationToDTO(er);
      equipmentReservationDTOS.add(equipmentReservationDTO);
    }
    reservationDTO.setMember(usersMapper.membreToDTO(reservation.getMember()));
    if(reservation.getAdmin()!=null){
      reservationDTO.setAdmin(usersMapper.adminToDTO(reservation.getAdmin()));
    }
    reservationDTO.setEquipmentReservationListDTO(equipmentReservationDTOS);
    return reservationDTO;
  }

  @Override
  public Reservation dtoToReservation(ReservationDTO reservationDTO) {
    Reservation reservation = new Reservation();
    BeanUtils.copyProperties(reservationDTO, reservation);
    return reservation;
  }
}
