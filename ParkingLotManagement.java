class ParkingSpot {
    String licensePlate;
    long entryTime;
    String status;

    ParkingSpot() {
        status = "EMPTY";
    }
}

class ParkingLot {

    private ParkingSpot[] table;
    private int size = 500;
    private int occupied = 0;
    private int totalProbes = 0;
    private int parkOperations = 0;

    public ParkingLot() {
        table = new ParkingSpot[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ParkingSpot();
        }
    }

    private int hash(String license) {
        return Math.abs(license.hashCode()) % size;
    }

    public void parkVehicle(String license) {

        int index = hash(license);
        int probes = 0;

        while (table[index].status.equals("OCCUPIED")) {
            index = (index + 1) % size;
            probes++;
        }

        table[index].licensePlate = license;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = "OCCUPIED";

        occupied++;
        totalProbes += probes;
        parkOperations++;

        System.out.println("parkVehicle(\"" + license + "\") → Assigned spot #" + index + " (" + probes + " probes)");
    }

    public void exitVehicle(String license) {

        int index = hash(license);

        while (!table[index].status.equals("EMPTY")) {

            if (table[index].status.equals("OCCUPIED") && table[index].licensePlate.equals(license)) {

                long duration = System.currentTimeMillis() - table[index].entryTime;
                double hours = duration / (1000.0 * 60 * 60);
                double fee = hours * 5;

                table[index].status = "DELETED";
                table[index].licensePlate = null;

                occupied--;

                System.out.println("exitVehicle(\"" + license + "\") → Spot #" + index +
                        " freed, Duration: " + String.format("%.2f", hours) +
                        "h, Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % size;
        }

        System.out.println("Vehicle not found");
    }

    public void getStatistics() {

        double occupancyRate = (occupied * 100.0) / size;
        double avgProbes = parkOperations == 0 ? 0 : (double) totalProbes / parkOperations;

        System.out.println("Occupancy: " + String.format("%.2f", occupancyRate) + "%");
        System.out.println("Avg Probes: " + String.format("%.2f", avgProbes));
    }
}

public class ParkingLotManagement {

    public static void main(String[] args) throws InterruptedException {

        ParkingLot lot = new ParkingLot();

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        Thread.sleep(2000);

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}