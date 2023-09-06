package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.exceptions.ProjectNotFoundException;
import ma.odc.fablabback.exceptions.UnAuthorizedProjectAction;
import ma.odc.fablabback.requests.ProjectRequest;
import ma.odc.fablabback.requests.ProjectUpdateRequest;

public interface IProjectService {

  List<ProjectDTO> getMyProjects() throws AppUsersNotFoundException;

  List<ProjectDTO> getMemberProjects(MemberDTO memberDTO);

  List<ProjectDTO> getPendingProjects();

  List<ProjectDTO> getApprovedProjects();

  List<ProjectDTO> getAllProjects();

  ProjectDTO addNewProject(ProjectRequest request) throws AppUsersNotFoundException;

  ProjectDTO getProject(Long id) throws ProjectNotFoundException;

  ProjectDTO approveProject(Long id)
      throws ProjectNotFoundException, AppUsersNotFoundException, UnAuthorizedProjectAction;

  ProjectDTO approveProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException;

  Project setProjectAdmin(Project project) throws AppUsersNotFoundException;

  ProjectDTO updateProject(ProjectUpdateRequest projectUpdateRequest)
      throws ProjectNotFoundException, AppUsersNotFoundException, UnAuthorizedProjectAction;

  ProjectDTO disableProject(long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException;

  ProjectDTO disableProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException;

  ProjectDTO rejectProject(ProjectDTO projectDTO)
      throws UnAuthorizedProjectAction, AppUsersNotFoundException;

  ProjectDTO rejectProject(Long id)
          throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException;
}
