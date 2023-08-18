package ma.odc.fablabback.entities.Docs;

import jakarta.persistence.Embeddable;

@Embeddable
public class IDocumentationImpl implements IDocumentation {
  /*    validate to use the separate documentation file
  inject the id of the documentation or the whole object*/

  //    @Transient
  private String documentationContent;

  @Override
  public String getContent() {
    return this.documentationContent;
  }
}
