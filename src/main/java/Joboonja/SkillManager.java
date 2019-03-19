package Joboonja;

import Exceptions.SkillEndorsementException;
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
    public static void addSkillToUser(String skillName, User user) throws UserException, SkillException {
        if (user.getSkills().stream().anyMatch(s -> (s.getName().equals(skillName))))
            throw new SkillException("User already owns skill \'" + skillName + "\'.");
        else if (Database.skills.stream().anyMatch(s -> (s.getName().equals(skillName))))
            UserManager.getUserByID(user.getId()).addSkill(new Skill(skillName));
        else
            throw new SkillException("There is no skill named \'" + skillName +
                    "\' available to add in the system.");
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
    public static void endorseSkillOfUser(String skillName, String endorserId, User user) throws SkillException, SkillEndorsementException {
        if (endorserId.equals(user.getId()))
            throw new SkillEndorsementException("Users can't endorse their own skills!");
        for (Skill s :
                user.getSkills()) {
            if (s.getName().equals(skillName)) {
                if (s.getEndorsers().contains(endorserId))
                    throw new SkillEndorsementException("User with id \'" + endorserId +
                            "\' has already endorsed skill \'" + skillName +
                            "\' of user with id \'" + user.getId() + "\'.");
                else
                    s.addEndorser(endorserId);
                return;
            }
        }
        throw new SkillException("Skill \'" + skillName
                + "\' was not found for user with id \'" + user.getId() + "\'.");
    }
}
