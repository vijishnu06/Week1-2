import java.util.*;

class week1 {


    private HashMap<String, Integer> users = new HashMap<>();


    private HashMap<String, Integer> attempts = new HashMap<>();

    public UsernameChecker() {
        users.put("john_doe", 1);
        users.put("admin", 2);
        users.put("user123", 3);
    }


    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
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

        if (!users.containsKey(username + ".")) {
            suggestions.add(username + ".");
        }

        return suggestions;
    }


    public String getMostAttempted() {
        String maxUser = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxUser = entry.getKey();
            }
        }

        return maxUser + " (" + maxCount + " attempts)";
    }
}

public class week{
    public static void main(String[] args) {

        UsernameChecker checker = new UsernameChecker();

        System.out.println("Check john_doe: " + checker.checkAvailability("john_doe"));
        System.out.println("Check jane_smith: " + checker.checkAvailability("jane_smith"));

        System.out.println("Suggestions for john_doe:");
        System.out.println(checker.suggestAlternatives("john_doe"));

        System.out.println("Most Attempted: " + checker.getMostAttempted());
    }
}