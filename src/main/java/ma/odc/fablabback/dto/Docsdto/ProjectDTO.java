package ma.odc.fablabback.dto.Docsdto;

import java.time.LocalDate;
import lombok.Data;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.enums.EProjectState;

@Data
public class ProjectDTO {
  private long projectId;
  private String title;
  private EProjectState projectState;
  private LocalDate submissionDate;
  private MemberDTO memberDTO;
  private AdminDTO adminDTO;
  private DocumentationDTO documentationDTO;
}
