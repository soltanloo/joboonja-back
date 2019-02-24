import com.sun.net.httpserver.HttpExchange;

import javax.swing.text.View;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class ProjectPage implements Section {
    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id;
        String response = "";
        int statusCode = 200;
        if (tokenizer.hasMoreTokens()) {
            id = tokenizer.nextToken();
            Project project = null;
            try {
                project = Manager.projectManager.getProjectByID(id);
                if (!Manager.projectManager.hasSkills(Manager.userManager.getUserByID("1"), project)) {
                    statusCode = 403;
                    response = Viewer.viewUneligibleProject(project.getId());
                } else {
                    response = Viewer.viewProject(project);
                }
            } catch (ProjectNotFoundException e) {
                statusCode = 404;
                response = Viewer.viewProjectNotFound(e.getMessage());
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            response = Viewer.viewProjects(Manager.projectManager.getEligibleProjects());
        }
        httpExchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
