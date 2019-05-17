package DTOs;

import Models.Project;
import Models.User;

public class ProjectWithBidStatus {
    private Project project;
    private Boolean hasBidden;
    private User winner;

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

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }
}
