package ma.odc.fablabback.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.exceptions.ProjectNotFoundException;
import ma.odc.fablabback.exceptions.UnAuthorizedProjectAction;
import ma.odc.fablabback.requests.ProjectRequest;
import ma.odc.fablabback.requests.ProjectUpdateRequest;
import ma.odc.fablabback.services.impl.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {
  private final ProjectService projectService;

  @GetMapping("/all")
  public ResponseEntity<List<ProjectDTO>> getAllProjects() {
    List<ProjectDTO> allProjects = projectService.getAllProjects();
    return ResponseEntity.ok(allProjects);
  }

  @GetMapping("/active")
  public ResponseEntity<List<ProjectDTO>> getActiveProjects() {
    List<ProjectDTO> approvedProjects = projectService.getApprovedProjects();
    return ResponseEntity.ok(approvedProjects);
  }

  @GetMapping("/pending")
  public ResponseEntity<List<ProjectDTO>> getPendingProjects() {
    List<ProjectDTO> pendingProjects = projectService.getPendingProjects();
    return ResponseEntity.ok(pendingProjects);
  }

  @PostMapping("/new")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectRequest request)
      throws AppUsersNotFoundException {
    ProjectDTO projectDTO = projectService.addNewProject(request);
    return ResponseEntity.ok(projectDTO);
  }

  @PostMapping("/approve/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ProjectDTO> approveProject(@PathVariable long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO projectDTO = projectService.approveProject(id);
    return ResponseEntity.ok(projectDTO);
  }

  @PostMapping("/disable/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<ProjectDTO> disableProject(@PathVariable long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO projectDTO = projectService.disableProject(id);
    return ResponseEntity.ok(projectDTO);
  }

  @PutMapping("/update")
  @PreAuthorize("hasAuthority('SCOPE_MEMBER')")
  public ResponseEntity<ProjectDTO> updateProject(
      @RequestBody ProjectUpdateRequest projectUpdateRequest)
      throws ProjectNotFoundException, AppUsersNotFoundException, UnAuthorizedProjectAction {
    ProjectDTO projectDTO = projectService.updateProject(projectUpdateRequest);
    return ResponseEntity.ok(projectDTO);
  }

  @PostMapping("/reject/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER_ADMIN') or hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity rejectProject(@PathVariable long id)
      throws ProjectNotFoundException, UnAuthorizedProjectAction, AppUsersNotFoundException {
    ProjectDTO projectDTO = projectService.rejectProject(id);
    return ResponseEntity.ok(projectDTO);
  }

  @GetMapping("/search")
  public List<ProjectDTO> searchProjects(
      @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    return projectService.searchProject("%" + keyword + "%");
  }
}
