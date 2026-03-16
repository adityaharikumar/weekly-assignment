import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time;

    public Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

class TransactionAnalyzer {

    public static void findTwoSum(List<Transaction> transactions, int target) {
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction t2 = map.get(complement);
                System.out.println("TwoSum Pair → (" + t2.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    public static void findTwoSumWithTimeWindow(List<Transaction> transactions, int target, long window) {
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction t2 = map.get(complement);

                if (Math.abs(t.time - t2.time) <= window) {
                    System.out.println("TwoSum (1hr window) → (" + t2.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }

    public static void detectDuplicates(List<Transaction> transactions) {
        HashMap<String, Set<String>> map = new HashMap<>();

        for (Transaction t : transactions) {
            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(t.account);
        }

        for (String key : map.keySet()) {
            if (map.get(key).size() > 1) {
                String[] parts = key.split("_");
                System.out.println("Duplicate → amount:" + parts[0] + " merchant:" + parts[1] + " accounts:" + map.get(key));
            }
        }
    }

    public static void findKSum(List<Transaction> transactions, int k, int target) {
        List<Integer> ids = new ArrayList<>();
        backtrack(transactions, k, target, 0, ids);
    }

    private static void backtrack(List<Transaction> t, int k, int target, int start, List<Integer> ids) {
        if (k == 0 && target == 0) {
            System.out.println("KSum → " + ids);
            return;
        }

        if (k == 0 || target < 0) return;

        for (int i = start; i < t.size(); i++) {
            ids.add(t.get(i).id);
            backtrack(t, k - 1, target - t.get(i).amount, i + 1, ids);
            ids.remove(ids.size() - 1);
        }
    }
}

public class FinancialTransactionAnalysis {

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        long baseTime = System.currentTimeMillis();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1", baseTime));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2", baseTime + 900000));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3", baseTime + 1800000));
        transactions.add(new Transaction(4, 500, "StoreA", "acc2", baseTime + 2000000));

        TransactionAnalyzer.findTwoSum(transactions, 500);

        TransactionAnalyzer.findTwoSumWithTimeWindow(transactions, 500, 3600000);

        TransactionAnalyzer.detectDuplicates(transactions);

        TransactionAnalyzer.findKSum(transactions, 3, 1000);
    }
}
