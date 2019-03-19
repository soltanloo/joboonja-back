package Joboonja;

import Exceptions.SkillException;
import Exceptions.UserException;
import Models.Skill;
import Models.User;

import java.util.ArrayList;

public class SkillManager {
    public static void addSkillToSystem(Skill skill) throws SkillException {
        if (Database.skills.stream().noneMatch(s -> (s.getName().equals(skill.getName()))))
            Database.skills.add(skill);
        else
            throw new SkillException("Skill \'" + skill.getName() + "\' already exists.");
    }
    public static void addSkillToUser(String skillName, User user) throws UserException {

        UserManager.getUserByID(user.getId()).addSkill(new Skill(skillName));
    }
    public static void removeSkillFromUser(String skillName, User user) throws SkillException {
        if (!user.getSkills().removeIf(skill -> skill.getName().equals(skillName)))
            throw new SkillException("Skill \'" + skillName
                    + "\' was not found for user with id \'" + user.getId() + "\'.");
    }
    public static ArrayList<Skill> getAddableSkillsOfUser(User user) {
        ArrayList<Skill> addableSkills = new ArrayList<>();
        for (Skill skill :
                Database.skills) {
            if (user.getSkills().stream().noneMatch(s -> (s.getName().equals(skill.getName())))) {
                addableSkills.add(skill);
            }
        }
        return addableSkills;
    }
    public static void endorseSkillOfUser(String skillName, String endorserId, User user) throws SkillException {
        for (Skill s :
                user.getSkills()) {
            if (s.getName().equals(skillName)) {
                s.addEndorser(endorserId);
                return;
            }
        }
        throw new SkillException("Skill \'" + skillName
                + "\' was not found for user with id \'" + user.getId() + "\'.");
    }
}
