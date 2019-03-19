package DTOs;

import Models.Skill;
import Models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserWithMeta {
    private User user;
    private HashMap<String, Boolean> skillsEndorsed = new HashMap<>();
    private ArrayList<Skill> addableSkills = new ArrayList<>();
    private Boolean isCurrentUser;
    private String myId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, Boolean> getSkillsEndorsed() {
        return skillsEndorsed;
    }

    public void setSkillsEndorsed(HashMap<String, Boolean> skillsEndorsed) {
        this.skillsEndorsed = skillsEndorsed;
    }

    public ArrayList<Skill> getAddableSkills() {
        return addableSkills;
    }

    public void setAddableSkills(ArrayList<Skill> addableSkills) {
        this.addableSkills = addableSkills;
    }

    public Boolean getIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(Boolean currentUser) {
        isCurrentUser = currentUser;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
}
