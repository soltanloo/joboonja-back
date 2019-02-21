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
        if (tokenizer.hasMoreTokens()) {
            id = tokenizer.nextToken();
            String response =
                    "<html>"
                            + "<body><h1>ID is: " + id + "<h1></body>"
                            + "</html>";
        } else {
            String response =
                    "<html>"
                            + "<body><h1>All projects.<h1></body>"
                            + "</html>";
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
