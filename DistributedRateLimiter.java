import java.util.HashMap;

class TokenBucket {
    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate;

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - lastRefillTime) / 1000;

        if (elapsedSeconds > 0) {
            int newTokens = (int) (elapsedSeconds * refillRate);
            tokens = Math.min(maxTokens, tokens + newTokens);
            lastRefillTime = now;
        }
    }

    public int getRemainingTokens() {
        refill();
        return tokens;
    }
}

class RateLimiter {

    private HashMap<String, TokenBucket> clients = new HashMap<>();
    private int limit = 1000;
    private int refillRate = 1000 / 3600;

    public synchronized void checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(limit, refillRate));
        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("checkRateLimit(clientId=\"" + clientId + "\") → Allowed (" + bucket.getRemainingTokens() + " requests remaining)");
        } else {
            System.out.println("checkRateLimit(clientId=\"" + clientId + "\") → Denied (0 requests remaining)");
        }
    }

    public void getRateLimitStatus(String clientId) {
        TokenBucket bucket = clients.get(clientId);

        if (bucket != null) {
            int used = limit - bucket.getRemainingTokens();
            System.out.println("getRateLimitStatus(\"" + clientId + "\") → {used: " + used + ", limit: " + limit + "}");
        }
    }
}

public class DistributedRateLimiter {

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        limiter.checkRateLimit("abc123");
        limiter.checkRateLimit("abc123");
        limiter.checkRateLimit("abc123");

        limiter.getRateLimitStatus("abc123");
    }
}
