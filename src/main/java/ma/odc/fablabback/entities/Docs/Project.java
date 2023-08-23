package ma.odc.fablabback.entities.Docs;

import jakarta.persistence.*;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.ProjectState;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;
    @Embedded
    private  IDocumentationImpl documentation;
    @ManyToOne
    private Member member;

    @ManyToOne
    private Admin approver;
    @Enumerated(EnumType.STRING)
    private ProjectState projectState;
}
