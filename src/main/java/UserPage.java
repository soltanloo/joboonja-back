import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public class UserPage implements Section {
    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
        String context = tokenizer.nextToken();
        String id = tokenizer.nextToken();
        String response = "";
        int statusCode = 200;
        try {
            response = Viewer.viewUser(Manager.userManager.getUserByID(id));
        } catch (UserNotFoundException e) {
            statusCode = 404;
            response = Viewer.viewUserNotFound(id);
        }
        httpExchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
