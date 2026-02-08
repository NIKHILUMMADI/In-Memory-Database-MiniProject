import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import exceptions.DatabaseStoppedException;

public class InMemoryDatabase<T> {

    private final Map<Integer, Entry<T>> map = new HashMap<>();
    private volatile boolean running = true;

    public synchronized void put(Integer key, T value, Long ttl) {
        ensureRunning();
        long expiry = (ttl == null) ? -1 : System.currentTimeMillis() + ttl;
        map.put(key, new Entry<>(value, expiry));
    }

    public synchronized T get(Integer key) {
        ensureRunning();
        Entry<T> entry = map.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.isExpired()) {
            map.remove(key);
            return null;
        }

        return entry.value;
    }

    public synchronized void delete(Integer key) {
        ensureRunning();
        map.remove(key);
    }

    public synchronized void cleanupExpiredKeys() {
        Iterator<Map.Entry<Integer, Entry<T>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().isExpired()) {
                iterator.remove();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
    }

    private void ensureRunning() {
        if (!running) {
            throw new DatabaseStoppedException("Database is stopped");
        }
    }
}
