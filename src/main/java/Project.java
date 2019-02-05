import java.util.ArrayList;

public class Project{
    private String title = "";
    private int budget = 0;
    private ArrayList<Skill> skills = new ArrayList <Skill>();
    private ArrayList<Bid> bids = new ArrayList<Bid>();

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

    public ArrayList<Bid> getBids() {
        return bids;
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

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}