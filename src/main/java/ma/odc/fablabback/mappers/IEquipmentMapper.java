package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;

public interface IEquipmentMapper {
  EquipmentDTO equipmentToDTO(Equipment equipment);

  Equipment dtoToEquipment(EquipmentDTO equipmentDTO);

  EquipmentReservationDTO equipmentReservationToDTO(EquipmentReservation equipmentReservation);

  EquipmentReservation dtoToEquipmentReservation(EquipmentReservationDTO equipmentReservationDTO);

  ReservationDTO reservationToDTO(Reservation reservation);

  Reservation dtoToReservation(ReservationDTO reservationDTO);

}
