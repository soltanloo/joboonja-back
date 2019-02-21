import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class ProjectPage implements Section {
    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id = "";
        String response = "";
        if (tokenizer.hasMoreTokens()) {
            id = tokenizer.nextToken();
            response = Viewer.viewProject(Manager.projectManager.getProjectByID(id));
        } else {
            response = Viewer.viewProjects(Manager.projectManager.getEligibleProjects());
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
