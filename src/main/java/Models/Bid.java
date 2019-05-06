package Models;

public class Bid{
    private int userId;
    private String projectId;
    private int bidAmount;

    public Bid() {}

    public Bid(int userId, String projectId, int bidAmount) {
        this.userId = userId;
        this.projectId = projectId;
        this.bidAmount = bidAmount;
    }


    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProject(String projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setBiddingUser(int userId) {
        this.userId = userId;
    }
}