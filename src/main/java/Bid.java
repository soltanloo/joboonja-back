
public class Bid{
    private String biddingUser;
    private String projectTitle;
    private int bidAmount;

    public Bid() {}

    public Bid(String biddingUser, String projectTitle, int bidAmount) {
        this.biddingUser = biddingUser;
        this.projectTitle = projectTitle;
        this.bidAmount = bidAmount;
    }

    public String getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(String biddingUser) {
        this.biddingUser = biddingUser;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
}