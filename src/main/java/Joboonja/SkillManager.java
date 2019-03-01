package Joboonja;

import Exceptions.UserNotFoundException;
import Models.Skill;
import Models.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class SkillManager {
    public static void addSkillToSystem(Skill skill) {
        if (Database.skills.stream().noneMatch(s -> (s.getName().equals(skill.getName()))))
            Database.skills.add(skill);
        else
            System.out.println("Skill \'" + skill.getName() + "\' already exists.");
    }
    public static void addSkillToUser(String skillName, User user) {
        try {
            UserManager.getUserByID(user.getId()).addSkill(new Skill(skillName, 0));
        } catch (UserNotFoundException e) {
            //TODO
        }
    }
    public static void removeSkillFromUser(String skillName, User user) {
        if (user.getSkills().removeIf(skill -> skill.getName().equals(skillName))) {
            return;
        } else {
            //TODO: throw SkillNotFoundException();
            return;
        }
    }
    public static ArrayList<Skill> getAddableSkills(User user) {
        ArrayList<Skill> addableSkills = new ArrayList<>();
        for (Skill skill :
                Database.skills) {
            if (user.getSkills().stream().noneMatch(s -> (s.getName().equals(skill.getName())))) {
                addableSkills.add(skill);
            }
        }
        return addableSkills;
    }
}
