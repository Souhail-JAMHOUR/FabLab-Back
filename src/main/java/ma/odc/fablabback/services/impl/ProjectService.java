package ma.odc.fablabback.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.enums.EProjectState;
import ma.odc.fablabback.exceptions.*;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Docs.ProjectRepository;
import ma.odc.fablabback.requests.ProjectRequest;
import ma.odc.fablabback.requests.ProjectUpdateRequest;
import ma.odc.fablabback.services.IProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
  private final EquipmentMapper equipmentMapper;
  private final UsersMapperImpl usersMapper;
  private final ProjectRepository projectRepository;
  private final MemberService memberService;
  private final AdminServiceImpl adminService;

  @Override
  public List<ProjectDTO> getMyProjects() throws AppUsersNotFoundException {
    MemberDTO connectedMember = memberService.getConnectedMember();
    List<ProjectDTO> projectDTOS =
        projectRepository.findByMember(usersMapper.dtoToMembre(connectedMember)).stream()
            .map(equipmentMapper::projectToDTO)
            .collect(Collectors.toList());
    return projectDTOS;
  }

  @Override
  public List<ProjectDTO> getMemberProjects(MemberDTO memberDTO) {
    List<ProjectDTO> projectDTOS =
        projectRepository.findByMember(usersMapper.dtoToMembre(memberDTO)).stream()
            .map(equipmentMapper::projectToDTO)
            .collect(Collectors.toList());
    return projectDTOS;
  }

  @Override
  public List<ProjectDTO> getPendingProjects() {
    List<ProjectDTO> projectDTOS =
        projectRepository
            .findAllByProjectStateOrderBySubmissionDateDesc(EProjectState.ON_HOLD)
            .stream()
            .map(equipmentMapper::projectToDTO)
            .collect(Collectors.toList());
    return projectDTOS;
  }

  @Override
  public List<ProjectDTO> getApprovedProjects() {
    List<ProjectDTO> projectDTOS =
        projectRepository
            .findAllByProjectStateOrderBySubmissionDateDesc(EProjectState.ACTIVE)
            .stream()
            .map(equipmentMapper::projectToDTO)
            .collect(Collectors.toList());
    return projectDTOS;
  }

  @Override
  public List<ProjectDTO> getAllProjects() {
    List<Project> projectList = projectRepository.findAllByOrderBySubmissionDateDesc();
    List<ProjectDTO> projectDTOS = new ArrayList<>();
    for (Project p : projectList) {
      projectDTOS.add(equipmentMapper.projectToDTO(p));
    }
    return projectDTOS;
  }

  @Override
  @Transactional(rollbackFor = AppUsersNotFoundException.class)
  public ProjectDTO addNewProject(ProjectRequest request) throws AppUsersNotFoundException {

    MemberDTO connectedMember = memberService.getConnectedMember();
    Project newProject =
        Project.builder()
            .projectState(EProjectState.ON_HOLD)
            .title(request.getTitle())
            .member(usersMapper.dtoToMembre(connectedMember))
            .submissionDate(LocalDate.now())
            .documentation(equipmentMapper.dtoToDocumentation(request.getDocumentationDTO()))
            .build();
    Project saved = projectRepository.save(newProject);
    return equipmentMapper.projectToDTO(saved);
  }

  @Override
  public ProjectDTO getProject(Long id) throws ProjectNotFoundException {
    Project project =
        projectRepository
            .findById(id)
            .orElseThrow(() -> new ProjectNotFoundException("No project found with id" + id));
    return equipmentMapper.projectToDTO(project);
  }

  @Override
  public ProjectDTO approveProject(Long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO projectDTO = getProject(id);
    return approveProject(projectDTO);
  }

  @Override
  public ProjectDTO approveProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException {
    Project project = equipmentMapper.dtoToProject(projectDTO);
    project.approveProject();
    Project savedProject = setProjectAdmin(project);
    return equipmentMapper.projectToDTO(savedProject);
  }

  @Override
  public Project setProjectAdmin(Project project) throws AppUsersNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String adminUsername = authentication.getName();
    AdminDTO adminByName = adminService.getAdminByUsername(adminUsername);
    Admin admin = usersMapper.dtoToAdmin(adminByName);
    project.setAdmin(admin);
    return projectRepository.save(project);
  }

  @Override
  public ProjectDTO updateProject(ProjectUpdateRequest projectUpdateRequest)
      throws ProjectNotFoundException, AppUsersNotFoundException, UnAuthorizedProjectAction {

    ProjectDTO projectDto = getProject(projectUpdateRequest.getProjectId());
    Project project = equipmentMapper.dtoToProject(projectDto);
    project.setDocumentation(
        equipmentMapper.dtoToDocumentation(projectUpdateRequest.getDocumentationDTO()));
    project.setSubmissionDate(LocalDate.now());
    project.setTitle(projectUpdateRequest.getTitle());
    project.setAdmin(null);
    MemberDTO connectedMember = memberService.getConnectedMember();

    // ! verify if the member is the owner

    if (project.getMember().getAppUsersname() != connectedMember.getAppUsersname()) {
      throw new UnAuthorizedProjectAction("You can only modify your own projects");
    }

    // ! Set the state to OnHold again
    project.disableProject();

    Project saved = projectRepository.save(project);
    return equipmentMapper.projectToDTO(saved);
  }

  @Override
  public ProjectDTO disableProject(long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO projectDTO = getProject(id);
    return disableProject(projectDTO);
  }

  @Override
  public ProjectDTO disableProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException {
    Project project = equipmentMapper.dtoToProject(projectDTO);
    project.disableProject();
    Project savedProject = setProjectAdmin(project);
    return equipmentMapper.projectToDTO(savedProject);
  }

  @Override
  public ProjectDTO rejectProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException {
    Project project = equipmentMapper.dtoToProject(projectDTO);
    project.rejectProject();
    Project savedProject = setProjectAdmin(project);
    return equipmentMapper.projectToDTO(savedProject);
  }

  @Override
  public ProjectDTO rejectProject(Long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO project = getProject(id);
    return rejectProject(project);
  }

  public List<ProjectDTO> searchProject(String kw) {
    List<ProjectDTO> collected =
        projectRepository.searchProject(kw).stream()
            .map(equipmentMapper::projectToDTO)
            .collect(Collectors.toList());
    return collected;
  }
}
