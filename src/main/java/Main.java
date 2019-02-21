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
        server.setExecutor(null); // creates a default executor
        server.start();
//        Auctioneer auctioneer = new Auctioneer();
//        while (!isFinished) {
//            Pair<String, String> commandParts = getCommandParts();
//            String commandName = commandParts.getKey();
//            String commandData = commandParts.getValue();
//
//            switch(commandName) {
//                case "register":
//                    auctioneer.register(commandData);
//                    break;
//                case "addProject":
//                    auctioneer.addProject(commandData);
//                    break;
//                case "bid":
//                    auctioneer.bid(commandData);
//                    break;
//                case "auction":
//                    System.out.println("-> winner: " + auctioneer.auction(commandData));
//                    isFinished = true;
//                    break;
//            }
//        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex + 1));
    }

}
