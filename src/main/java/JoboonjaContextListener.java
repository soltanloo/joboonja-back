import Exceptions.ProjectException;
import Exceptions.SkillException;
import Joboonja.Database;
import Joboonja.ProjectManager;
import Joboonja.SkillManager;
import Models.Project;
import Models.Skill;
import Tools.Parser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@WebListener
public class JoboonjaContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Database.getInstance();
        Parser parser = new Parser();
        try {
            fetchURL(parser, "project");
            fetchURL(parser, "skill");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fetchURL(Parser parser, String kind) throws IOException {
        InputStream response = new URL("http://142.93.134.194:8000/joboonja/" + kind).openStream();
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            if (kind.equals("project")) {
                ArrayList<Project> projects = parser.parseProjects(responseBody);
                for (Project p:
                        projects) {
                    ProjectManager.addProject(p);
                }
            }
            else{
                ArrayList<Skill> skills = parser.parseSkills(responseBody);
                for (Skill s:
                        skills) {
                    SkillManager.addSkillToSystem(s);
                }
            }
        } catch (SkillException | ProjectException e) {
            e.printStackTrace();
        }
    }
}