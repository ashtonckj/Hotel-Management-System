import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Invoice {
    private String GayMoment;

    public void InvoiceGenerator() {
        String[][] roomData = new String[1000][3]; // [roomId, roomType, roomPrice]
        int roomCount = 0;

        String[][] reservationData = new String[1000][5]; // [reservationId, roomId, guestId, checkInDate, checkOutDate]
        int reservationCount = 0;

        int invoiceCounter = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Step 1: Read Room.txt into roomData array
        try (BufferedReader br = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            while ((line = br.readLine()) != null && roomCount < 1000) {
                String[] parts = line.trim().split(", ");
                if (parts.length >= 3) {
                    roomData[roomCount][0] = parts[0].trim(); // roomId
                    roomData[roomCount][2] = parts[2].trim(); // roomPrice
                    roomCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Room.txt");
            return;
        }

        // Step 2: Read Reservation.txt into reservationData array
        try (BufferedReader br = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = br.readLine()) != null && reservationCount < 1000) {
                String[] parts = line.trim().split(", ");
                if (parts.length >= 5) {
                    for (int i = 0; i < 5; i++) {
                        reservationData[reservationCount][i] = parts[i].trim();
                    }
                    reservationCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Reservation.txt");
            return;
        }

        // Step 3: Generate and write invoice info
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Invoice.txt", true))) {
            for (int i = 0; i < reservationCount; i++) {
                String reservationId = reservationData[i][0];
                String roomId = reservationData[i][1];
                String checkInStr = reservationData[i][3];
                String checkOutStr = reservationData[i][4];

                // Find room price for this roomId
                double roomPrice = 0.0;
                for (int j = 0; j < roomCount; j++) {
                    if (roomData[j][0].equals(roomId)) {
                        roomPrice = Double.parseDouble(roomData[j][2]);
                        break;
                    }
                }

                try {
                    LocalDate checkInDate = LocalDate.parse(checkInStr, formatter);
                    LocalDate checkOutDate = LocalDate.parse(checkOutStr, formatter);
                    long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();

                    double totalPrice = roomPrice * days;
                    double discount = totalPrice * 0.05;
                    double finalPrice = totalPrice - discount;

                    String yearPrefix = reservationId.substring(0, 2); // e.g. 24 from 24RS0001
                    String invoiceId = String.format("%sIN%04d", yearPrefix, invoiceCounter++);

                    String newLine = String.join(", ", invoiceId, reservationId, String.format("%.2f", totalPrice), String.format("%.2f", discount), String.format("%.2f", finalPrice), checkInStr, "Paid");

                    writer.write(newLine);
                    writer.newLine();

                } catch (Exception e) {
                    System.out.println("Error processing reservation: " + reservationId);
                }
            }
            System.out.println("Invoices generated and saved to Invoice.txt.");
        } catch (IOException e) {
            System.out.println("Error writing to Invoice.txt");
        }
    }

    public void generateInvoiceForReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation ID to generate invoice: ");
        String reservationIdInput = scanner.nextLine().trim();
        String[] reservation = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Step 1: Find reservation by ID
        try (BufferedReader br = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(", ");
                if (parts.length >= 5 && parts[0].trim().equals(reservationIdInput)) {
                    reservation = new String[5];
                    for (int i = 0; i < 5; i++) {
                        reservation[i] = parts[i].trim();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Reservation.txt");
            return;
        }

        if (reservation == null) {
            System.out.println("Reservation ID not found.");
            return;
        }

        String roomId = reservation[1];
        String checkInStr = reservation[3];
        String checkOutStr = reservation[4];
        double roomPrice = 0.0;

        // Step 2: Find room price by roomId
        try (BufferedReader br = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(", ");
                if (parts.length >= 3 && parts[0].trim().equals(roomId)) {
                    roomPrice = Double.parseDouble(parts[2].trim());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Room.txt");
            return;
        }

        if (roomPrice == 0.0) {
            System.out.println("Room price not found for room ID: " + roomId);
            return;
        }

        // Step 3: Calculate invoice details
        try {
            LocalDate checkInDate = LocalDate.parse(checkInStr, formatter);
            LocalDate checkOutDate = LocalDate.parse(checkOutStr, formatter);
            long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();
            if (days <= 0) days = 1;

            double totalPrice = roomPrice * days;
            double discount = totalPrice * 0.05;
            double finalPrice = totalPrice - discount;

            // Step 4: Generate new invoice ID
            String yearPrefix = reservationIdInput.substring(0, 2);
            int maxID = 0;

            try (BufferedReader br = new BufferedReader(new FileReader("Invoice.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.trim().split(",");
                    if (parts.length >= 1 && parts[0].startsWith(yearPrefix + "IN")) {
                        String numberPart = parts[0].substring(4); // after "24IN"
                        int id = Integer.parseInt(numberPart);
                        if (id > maxID) {
                            maxID = id;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                // If file doesn't exist yet, that's okay
            }

            String invoiceId = String.format("%sIN%04d", yearPrefix, maxID + 1);

            // Step 5: Write to Invoice.txt
            String newLine = String.join(", ", invoiceId, reservationIdInput, String.format("%.2f", totalPrice), String.format("%.2f", discount), String.format("%.2f", finalPrice), checkInStr, "Paid");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Invoice.txt", true))) {
                writer.newLine();
                writer.write(newLine);
                System.out.println("Invoice generated: " + invoiceId);
            } catch (IOException e) {
                System.out.println("Error writing to Invoice.txt");
            }

        } catch (Exception e) {
            System.out.println("Error processing invoice: " + e.getMessage());
        }
    }

    public void generateInvoiceForm() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter InvoiceId: ");
        String invoiceId = scanner.nextLine();
        String foundLine = null;
        
      try {
            BufferedReader invoiceReader = new BufferedReader(new FileReader("Invoice.txt"));
            BufferedReader reservationReader = new BufferedReader(new FileReader("Reservation.txt"));
            BufferedReader roomReader = new BufferedReader(new FileReader("Room.txt"));

            String invoiceLine = null, reservationID = null, invoiceDate = null, invoiceStatus = null;
            Double invoiceTotalPrice = 0.00, invoiceDiscount = 0.00, invoiceFinalPrice = 0.00;
            
            while ((invoiceLine = invoiceReader.readLine()) != null) {
                String[] invoiceParts = invoiceLine.split(", ");
                if (invoiceParts[0].trim().equals(invoiceId)) {
                    reservationID = invoiceParts[1].trim();
                    invoiceTotalPrice = Double.parseDouble(invoiceParts[2].trim());
                    invoiceDiscount = Double.parseDouble(invoiceParts[3].trim());
                    invoiceFinalPrice = Double.parseDouble(invoiceParts[4].trim());
                    invoiceDate = invoiceParts[5].trim();
                    invoiceStatus = invoiceParts[6].trim();
                    break;
                }
            }
            invoiceReader.close();

            if (reservationID == null) {
                System.out.println("Invoice ID not found.");
                return;
            }

            // Get reservation data
            String resLine;
            String roomID = null;
            LocalDate checkInDate = null, checkOutDate = null;
            while ((resLine = reservationReader.readLine()) != null) {
                String[] resParts = resLine.split(", ");
                if (resParts[0].trim().equals(reservationID)) {
                    roomID = resParts[1].trim();
                    checkInDate = LocalDate.parse(resParts[3].trim());
                    checkOutDate = LocalDate.parse(resParts[4].trim());
                    break;
                }
            }
            reservationReader.close();

            if (roomID == null) {
                System.out.println("Reservation data not found.");
                return;
            }

            // Get room data
            String roomLine;
            String roomType = null;
            double roomPrice = 0.0;
            while ((roomLine = roomReader.readLine()) != null) {
                String[] roomParts = roomLine.split(", ");
                if (roomParts[0].trim().equals(roomID)) {
                    roomType = roomParts[1].trim();
                    roomPrice = Double.parseDouble(roomParts[2].trim());
                    break;
                }
            }
            roomReader.close();

            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            // Display Invoice
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     INVOICE DETAILS                    ║");
            System.out.println("╠══════════════════════════════╦═════════════════════════╣");
            System.out.printf("║ %-28s ║ %-23s ║\n", "Invoice ID", invoiceId);
            System.out.printf("║ %-28s ║ %-23s ║\n", "Reservation ID", reservationID);
            System.out.printf("║ %-28s ║ %-23s ║\n", "Date Issued", invoiceDate);
            System.out.printf("║ %-28s ║ %-23s ║\n", "Status", invoiceStatus);
            System.out.println("╚══════════════════════════════╩═════════════════════════╝");

            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                    ROOM CHARGE BREAKDOWN                 ║");
            System.out.println("╠═════════════════╦══════════════╦══════════╦══════════════╣");
            System.out.printf("║ %-15s ║ RM %-9.2f ║ %-8d ║ RM %-9.2f ║\n", roomType, roomPrice, nights, invoiceTotalPrice);
            System.out.println("╚═════════════════╩══════════════╩══════════╩══════════════╝");

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     TOTAL & DISCOUNT                   ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-30s : RM %-9.2f          ║\n", "Subtotal", invoiceTotalPrice);
            System.out.printf("║ %-30s : -RM %-9.2f         ║\n", "Discount (5%)", invoiceDiscount);
            System.out.println("║ ------------------------------------------------------ ║");
            System.out.printf("║ %-30s : RM %-9.2f          ║\n", "Grand Total", invoiceFinalPrice);
            System.out.println("╚════════════════════════════════════════════════════════╝");

        } catch (IOException e) {
            System.out.println("Error reading files.");
        }
    }
}