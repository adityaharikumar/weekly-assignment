public class ClientRiskSystem {

    // 🔷 Client Class
    static class Client {
        String name;
        int riskScore;
        double accountBalance;

        public Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return name + ":" + riskScore + " (Bal:" + accountBalance + ")";
        }
    }

    // 🔷 Bubble Sort (Ascending by Risk Score)
    public static void bubbleSortAsc(Client[] arr) {
        int n = arr.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swaps++;
                    swapped = true;

                    // 🔥 Visualization (important for demo)
                    System.out.println("Swap: " + arr[j] + " <-> " + arr[j + 1]);
                }
            }

            if (!swapped) break;
        }

        System.out.println("Total Swaps: " + swaps);
    }

    // 🔷 Insertion Sort (Descending by Risk + Balance)
    public static void insertionSortDesc(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // 🔷 Comparator: Risk DESC, Balance DESC
    public static int compare(Client c1, Client c2) {
        if (c1.riskScore != c2.riskScore) {
            return Integer.compare(c1.riskScore, c2.riskScore);
        }
        return Double.compare(c1.accountBalance, c2.accountBalance);
    }

    // 🔷 Top N High Risk Clients
    public static void printTopRiskClients(Client[] arr, int topN) {
        System.out.println("\nTop " + topN + " High Risk Clients:");
        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            System.out.println(arr[i]);
        }
    }

    // 🔷 Main Method
    public static void main(String[] args) {

        // Sample Input
        Client[] clients = {
            new Client("clientC", 80, 5000),
            new Client("clientA", 20, 2000),
            new Client("clientB", 50, 3000)
        };

        // 🔹 Bubble Sort (Ascending)
        System.out.println("Bubble Sort (Ascending by Risk):");
        bubbleSortAsc(clients);

        System.out.println("\nAfter Bubble Sort:");
        for (Client c : clients) {
            System.out.println(c);
        }

        // 🔹 Insertion Sort (Descending)
        insertionSortDesc(clients);

        System.out.println("\nAfter Insertion Sort (Descending):");
        for (Client c : clients) {
            System.out.println(c);
        }

        // 🔹 Top Risk Clients
        printTopRiskClients(clients, 10);
    }
}