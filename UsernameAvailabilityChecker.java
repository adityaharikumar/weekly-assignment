import java.util.*;

class UsernameService {

    private HashMap<String, Integer> users;
    private HashMap<String, Integer> attemptFrequency;

    public UsernameService() {
        users = new HashMap<>();
        attemptFrequency = new HashMap<>();

        users.put("john_doe", 101);
        users.put("admin", 1);
        users.put("alex99", 102);
    }

    public boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String modified = username.replace("_", ".");
        if (!users.containsKey(modified)) {
            suggestions.add(modified);
        }

        return suggestions;
    }

    public String getMostAttempted() {

        String mostAttempted = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }
}

public class UsernameAvailabilityChecker {

    public static void main(String[] args) {

        UsernameService service = new UsernameService();

        System.out.println("checkAvailability(\"john_doe\") → " + service.checkAvailability("john_doe"));
        System.out.println("checkAvailability(\"jane_smith\") → " + service.checkAvailability("jane_smith"));

        System.out.println("suggestAlternatives(\"john_doe\") → " + service.suggestAlternatives("john_doe"));

        service.checkAvailability("admin");
        service.checkAvailability("admin");
        service.checkAvailability("admin");

        System.out.println("getMostAttempted() → " + service.getMostAttempted());
    }
}