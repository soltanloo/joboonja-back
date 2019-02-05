import java.util.ArrayList;

public class User{
    String username;
    ArrayList<Skill> skills = new ArrayList <Skill>();

    public User(String username, ArrayList<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}