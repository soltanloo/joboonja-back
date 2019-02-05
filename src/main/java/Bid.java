import javafx.util.Pair;
import java.util.Scanner;

public class Bid{
    String biddingUser;
    String projectTitle;
    String bidAmount;

    public Bid(String biddingUser, String projectTitle, String bidAmount) {
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

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }
}