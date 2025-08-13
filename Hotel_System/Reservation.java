import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation {
    private String reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingStatus;

    public void generateNewReservation() {
        Scanner scanner = new Scanner(System.in);
        int maxID = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 5) {
                    reservationId = parts[0].trim();
                    int idNum = Integer.parseInt(reservationId.substring(4));
                    if (idNum > maxID) maxID = idNum;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Reservation.txt");
            return;
        }

        String roomId, guestId, checkInStr, checkOutStr;
        while (true) {
            System.out.print("Enter Room ID (e.g. R1501A): ");
            roomId = scanner.nextLine().trim();
            if (!roomId.matches("^R\\d{4}[A-B]$")) {
                System.out.println("Invalid Room ID. Format should be like R1501A.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter Guest ID (e.g. 24G0001): ");
            guestId = scanner.nextLine().trim();
            if (!guestId.matches("^\\d{2}G\\d{4}$")) {
                System.out.println("Invalid Guest ID. Format should be like 24G0001.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
            checkInStr = scanner.nextLine().trim();
            try {
                LocalDate checkInDate = LocalDate.parse(checkInStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }

        // Validate check-out date
        while (true) {
            System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
            checkOutStr = scanner.nextLine().trim();
            try {
                LocalDate checkInDate = LocalDate.parse(checkInStr, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOutStr, formatter);
                if (!checkOutDate.isAfter(checkInDate)) {
                    System.out.println("Check-Out date must be after Check-In date.");
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }

        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        String newReservationID = String.format("%sRS%04d", year, maxID + 1);

        // Create line for Reservation.txt
        String newLine = String.join(", ", newReservationID, roomId, guestId, checkInStr, checkOutStr);

        // Append to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reservation.txt", true))) {
            writer.newLine();
            writer.write(newLine);
            System.out.println("Reservation recorded successfully with ID: " + newReservationID);
        } catch (IOException e) {
            System.out.println("Error writing to Reservation.txt");
        }
    }
}