public class Command {

    public enum CommandType {
        PUT, GET, DELETE, STOP, START, EXIT
    }

    CommandType type;
    Integer key;
    String rawValue;
    Long ttl;

    public Command(CommandType type, Integer key, String rawValue, Long ttl) {
        this.type = type;
        this.key = key;
        this.rawValue = rawValue;
        this.ttl = ttl;
    }
}
