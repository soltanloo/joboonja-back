package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project{
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private ArrayList<Skill> skills = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();
    private int budget = 0;
    private long deadline;
    private long creationDate;
    private int winnerId;

    public Project() {}

    public Project(String id, String title, String description, String imageUrl, ArrayList<Skill> skills,
                   ArrayList<Bid> bids, int budget, long deadline, long creationDate, int winnerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.skills = skills;
        this.bids = bids;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.winnerId = winnerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid){
        this.bids.add(bid);
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}