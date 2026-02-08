import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InMemoryDatabase<String> database = new InMemoryDatabase<>();
        CleanerThread cleaner = new CleanerThread(database);
        cleaner.start();

        Scanner scanner = new Scanner(System.in);

        System.out.println("In-Memory DB Started");

        while (true) {
            try {
                String input = scanner.nextLine();
                Command command = CommandParser.parse(input);

                switch (command.type) {

                    case PUT:
                        database.put(command.key, command.rawValue, command.ttl);
                        System.out.println("OK");
                        break;

                    case GET:
                        System.out.println(database.get(command.key));
                        break;

                    case DELETE:
                        database.delete(command.key);
                        System.out.println("DELETED");
                        break;

                    case STOP:
                        database.stop();
                        System.out.println("DATABASE STOPPED");
                        break;

                    case START:
                        database.start();
                        System.out.println("DATABASE STARTED");
                        break;

                    case EXIT:
                        System.out.println("EXITING...");
                        return;
                }

            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
