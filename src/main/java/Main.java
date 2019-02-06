import javafx.util.Pair;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) throws IOException {
        Utilities myUtility = new Utilities();
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch(commandName) {
                case "register":
                    myUtility.register(commandData);
                    break;
                case "addProject":
                    myUtility.addProject(commandData);
                    break;
                case "bid":
                    myUtility.bid(commandData);
                    break;
                case "auction":
                    System.out.println("-> winner: " + myUtility.auction(commandData));
                    isFinished = true;
                    break;
            }
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex + 1));
    }

}
