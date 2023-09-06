package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.Docsdto.DocumentationDTO;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.entities.Docs.Documentation;
import ma.odc.fablabback.entities.Docs.Project;
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

  ProjectDTO projectToDTO(Project project);

  Project dtoToProject(ProjectDTO projectDTO);

  Documentation dtoToDocumentation(DocumentationDTO documentationDTO);

  DocumentationDTO documentationToDTO(Documentation documentation);
}
