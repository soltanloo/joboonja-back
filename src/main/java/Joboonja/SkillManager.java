package Joboonja;

import Exceptions.SkillEndorsementException;
import Exceptions.SkillException;
import Exceptions.UserException;
import Models.Skill;
import Models.User;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkillManager {
    public static void addSkillToSystem(Skill skill) throws SkillException {
        if (!Database.skillMapper.isAvailable(skill.getName()))
            Database.skillMapper.addSkill(skill.getName());
//        else
//            throw new SkillException("UserSkill \'" + skill.getName() + "\' already exists.");
    }
    public static void addSkillToUser(String skillName, User user) throws UserException, SkillException {
        if (user.getSkills().stream().anyMatch(s -> (s.getName().equals(skillName))))
            throw new SkillException("User already owns skill \'" + skillName + "\'.");
        else if (Database.skillMapper.isAvailable(skillName))
            Database.userSkillMapper.addUserSkill(user.getId(), skillName);
        else
            throw new SkillException("There is no skill named \'" + skillName +
                    "\' available to add in the system.");
    }
    public static void removeSkillFromUser(Integer skillId, User user) throws SkillException {
        Skill skill = null;
        for (Skill s :
                user.getSkills()) {
            if (s.getId() == skillId) {
                skill = s;
                break;
            }
        }
        if (skill == null) {
            throw new SkillException("UserSkill with id \'" + skillId
                    + "\' was not found for user with id \'" + user.getId() + "\'.");
        } else {
            Database.userSkillMapper.removeSkill(skill.getName(), user.getId());
        }
    }
    public static ArrayList<Skill> getAddableSkillsOfUser(Integer userId) {
        ArrayList<Skill> addableSkills = new ArrayList<>();
        User user = Database.userMapper.find(userId);
        for (String skill :
                Database.skillMapper.getSystemSkills()) {
            if (user.getSkills().stream().noneMatch(s -> (s.getName().equals(skill)))) {
                addableSkills.add(new Skill(skill));
            }
        }
        return addableSkills;
    }
    public static void endorseSkillOfUser(int skillId, Integer endorserId, Integer endorseeId) throws SkillException, SkillEndorsementException {
        if (endorserId.equals(endorseeId))
            throw new SkillEndorsementException("Users can't endorse their own skills!");
        User endorsee = Database.userMapper.find(endorseeId);
        for (Skill s :
                endorsee.getSkills()) {
            if (s.getId() == (skillId)) {
                if (s.getEndorsers().contains(endorserId))
                    throw new SkillEndorsementException("User with id \'" + endorserId +
                            "\' has already endorsed skill with id \'" + skillId +
                            "\' of user with id \'" + endorseeId + "\'.");
                else {
                    Database.userSkillMapper.addEndorsement(s.getName(), endorserId, endorseeId);
                    s.addEndorser(endorserId);
                }
                return;
            }
        }
        throw new SkillException("UserSkill with id \'" + skillId
                + "\' was not found for user with id \'" + endorseeId + "\'.");
    }
}
