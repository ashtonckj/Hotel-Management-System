import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Room{
    private String roomID;
    protected String roomType;
    protected double roomPrice;
    private int roomFloor;

    public Room() {}

    public Room (String roomID, String roomType, double roomPrice, int roomFloor) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomFloor = roomFloor;
    }

    public void SearchRoomTypeAvailability() {
        int typeChoice = -1;
        String searchTerm;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Room Type:");
        System.out.println("1. Single Room");
        System.out.println("2. Suite");
        System.out.println("3. Executive Suite");
        System.out.println("4. Deluxe Room");
        System.out.println("5. Family Room");
        System.out.println("6. Deluxe Suite");
        System.out.println("7. Family Suite");

        while (true) {
            System.out.print("Enter Choice (1-7): ");
            if (scanner.hasNextInt()) {
                typeChoice = scanner.nextInt();
                scanner.nextLine();
                if (typeChoice >= 1 && typeChoice <= 7) {
                    break;
                } else {
                    System.out.println("Please select a valid number between 1 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        String[] roomTypes = {"Single Room", "Suite", "Executive Suite", "Deluxe Room", "Family Room", "Deluxe Suite", "Family Suite"};
        searchTerm = roomTypes[typeChoice - 1];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedCheckInDate = LocalDate.parse("2024-07-25", formatter);
        LocalDate selectedCheckOutDate = LocalDate.parse("2025-05-01", formatter);
        String[] bookedRoomIDs = new String[1000000];
        int bookedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 5) {
                    String roomID = parts[1].trim();
                    LocalDate checkInDate = LocalDate.parse(parts[3].trim(), formatter);
                    LocalDate checkOutDate = LocalDate.parse(parts[4].trim(), formatter);

                    boolean willRoomsOverlap = !(selectedCheckOutDate.isBefore(checkInDate) || selectedCheckInDate.isAfter(checkOutDate.minusDays(1)));

                    if (willRoomsOverlap) {
                        boolean alreadyAdded = false;
                        for (int i = 0; i < bookedCount; i++) {
                            if (bookedRoomIDs[i].equals(roomID)) {
                                alreadyAdded = true;
                                break;
                            }
                        }
        
                        if (!alreadyAdded) {
                            bookedRoomIDs[bookedCount] = roomID;
                            bookedCount++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Reservation.txt");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            System.out.println("╔════════════╦═════════════════════╦═══════════╗");
            System.out.printf("║ %-10s ║ %-19s ║ %-9s ║\n", "Room ID", "Room Type", "Floor");
            System.out.println("╠════════════╬═════════════════════╬═══════════╣");
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) {
                    String roomID = parts[0].trim();
                    String roomType = parts[1].trim();
                    String floor = parts[3].trim();
    
                    if (roomType.equalsIgnoreCase(searchTerm)) {
                        boolean isBooked = false;
                        for (int i = 0; i < bookedCount; i++) {
                            if (bookedRoomIDs[i].equals(roomID)) {
                                isBooked = true;
                                break;
                            }
                        }
                        if (!isBooked) {
                            System.out.printf("║ %-10s ║ %-19s ║ %-9s ║\n", roomID, roomType, floor);
                        }
                    }
                }
            }
            System.out.println("╚════════════╩═════════════════════╩═══════════╝");
        } catch (IOException e) {
            System.out.println("Error reading Room.txt");
        }
    }

    public void GenerateRoomTypeSummaryAvailability() {
        String[] roomTypes = {"Single Room", "Suite", "Executive Suite", "Deluxe Room", "Family Room", "Deluxe Suite", "Family Suite"};
        double[] prices = new double[roomTypes.length];
        int[] totalRooms = new int[roomTypes.length];
        int[] reservedRooms = new int[roomTypes.length];

        try (BufferedReader reader = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    String type = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    for (int i = 0; i < roomTypes.length; i++) {
                        if (roomTypes[i].equalsIgnoreCase(type)) {
                            totalRooms[i]++;
                            prices[i] = price;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Room.txt: " + e.getMessage());
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedCheckInDate = LocalDate.parse("2024-07-25", formatter);
        LocalDate selectedCheckOutDate = LocalDate.parse("2025-05-01", formatter);
        String[] bookedRoomIDs = new String[1000000];
        int bookedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 5) {
                    String roomID = parts[1].trim();
                    LocalDate checkInDate = LocalDate.parse(parts[3].trim(), formatter);
                    LocalDate checkOutDate = LocalDate.parse(parts[4].trim(), formatter);

                    boolean willRoomsOverlap = !(selectedCheckOutDate.isBefore(checkInDate) || selectedCheckInDate.isAfter(checkOutDate.minusDays(1)));

                    if (willRoomsOverlap) {
                        boolean alreadyAdded = false;
                        for (int i = 0; i < bookedCount; i++) {
                            if (bookedRoomIDs[i].equals(roomID)) {
                                alreadyAdded = true;
                                break;
                            }
                        }
        
                        if (!alreadyAdded) {
                            bookedRoomIDs[bookedCount] = roomID;
                            bookedCount++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Reservation.txt: " + e.getMessage());
            return;
        }

        for (int bookedRoomIndex = 0; bookedRoomIndex < bookedCount; bookedRoomIndex++) {
            String roomID = bookedRoomIDs[bookedRoomIndex];
            try (BufferedReader roomReader = new BufferedReader(new FileReader("Room.txt"))) {
                String roomLine;
                while ((roomLine = roomReader.readLine()) != null) {
                    String[] roomParts = roomLine.split(", ");
                    if (roomParts.length >= 2 && roomParts[0].equals(roomID)) {
                        String roomType = roomParts[1].trim();
                        for (int i = 0; i < roomTypes.length; i++) {
                            if (roomTypes[i].equalsIgnoreCase(roomType)) {
                                reservedRooms[i]++;
                                break;
                            }
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading Room.txt: " + e.getMessage());
                return;
            }
        }

        // Step 3: Print the table
        System.out.println("╔══════════════════════╦═══════════════════╦═════════════════════════════════════════════╦═════════════════╗");
        System.out.printf("║ %-20s ║ %-17s ║ %-43s ║ %-15s ║\n", "Room Type", "Price (per Night)", "Room Description", "Rooms Available");        

        for (int i = 0; i < roomTypes.length; i++) {
            int available = totalRooms[i] - reservedRooms[i];
            String descLine = "";
            switch (roomTypes[i]) {
                case "Single Room":
                    descLine = new SingleRoom().toString();
                    break;
                case "Suite":
                    descLine = new Suite().toString();
                    break;
                case "Executive Suite":
                    descLine = new ExecutiveSuite().toString();
                    break;
                case "Deluxe Room":
                    descLine = new DeluxeRoom().toString();
                    break;
                case "Family Room":
                    descLine = new FamilyRoom().toString();
                    break;
                case "Deluxe Suite":
                    descLine = new DeluxeSuite().toString();
                    break;
                case "Family Suite":
                    descLine = new FamilySuite().toString();
                    break;
            }
            String[] descParts = descLine.split(", ");
            System.out.println("╠══════════════════════╬═══════════════════╬═════════════════════════════════════════════╬═════════════════╣");
            System.out.printf("║ %-20s ║ RM%-15.2f ║ %-43s ║ %-15d ║\n", roomTypes[i], prices[i], descParts[0], available);
        
            for (int j = 1; j < descParts.length; j++) {
                System.out.printf("║ %-20s ║ %-17s ║ %-43s ║ %-15s ║\n", "", "", descParts[j], "");
            }
        }
        System.out.println("╚══════════════════════╩═══════════════════╩═════════════════════════════════════════════╩═════════════════╝");
    }
}