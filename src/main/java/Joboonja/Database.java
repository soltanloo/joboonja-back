package Joboonja;

import Models.Project;
import Models.Skill;
import Models.User;

import java.util.ArrayList;

public class Database {
    private static Database databaseInstance = null;
    static ArrayList<User> users = new ArrayList<User>();
    static ArrayList<Project> projects = new ArrayList<Project>();
    static ArrayList<Skill> skills = new ArrayList<Skill>();
    static User currentUser;

    private Database() {
        currentUser = new User();
        currentUser.setId("1");
        currentUser.setFirstName("علی");
        currentUser.setLastName("شریف‌زاده");
        currentUser.setJobTitle("برنامه‌نویس وب");
        currentUser.setBio("روی سنگ قبرم بنویسید: خدا بیامرز می‌خواست خیلی کارا بکنه ولی پول نداشت.");
        currentUser.addSkill(new Skill("HTML", 5));
        currentUser.addSkill(new Skill("Javascript", 4));
        currentUser.addSkill(new Skill("C++", 2));
        currentUser.addSkill(new Skill("Java", 3));
        users.add(currentUser);

        User secondUser = new User();
        secondUser.setId("2");
        secondUser.setFirstName("احمد");
        secondUser.setLastName("پورمخبر");
        secondUser.setJobTitle("دانشمند داده");
        secondUser.setBio("گله‌ای نیست من و فاصله‌ها هم‌زادیم.");
        secondUser.addSkill(new Skill("Python", 9));
        secondUser.addSkill(new Skill("Linux", 7));
        secondUser.addSkill(new Skill("C++", 5));
        users.add(secondUser);

        User thirdUser = new User();
        thirdUser.setId("3");
        thirdUser.setFirstName("اکبر");
        thirdUser.setLastName("گونزالس");
        thirdUser.setJobTitle("بی‌کار");
        thirdUser.setBio("می‌کوبم تا ته.");
        thirdUser.addSkill(new Skill("CSS", 1));
        thirdUser.addSkill(new Skill("HTML", 3));
        thirdUser.addSkill(new Skill("SEO", 2));
        users.add(thirdUser);
    }

    public static Database getInstance()
    {
        if (databaseInstance == null)
            databaseInstance = new Database();

        return databaseInstance;
    }

}
