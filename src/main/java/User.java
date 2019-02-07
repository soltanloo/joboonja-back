import java.util.ArrayList;

public class User{
    private String username = "";
    private ArrayList<Skill> skills = new ArrayList <Skill>();

    public User() {}

    public User(String username, ArrayList<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}