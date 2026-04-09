import java.util.Arrays;
import java.util.Comparator;

public class AccountLookupSystem {

    // 🔷 Transaction Log Class
    static class Log {
        String accountId;

        public Log(String accountId) {
            this.accountId = accountId;
        }

        @Override
        public String toString() {
            return accountId;
        }
    }

    // 🔷 Linear Search (First Occurrence)
    public static int linearSearchFirst(Log[] arr, String target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].accountId.equals(target)) {
                System.out.println("Linear First Found at index: " + i);
                System.out.println("Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Not Found (Linear)");
        return -1;
    }

    // 🔷 Linear Search (Last Occurrence)
    public static int linearSearchLast(Log[] arr, String target) {
        int comparisons = 0;
        int index = -1;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].accountId.equals(target)) {
                index = i;
            }
        }

        System.out.println("Linear Last Found at index: " + index);
        System.out.println("Comparisons: " + comparisons);
        return index;
    }

    // 🔷 Binary Search (Find One Occurrence)
    public static int binarySearch(Log[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            int cmp = arr[mid].accountId.compareTo(target);

            if (cmp == 0) {
                System.out.println("Binary Found at index: " + mid);
                System.out.println("Comparisons: " + comparisons);
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Not Found (Binary)");
        return -1;
    }

    // 🔷 Count Occurrences using Binary Search
    public static int countOccurrences(Log[] arr, String target) {
        int first = findFirst(arr, target);
        int last = findLast(arr, target);

        if (first == -1) return 0;

        return last - first + 1;
    }

    // 🔷 Find First Occurrence (Binary)
    public static int findFirst(Log[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].accountId.equals(target)) {
                result = mid;
                high = mid - 1;
            } else if (arr[mid].accountId.compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    // 🔷 Find Last Occurrence (Binary)
    public static int findLast(Log[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].accountId.equals(target)) {
                result = mid;
                low = mid + 1;
            } else if (arr[mid].accountId.compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    // 🔷 Main Method
    public static void main(String[] args) {

        // Sample Input
        Log[] logs = {
            new Log("accB"),
            new Log("accA"),
            new Log("accB"),
            new Log("accC")
        };

        String target = "accB";

        // 🔹 Linear Search
        linearSearchFirst(logs, target);
        linearSearchLast(logs, target);

        // 🔹 Sort for Binary Search
        Arrays.sort(logs, Comparator.comparing(l -> l.accountId));

        System.out.println("\nAfter Sorting:");
        for (Log l : logs) {
            System.out.println(l);
        }

        // 🔹 Binary Search
        int index = binarySearch(logs, target);

        // 🔹 Count occurrences
        int count = countOccurrences(logs, target);
        System.out.println("Total Occurrences: " + count);
    }
}
