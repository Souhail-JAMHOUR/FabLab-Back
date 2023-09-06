package ma.odc.fablabback.entities.Docs;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.EProjectState;
import ma.odc.fablabback.exceptions.UnAuthorizedProjectAction;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long projectId;

  private String title;

  @Column(name = "image_url")
  private String imageUrl;


  @Embedded private Documentation documentation;

  @Temporal(TemporalType.DATE)
  private LocalDate submissionDate;

  @ManyToOne private Member member;

  @ManyToOne private Admin admin;

  @Enumerated(EnumType.STRING)
  private EProjectState projectState;


  public void approveProject() throws UnAuthorizedProjectAction {
    projectState.approveProject(this);
  }

  public void rejectProject() throws UnAuthorizedProjectAction {
    projectState.rejectProject(this);
  }

  public void disableProject() throws UnAuthorizedProjectAction {
    projectState.disableProject(this);
  }
}
