package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import org.springframework.beans.BeanUtils;

public class EquipmentMapperImpl implements IEquipmentMapper {
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
    BeanUtils.copyProperties(reservation, reservationDTO);
    return reservationDTO;
  }

  @Override
  public Reservation dtoToReservation(ReservationDTO reservationDTO) {
    Reservation reservation = new Reservation();
    BeanUtils.copyProperties(reservationDTO, reservation);
    return reservation;
  }
}
