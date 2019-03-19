package DTOs;

import Models.Project;

public class ProjectWithBidStatus {
    private Project project;
    private Boolean hasBidden;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean getHasBidden() {
        return hasBidden;
    }

    public void setHasBidden(Boolean hasBidden) {
        this.hasBidden = hasBidden;
    }
}
