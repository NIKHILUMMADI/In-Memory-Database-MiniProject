import exceptions.InvalidCommandException;
import exceptions.InvalidTTLException;

public class CommandParser {

    public static Command parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new InvalidCommandException("Command cannot be empty");
        }

        String[] parts = input.trim().split("\\s+");
        String command = parts[0].toUpperCase();

        try {
            switch (command) {

                case "PUT":
                    if (parts.length < 3) {
                        throw new InvalidCommandException("PUT requires key and value");
                    }
                    Integer key = Integer.parseInt(parts[1]);
                    String value = parts[2];

                    Long ttl = null;
                    if (parts.length == 4) {
                        ttl = Long.parseLong(parts[3]);
                        if (ttl <= 0) {
                            throw new InvalidTTLException("TTL must be greater than 0");
                        }
                    }
                    return new Command(Command.CommandType.PUT, key, value, ttl);

                case "GET":
                case "DELETE":
                    if (parts.length != 2) {
                        throw new InvalidCommandException(command + " requires a key");
                    }
                    return new Command(
                            Command.CommandType.valueOf(command),
                            Integer.parseInt(parts[1]),
                            null,
                            null
                    );

                case "STOP":
                case "START":
                case "EXIT":
                    return new Command(Command.CommandType.valueOf(command), null, null, null);

                default:
                    throw new InvalidCommandException("Unknown command");

            }
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Key or TTL must be numeric");
        }
    }
}
