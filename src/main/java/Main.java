import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) throws Exception {
        Manager.getInstance();
        Parser parser = new Parser();
        fetchURL(parser, "project");
//        fetchURL(parser, "skill");
        Server server = new Server();
        server.startServer();
    }

    private static void fetchURL(Parser parser, String kind) throws IOException {
        InputStream response = new URL("http://142.93.134.194:8000/joboonja/" + kind).openStream();
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            if (kind.equals("project")) {
                ArrayList<Project> projects = parser.parseProjects(responseBody);
                for (Project p:
                        projects) {
                    Manager.projectManager.addProject(p);
                }
            }
            else{
                ArrayList<Skill> skills = parser.parseSkills(responseBody);
                for (Skill s:
                        skills) {
                    Manager.addSkill(s);
                }
            }
        }
    }

}
