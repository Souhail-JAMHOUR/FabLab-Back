package ma.odc.fablabback.requests;

import lombok.Data;
import ma.odc.fablabback.dto.Docsdto.DocumentationDTO;

@Data
public class ProjectRequest {
  String title;
  DocumentationDTO documentationDTO;
  String imageUrl;
}
