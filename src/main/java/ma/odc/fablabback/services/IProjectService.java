package ma.odc.fablabback.services;

import ma.odc.fablabback.dto.Docsdto.DocumentationDTO;
import ma.odc.fablabback.dto.Docsdto.ProjectDTO;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;

public interface IProjectService {

  ProjectDTO addNewProject(MemberDTO memberDTO, DocumentationDTO documentationDTO);

  ProjectDTO getProject(Long id);
  ProjectDTO approveProject(Long id, AdminDTO adminDTO);
}
