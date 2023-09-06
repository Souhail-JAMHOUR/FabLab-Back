package ma.odc.fablabback.entities.Docs;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documentation implements IDocumentation {
  /*    validate to use the separate documentation file
  inject the id of the documentation or the whole object*/

  //    @Transient
  private String documentationContent;

  @Override
  public String getContent() {
    return this.documentationContent;
  }
}
