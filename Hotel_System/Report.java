import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Report extends Manager {
    private String reportTitle;
    private LocalDate reportDate;
    private static String content; 
    Scanner scanner = new Scanner(System.in);

    public Report(){
        this.reportTitle = "";
        this.reportDate = LocalDate.now();
        content = "";
    }
    public Report (String reportTitle, LocalDate reportDate) {
        this.reportTitle = reportTitle;
        this.reportDate = reportDate;
        content = ""; // initialize
    }
    public static void askSaveReport(Scanner scanner){
        System.out.print("Do you want to save this report to a file? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the filename to save the report(.txt): ");
            String saveFileName = scanner.nextLine();
            try {
                File saveFile = new File(saveFileName);

                // Check if the file's directory exists
                File parentDir = saveFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    System.out.println("The file does not exist. Please check the file path.");
                    return;  // Exit or ask for a new path
                }

                saveToFile(saveFileName);  // Save report to the file
                System.out.println("Report successfully saved to " + saveFileName);

            } catch (Exception e) {
                System.out.println("Error occurred while saving the file: " + e.getMessage());
            }
        }
    }

    public static void generateReservationReport(Scanner scanner) {
        StringBuilder reportContent = new StringBuilder();

        System.out.print("Enter the year for the report (e.g., 2024): ");
        int targetYear;
        try {
            targetYear = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year entered.");
            return;
        }

        reportContent.append("\n")
            .append("-".repeat(25)).append(" Reservation Report for ").append(targetYear).append(" ")
            .append("-".repeat(25)).append("\n");

        Map<String, List<String[]>> groupedReservations = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader("reservation.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String reservationID = parts[0].trim();
                    String roomID = parts[1].trim();
                    String guestID = parts[2].trim();
                    String checkIn = parts[3].trim();
                    String checkOut = parts[4].trim();
                    LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                    if (checkInDate.getYear() == targetYear) {
                        String month = checkInDate.getMonth().toString();
                        String key = String.format("%02d - %s", checkInDate.getMonthValue(), month); 
                        groupedReservations.putIfAbsent(key, new ArrayList<>());
                        groupedReservations.get(key).add(new String[]{reservationID, roomID, guestID, checkIn, checkOut});
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reservation.txt: " + e.getMessage());
            return;
        }

        if (groupedReservations.isEmpty()) {
            reportContent.append("No reservation records found for year ").append(targetYear).append(".\n");
        } else {
            for (String month : groupedReservations.keySet()) {
                List<String[]> reservations = groupedReservations.get(month);
                reportContent.append("\n").append("=".repeat(20)).append(" ").append(month).append(" ").append("=".repeat(20)).append("\n");
                reportContent.append(String.format("%-14s | %-8s | %-10s | %-12s | %-12s\n", "ReservationID", "RoomID", "GuestID", "Check-In", "Check-Out"));
                reportContent.append("-".repeat(68)).append("\n");
                for (String[] r : reservations) {
                    reportContent.append(String.format("%-14s | %-8s | %-10s | %-12s | %-12s\n", r[0], r[1], r[2], r[3], r[4]));
                }
                reportContent.append(String.format("Total Reservations: %d\n", reservations.size()));  
            }
        }
        Report.content = reportContent.toString();
        System.out.println(content);
        askSaveReport(scanner);
    }

    public static void generateInventoryReport(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        sb.append("Inventory Report for ").append(formattedDate).append("\n");
        sb.append("-".repeat(80)).append("\n");
        sb.append(String.format("|%-32s | %-30s | %-10s|%n", "Category", "Product Name", "Quantity"));
        sb.append("-".repeat(80)).append("\n");
    
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                if (parts.length >= 4) {
                    String category = parts[2];
                    String productName = parts[1];
                    String quantity = parts[3];
    
                    String formattedLine = String.format("|%-32s | %-30s | %-10s|%n", category, productName, quantity);
                    sb.append(formattedLine);
                }
            }
            Report.content = sb.toString();
            System.out.println(Report.content);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        askSaveReport(scanner);
    }

    public static void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.print(content);
            System.out.println("Report saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
