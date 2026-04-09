public class RiskThresholdSystem {

    // 🔷 Linear Search (Unsorted)
    public static int linearSearch(int[] arr, int target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear Found at index: " + i);
                System.out.println("Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Not Found (Linear)");
        System.out.println("Comparisons: " + comparisons);
        return -1;
    }

    // 🔷 Binary Search (Exact Match)
    public static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Binary Found at index: " + mid);
                System.out.println("Comparisons: " + comparisons);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Not Found (Binary)");
        System.out.println("Comparisons: " + comparisons);
        return -1;
    }

    // 🔷 Find Floor (largest ≤ target)
    public static int findFloor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int floor = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                floor = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return floor;
    }

    // 🔷 Find Ceiling (smallest ≥ target)
    public static int findCeiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int ceiling = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                ceiling = arr[mid];
                high = mid - 1;
            }
        }

        return ceiling;
    }

    // 🔷 Find Insertion Point (lower_bound)
    public static int findInsertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length;

        while (low < high) {
            int mid = (low + high) / 2;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    // 🔷 Main Method
    public static void main(String[] args) {

        // Unsorted array (for Linear)
        int[] unsorted = {50, 10, 100, 25};

        // Sorted array (for Binary)
        int[] sorted = {10, 25, 50, 100};

        int target = 30;

        // 🔹 Linear Search
        linearSearch(unsorted, target);

        // 🔹 Binary Search
        binarySearch(sorted, target);

        // 🔹 Floor & Ceiling
        int floor = findFloor(sorted, target);
        int ceiling = findCeiling(sorted, target);

        System.out.println("\nFloor of " + target + ": " + floor);
        System.out.println("Ceiling of " + target + ": " + ceiling);

        // 🔹 Insertion Point
        int pos = findInsertionPoint(sorted, target);
        System.out.println("Insertion Position: " + pos);
    }
}