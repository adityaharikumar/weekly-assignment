import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

class MultiLevelCache {

    private LRUCache<String, String> L1;
    private LRUCache<String, String> L2;
    private HashMap<String, String> L3;

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int l3Hits = 0;
    private int requests = 0;

    public MultiLevelCache() {
        L1 = new LRUCache<>(10000);
        L2 = new LRUCache<>(100000);
        L3 = new HashMap<>();

        L3.put("video_123", "VideoData123");
        L3.put("video_999", "VideoData999");
    }

    public String getVideo(String videoId) {
        requests++;

        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("L2 Cache HIT (5ms)");
            String data = L2.get(videoId);
            L1.put(videoId, data);
            System.out.println("Promoted to L1");
            return data;
        }

        System.out.println("L2 Cache MISS");

        if (L3.containsKey(videoId)) {
            l3Hits++;
            System.out.println("L3 Database HIT (150ms)");
            String data = L3.get(videoId);
            L2.put(videoId, data);
            return data;
        }

        return null;
    }

    public void getStatistics() {
        double l1Rate = (requests == 0) ? 0 : (l1Hits * 100.0) / requests;
        double l2Rate = (requests == 0) ? 0 : (l2Hits * 100.0) / requests;
        double l3Rate = (requests == 0) ? 0 : (l3Hits * 100.0) / requests;

        System.out.println("L1: Hit Rate " + l1Rate + "%");
        System.out.println("L2: Hit Rate " + l2Rate + "%");
        System.out.println("L3: Hit Rate " + l3Rate + "%");
    }
}

public class MultiLevelCacheSystem {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123");
        cache.getVideo("video_123");

        cache.getVideo("video_999");

        cache.getStatistics();
    }
}