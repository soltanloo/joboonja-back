import javafx.util.Pair;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) {
        Utilities myUtility = new Utilities();
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();
            System.out.println(commandData);

            switch(commandName) {
                case "register":
                    //System.out.println(commandData);
                    myUtility.register(commandData);
                case "addProject":
                    //System.out.println(commandData);
                    myUtility.addProject(commandData);
                case "bid":
                    //System.out.println(commandData);
                    myUtility.bid(commandData);
                case "auction":
                    //System.out.println(commandData);
                    myUtility.auction(commandData);
                    isFinished = true;
            }
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<String, String>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }

}
