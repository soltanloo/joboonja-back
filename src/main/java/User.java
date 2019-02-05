import javafx.util.Pair;
import java.util.Scanner;
import Skill.java;

public class User{
    String uname;
    ArrayList <Skill> userSkills = new ArrayList <Skill>();

    public User(String uname, ArrayList<Skill> userSkills) {
        this.uname = uname;
        this.userSkills = userSkills;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public ArrayList<Skill> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(ArrayList<Skill> userskills) {
        this.userSkills = userskills;
    }
}