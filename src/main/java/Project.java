import java.util.ArrayList;

public class Project{
    String title;
    int budget;
    ArrayList<Skill> skills = new ArrayList <Skill>();
    ArrayList<Bid> bids = new ArrayList<Bid>();

    public Project(String title, int budget, ArrayList<Skill> skills, ArrayList<Bid> bids) {
        this.title = title;
        this.budget = budget;
        this.skills = skills;
        this.bids = bids;
    }

    public int getBudget() {
        return budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void addBid(Bid bid){
        bids.add(bid);
    }
}