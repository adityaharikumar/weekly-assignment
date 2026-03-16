import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
    String query;
}

class AutocompleteSystem {

    private TrieNode root = new TrieNode();
    private HashMap<String, Integer> frequency = new HashMap<>();

    public void addQuery(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);

        TrieNode node = root;
        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
        node.query = query;
    }

    public List<String> search(String prefix) {
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        collect(node, results);

        PriorityQueue<String> pq = new PriorityQueue<>(
            (a, b) -> frequency.get(a) - frequency.get(b)
        );

        for (String q : results) {
            pq.offer(q);
            if (pq.size() > 10) {
                pq.poll();
            }
        }

        List<String> top = new ArrayList<>();
        while (!pq.isEmpty()) {
            top.add(pq.poll());
        }

        Collections.reverse(top);
        return top;
    }

    private void collect(TrieNode node, List<String> results) {
        if (node.isEnd) {
            results.add(node.query);
        }

        for (TrieNode child : node.children.values()) {
            collect(child, results);
        }
    }

    public void updateFrequency(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
    }
}

public class SearchAutocomplete {

    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        system.addQuery("java tutorial");
        system.addQuery("javascript");
        system.addQuery("java download");
        system.addQuery("java tutorial");
        system.addQuery("java 21 features");

        List<String> suggestions = system.search("jav");

        System.out.println("search(\"jav\") →");
        int rank = 1;
        for (String s : suggestions) {
            System.out.println(rank + ". " + s + " (" + system.frequency.get(s) + " searches)");
            rank++;
        }

        system.updateFrequency("java 21 features");
        system.updateFrequency("java 21 features");
        system.updateFrequency("java 21 features");

        System.out.println("\nupdateFrequency(\"java 21 features\") → Frequency: " + system.frequency.get("java 21 features"));
    }
}