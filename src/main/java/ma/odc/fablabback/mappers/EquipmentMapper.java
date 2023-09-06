package ma.odc.fablabback.mappers;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.Docsdto.DocumentationDTO;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.dto.equipmentsdto.CategoryDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.dto.equipmentsdto.ReservationDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Docs.Documentation;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.equipments.Category;
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
    equipmentDTO.setEquipmentId(equipment.getId());
    equipmentDTO.setCategoryDTO(categoryToDto(equipment.getCategory()));
    BeanUtils.copyProperties(equipment, equipmentDTO);
    return equipmentDTO;
  }

  @Override
  public Equipment dtoToEquipment(EquipmentDTO equipmentDTO) {
    Equipment equipment = new Equipment();
    equipment.setCategory(dtoToCategory(equipmentDTO.getCategoryDTO()));
    equipment.setName(equipmentDTO.getName());
    equipment.setId(equipmentDTO.getEquipmentId());
    equipment.setQuantity(equipmentDTO.getQuantity());
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
    equipmentReservation.setEquipment(dtoToEquipment(equipmentReservationDTO.getEquipmentDTO()));
    BeanUtils.copyProperties(equipmentReservationDTO, equipmentReservation);
    return equipmentReservation;
  }

  @Override
  public ReservationDTO reservationToDTO(Reservation reservation) {
    ReservationDTO reservationDTO = new ReservationDTO();
    List<EquipmentReservation> equipmentReservationList = reservation.getEquipmentReservationList();
    List<EquipmentReservationDTO> equipmentReservationDTOS = new ArrayList<>();
    BeanUtils.copyProperties(reservation, reservationDTO);
    for (EquipmentReservation er : equipmentReservationList) {
      EquipmentReservationDTO equipmentReservationDTO = equipmentReservationToDTO(er);
      equipmentReservationDTOS.add(equipmentReservationDTO);
    }
    reservationDTO.setMember(usersMapper.membreToDTO(reservation.getMember()));
    reservationDTO.setReservationState(reservation.getReservationState());
    if (reservation.getAdmin() != null) {
      reservationDTO.setAdmin(usersMapper.adminToDTO(reservation.getAdmin()));
    }
    reservationDTO.setEquipmentReservationListDTO(equipmentReservationDTOS);
    return reservationDTO;
  }

  @Override
  public Reservation dtoToReservation(ReservationDTO reservationDTO) {
    Reservation reservation = new Reservation();
    List<EquipmentReservationDTO> equipmentReservationListDTO =
        reservationDTO.getEquipmentReservationListDTO();
    List<EquipmentReservation> equipmentReservations = new ArrayList<>();
    MemberDTO memberDTO = reservationDTO.getMember();
    if (reservationDTO.getAdmin() != null) {
      AdminDTO adminDTO = reservationDTO.getAdmin();
      reservation.setAdmin(usersMapper.dtoToAdmin(adminDTO));
    }

    reservation.setMember(usersMapper.dtoToMembre(memberDTO));

    for (EquipmentReservationDTO e : equipmentReservationListDTO) {
      equipmentReservations.add(dtoToEquipmentReservation(e));
    }
    reservation.setEquipmentReservationList(equipmentReservations);
    BeanUtils.copyProperties(reservationDTO, reservation);
    return reservation;
  }

  @Override
  public ProjectDTO projectToDTO(Project project) {
    ProjectDTO projectDTO = new ProjectDTO();
    BeanUtils.copyProperties(project, projectDTO);
    projectDTO.setTitle(project.getTitle());
    projectDTO.setSubmissionDate(project.getSubmissionDate());
    if (project.getAdmin() != null) {
      projectDTO.setAdminDTO(usersMapper.adminToDTO(project.getAdmin()));
    }
    projectDTO.setDocumentationDTO(documentationToDTO(project.getDocumentation()));

    projectDTO.setMemberDTO(usersMapper.membreToDTO(project.getMember()));
    return projectDTO;
  }

  @Override
  public Project dtoToProject(ProjectDTO projectDTO) {
    Project project = new Project();
    BeanUtils.copyProperties(projectDTO, project);
    project.setTitle(projectDTO.getTitle());
    project.setMember(usersMapper.dtoToMembre(projectDTO.getMemberDTO()));
    if (projectDTO.getAdminDTO() != null) {
      project.setAdmin(usersMapper.dtoToAdmin(projectDTO.getAdminDTO()));
    }
    project.setDocumentation(dtoToDocumentation(projectDTO.getDocumentationDTO()));

    project.setSubmissionDate(projectDTO.getSubmissionDate());
    return project;
  }

  @Override
  public Documentation dtoToDocumentation(DocumentationDTO documentationDTO) {
    Documentation documentation = new Documentation();
    documentation.setDocumentationContent(documentationDTO.getDocumentationContent());
    return documentation;
  }

  @Override
  public DocumentationDTO documentationToDTO(Documentation documentation) {
    DocumentationDTO documentationDTO = new DocumentationDTO();
    BeanUtils.copyProperties(documentation, documentationDTO);
    documentationDTO.setDocumentationContent(documentation.getDocumentationContent());
    return documentationDTO;
  }

  public CategoryDTO categoryToDto(Category category){
    CategoryDTO categoryDTO = new CategoryDTO();
    BeanUtils.copyProperties(category,categoryDTO);
    return categoryDTO;
  }


  public Category dtoToCategory(CategoryDTO categoryDTO){
    Category category = new Category();
    BeanUtils.copyProperties(categoryDTO,category);
    return category;
  }




}
