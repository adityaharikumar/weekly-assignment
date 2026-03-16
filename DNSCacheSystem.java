
import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    public DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttl * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    private LinkedHashMap<String, DNSEntry> cache;
    private int capacity;
    private int hits = 0;
    private int misses = 0;

    public DNSCache(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > DNSCache.this.capacity;
            }
        };
    }

    public synchronized String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null) {
            if (!entry.isExpired()) {
                hits++;
                System.out.println("resolve(\"" + domain + "\") → Cache HIT → " + entry.ipAddress);
                return entry.ipAddress;
            } else {
                cache.remove(domain);
                System.out.println("resolve(\"" + domain + "\") → Cache EXPIRED");
            }
        }

        misses++;
        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(domain, ip, 5));

        System.out.println("resolve(\"" + domain + "\") → Cache MISS → Query upstream → " + ip);
        return ip;
    }

    private String queryUpstream(String domain) {
        Random r = new Random();
        return "172.217.14." + (200 + r.nextInt(50));
    }

    public void getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;
        System.out.println("Hit Rate: " + hitRate + "%");
    }
}

public class DNSCacheSystem {

    public static void main(String[] args) throws InterruptedException {

        DNSCache cache = new DNSCache(5);

        cache.resolve("google.com");
        cache.resolve("google.com");

        Thread.sleep(6000);

        cache.resolve("google.com");

        cache.getCacheStats();
    }
}
