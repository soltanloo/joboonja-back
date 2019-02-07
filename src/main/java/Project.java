import java.util.ArrayList;

public class Project{
    private String title = "";
    private int budget = 0;
    private ArrayList<Skill> skills = new ArrayList <Skill>();
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public Project() {}

    public Project(String title, int budget, ArrayList<Skill> skills, ArrayList<Bid> bids) {
        this.title = title;
        this.budget = budget;
        this.skills = skills;
        this.bids = bids;
    }

    int getBudget() {
        return budget;
    }

    String getTitle() {
        return title;
    }

    ArrayList<Bid> getBids() {
        return bids;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    void addBid(Bid bid){
        bids.add(bid);
    }

    ArrayList<Skill> getSkills() {
        return skills;
    }
}