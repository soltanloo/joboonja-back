
import Skill.java;

public class User{
    String username;
    ArrayList <Skill> skills = new ArrayList <Skill>();

    public User(String username, ArrayList<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public ArrayList<Skill> getskills() {
        return skills;
    }

    public void setskills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}