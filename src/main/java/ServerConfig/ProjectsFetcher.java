package ServerConfig;

import Exceptions.ProjectException;
import Joboonja.Database;
import Joboonja.ProjectManager;
import Models.Project;
import Tools.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectsFetcher implements Runnable {

    @Override
    public void run() {
        System.out.println("FETCHING NEW PROJECTS");
        Parser parser = new Parser();
        InputStream response = null;
        try {
            response = new URL("http://142.93.134.194:8000/joboonja/project").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            ArrayList<Project> projects = parser.parseProjects(responseBody);
            Project lastProject = Database.projectMapper.getLastProject();
            Long lastDate = lastProject.getCreationDate();
            for (Project p:
                    projects) {
                if (p.getCreationDate() > lastDate) {
                    ProjectManager.addProject(p);
                    System.out.println("ADDING NEW PROJECT");
                }
            }
        } catch (IOException | ProjectException e) {
            e.printStackTrace();
        }
    }
}
