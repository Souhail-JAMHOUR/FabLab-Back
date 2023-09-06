package ma.odc.fablabback.requests;

import lombok.Data;
import ma.odc.fablabback.dto.Docsdto.DocumentationDTO;

@Data
public class ProjectUpdateRequest {
  private long projectId;
  private String title;
  private DocumentationDTO documentationDTO;
}
