import javafx.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) throws IOException {

        InputStream response = new URL("http://142.93.134.194:8000/joboonja/project").openStream();
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();

        }
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/projects", new Handler.ProjectHandler());
        server.createContext("/user", new Handler.UserHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
