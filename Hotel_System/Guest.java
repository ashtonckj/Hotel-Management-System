import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Guest {
    private String guestId;
    private String guestName;
    private String guestIC;
    private String guestPhone;
    private String guestEmail;
    private String guestMember;

    public void SearchGuest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter full IC number: ");
        String inputIC = scanner.nextLine().trim();

        boolean found = false;
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Guest.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 6) {
                    guestId = parts[0].trim();
                    guestName = parts[1].trim();
                    guestIC = parts[2].trim();
                    guestPhone = parts[3].trim();
                    guestEmail = parts[4].trim();
                    guestMember = parts[5].trim();

                    int idNum = Integer.parseInt(guestId.substring(3)); // e.g. 0001
                    if (idNum > maxID) maxID = idNum;

                    if (guestIC.equals(inputIC)) {
                        System.out.println("╔════════════╦══════════════════════════════════╦════════════════════╦══════════════════════════════════════════╦════════╗");
                        System.out.printf("║ %-10s ║ %-32s ║ %-18s ║ %-40s ║ %-6s ║\n", "Guest ID", "Name", "Phone", "Email", "Member");
                        System.out.println("╠════════════╬══════════════════════════════════╬════════════════════╬══════════════════════════════════════════╬════════╣");
                        System.out.printf("║ %-10s ║ %-32s ║ %-18s ║ %-40s ║ %-6s ║\n", guestId, guestName, guestPhone, guestEmail, guestMember);
                        System.out.println("╚════════════╩══════════════════════════════════╩════════════════════╩══════════════════════════════════════════╩════════╝");
                            found = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Guest.txt");
            return;
        }

        if (!found) {
            System.out.println("No guest found with this IC.");
        }
    }

    public void RegisterGuest() {
        Scanner scanner = new Scanner(System.in);
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Guest.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 6) {
                    String guestId = parts[0].trim();
                    int idNum = Integer.parseInt(guestId.substring(3));
                    if (idNum > maxID) maxID = idNum;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Guest.txt: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.print("Enter full name: ");
            guestName = scanner.nextLine().trim();
            if (!guestName.matches("^[A-Za-z ]{2,50}$")) {
                System.out.println("Invalid name. Only letters and spaces allowed.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter IC Number (e.g. 012345678910): ");
            guestIC = scanner.nextLine().trim();
            if (!guestIC.matches("^\\d{12}$")) {
                System.out.println("Invalid guestIC format. Format should be 012345678910.");
            }
            else {
                break;
            }
        }
    
        while (true) {
            System.out.print("Enter Phone Number (e.g. +60 12-345 6789): ");
            guestPhone = scanner.nextLine().trim();
            if (!guestPhone.matches("^\\+60\\s?1[0-9]-\\d{3,4}\\s\\d{4}$")) {
                System.out.println("Invalid guestPhone number. Format should be +60 12-3456789.");
            }
            else {
                break;
            }
        }
    
        while (true) {
            System.out.print("Enter Email Address: ");
            guestEmail = scanner.nextLine().trim();
            if (!guestEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                System.out.println("Invalid guestEmail format.");
            }
            else {
                break;
            }
        }
    
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        String newGuestID = String.format("%sG%04d", year, maxID + 1);
    
        String newLine = String.join(", ", newGuestID, guestName, guestIC, guestPhone, guestEmail, "false");
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Guest.txt", true))) {
            writer.newLine();
            writer.write(newLine);
            System.out.println("Guest registered successfully with ID: " + newGuestID);
        } catch (IOException e) {
            System.out.println("Error writing to Guest.txt");
        }
    }    
}
