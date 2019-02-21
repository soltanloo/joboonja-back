import javafx.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler());
        server.setExecutor(null);
        server.start();
    }

    class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            Class<Section> pageClass;
            try {
                pageClass = (Class<Section>) Class.forName(context.substring(0, 1).toUpperCase() + context.substring(1) + "Page");
                Section newInstance = pageClass.getDeclaredConstructor().newInstance();
                newInstance.HandleRequest(httpExchange);
            } catch (ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException |
                    NoSuchMethodException |
                    SecurityException e) {
                e.printStackTrace();
                String response =
                        "<html>"
                                + "<body>Page \"" + context + "\" not found.</body>"
                                + "</html>";
                httpExchange.sendResponseHeaders(404, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}