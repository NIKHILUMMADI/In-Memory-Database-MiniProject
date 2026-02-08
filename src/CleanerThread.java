public class CleanerThread extends Thread {

    private final InMemoryDatabase<?> database;

    public CleanerThread(InMemoryDatabase<?> database) {
        this.database = database;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            database.cleanupExpiredKeys();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
