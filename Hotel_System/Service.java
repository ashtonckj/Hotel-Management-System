import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Service {
    
    private String serviceId;
    private String serviceName;
    private String serviceDesc;
    private LocalTime serviceOpeningTime;
    private LocalTime serviceClosingTime;

    public Service () {

    }

    public Service (String serviceId, String serviceName, String serviceDesc, LocalTime serviceOpeningTime, LocalTime serviceClosingTime) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceOpeningTime = serviceOpeningTime;
        this.serviceClosingTime = serviceClosingTime;
    }

    public void displayServices() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("╔════════════╦══════════════════╦════════════════════════════════════════════════════════════════════════════════════════════╦════════════╦════════════╗");
        System.out.printf("║ %-10s ║ %-16s ║ %-90s ║ %-10s ║ %-10s ║\n", "Service ID", "Service Name", "Description", "Start Time", "End Time");

        try (BufferedReader reader = new BufferedReader(new FileReader("Service.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 5) {
                    String serviceId = parts[0].trim();
                    String serviceName = parts[1].trim();
                    String serviceDesc = parts[2].trim();
                    LocalTime serviceOpeningTime = LocalTime.parse(parts[3].trim(), timeFormatter);
                    LocalTime serviceClosingTime = LocalTime.parse(parts[4].trim(), timeFormatter);
                    System.out.println("╠════════════╬══════════════════╬════════════════════════════════════════════════════════════════════════════════════════════╬════════════╬════════════╣");
                    System.out.printf("║ %-10s ║ %-16s ║ %-90s ║ %-10s ║ %-10s ║\n", serviceId, serviceName, serviceDesc, serviceOpeningTime, serviceClosingTime);
                }
            }
            System.out.println("╚════════════╩══════════════════╩════════════════════════════════════════════════════════════════════════════════════════════╩════════════╩════════════╝");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}