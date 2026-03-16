import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class PlagiarismDetector {

    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    private int N = 5;

    public void addDocument(String docId, String text) {
        List<String> ngrams = generateNGrams(text);

        for (String gram : ngrams) {
            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    public void analyzeDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);
        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {
            if (ngramIndex.containsKey(gram)) {
                for (String existingDoc : ngramIndex.get(gram)) {
                    matchCount.put(existingDoc, matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {
            int matches = entry.getValue();
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches + " matching n-grams with \"" + entry.getKey() + "\"");
            System.out.println("Similarity: " + similarity + "%");
        }
    }

    private List<String> generateNGrams(String text) {

        List<String> grams = new ArrayList<>();
        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }
            grams.add(gram.toString().trim());
        }

        return grams;
    }
}

public class PlagiarismDetectionSystem {

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String essay1 = "artificial intelligence is transforming the world and many industries are adopting artificial intelligence technologies";
        String essay2 = "artificial intelligence is transforming the world and many industries are adopting artificial intelligence technologies quickly";
        String essay3 = "climate change is affecting global temperatures and causing severe environmental impacts worldwide";

        detector.addDocument("essay_089.txt", essay1);
        detector.addDocument("essay_092.txt", essay2);

        detector.analyzeDocument("essay_123.txt", essay3);
    }
}