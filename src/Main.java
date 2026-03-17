import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ipAddress, long ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    private final int MAX_SIZE = 5;

    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(MAX_SIZE, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_SIZE;
                }
            };

    private int hits = 0;
    private int misses = 0;

    // Simulated upstream DNS lookup
    private String queryUpstream(String domain) {
        return "172.217.14." + new Random().nextInt(255);
    }

    public String resolve(String domain, int ttlSeconds) {

        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ipAddress;
        }

        misses++;

        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(ip, ttlSeconds));

        if (entry != null && entry.isExpired()) {
            return "Cache EXPIRED → Query upstream → " + ip;
        }

        return "Cache MISS → Query upstream → " + ip;
    }

    public void getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }
}

public class week {

    public static void main(String[] args) throws Exception {

        DNSCache cache = new DNSCache();

        System.out.println(cache.resolve("google.com", 5));
        System.out.println(cache.resolve("google.com", 5));

        Thread.sleep(6000); // simulate TTL expiry

        System.out.println(cache.resolve("google.com", 5));

        cache.getCacheStats();
    }
}