package ma.odc.fablabback.enums;

import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.exceptions.UnAuthorizedProjectAction;

public enum EProjectState {
  ON_HOLD,
  ACTIVE,
  REJECTED;

  public void approveProject(Project project) throws UnAuthorizedProjectAction {
    switch (this) {
      case ACTIVE -> throw new UnAuthorizedProjectAction("Project already approved");
      case ON_HOLD -> project.setProjectState(ACTIVE);
      case REJECTED -> throw new UnAuthorizedProjectAction("Cannot approve a rejected Project");
      default -> throw new UnAuthorizedProjectAction("Action not clear");
    }
  }

  public void disableProject(Project project) throws UnAuthorizedProjectAction {
    switch (this) {
      case ON_HOLD ,ACTIVE -> project.setProjectState(ON_HOLD);
//      case ACTIVE -> project.setProjectState(ON_HOLD);
      case REJECTED -> throw new UnAuthorizedProjectAction("Project Rejected already");
      default -> throw new UnAuthorizedProjectAction("Action not clear");
    }
  }


  public void rejectProject(Project project) throws UnAuthorizedProjectAction {
    switch (this){
      case ON_HOLD -> project.setProjectState(REJECTED);
      case ACTIVE -> throw  new UnAuthorizedProjectAction("Project needs to be disabled before");
      case REJECTED -> throw new UnAuthorizedProjectAction("Project already rejected");
      default -> throw new UnAuthorizedProjectAction("Action not clear");
    }
  }
}
