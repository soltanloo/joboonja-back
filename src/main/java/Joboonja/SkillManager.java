package Joboonja;

import Models.Skill;

public class SkillManager {
    public static void addSkill(Skill skill) {
        if (Database.skills.stream().noneMatch(s -> (s.getName().equals(skill.getName()))))
            Database.skills.add(skill);
        else
            System.out.println("Models.Skill \'" + skill.getName() + "\' already exists.");
    }
}
