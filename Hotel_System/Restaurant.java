import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Facility{
    private String facilityID;
    private String facilityName;
    private String description;
    private String facilityFloor;
    private LocalTime openHour;
    private LocalTime closeHour;
    
    public Restaurant(String facilityID, String facilityName, String description, String facilityFloor, LocalTime openHour, LocalTime closeHour) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.description = description;
        this.facilityFloor = facilityFloor;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    //Getter & Setter
    public String getFacilityID() {
        return facilityID;
    }
    public String getFacilityName() {
        return facilityName;
    }
    public String getDescription() {
        return description;
    }
    public String getFacilityFloor() {
        return facilityFloor;
    }
    public LocalTime getOpenHour() {
        return openHour;
    }
    public LocalTime getCloseHour() {
        return closeHour;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFacilityFloor(String facilityFloor) {
        this.facilityFloor = facilityFloor;
    }
    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }
    public void setCloseHour(LocalTime closeHour) {
        this.closeHour = closeHour;
    }

    public static List<Restaurant> loadRestaurantFacility() {
        List<Restaurant> list = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("Restaurant.txt"))) {
            String line;
            int lineNum = 0;
    
            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1); // Remove BOM if present
                }
    
                if (line.trim().isEmpty()) continue;
    
                try {
                    String[] parts = line.split(",");
    
                    if (parts.length >= 6) {
                        String facilityID = parts[0].trim();
                        String facilityName = parts[1].trim();
                        String description = parts[2].trim();
                        String facilityFloor = parts[3].trim();
                        LocalTime openHour = LocalTime.parse(parts[4].trim());
                        LocalTime closeHour = LocalTime.parse(parts[5].trim());
    
                        list.add(new Restaurant(facilityID, facilityName, description, facilityFloor, openHour, closeHour));
                    } else {
                        System.out.println("Invalid line format at line " + lineNum + ": " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line " + lineNum + ": " + line);
                    e.printStackTrace();
                }
            }
    
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    
        return list;
    }
    

    @Override
    public String toString() {
        return facilityName +" "+description+ " " + facilityFloor + " (" + openHour + "-" + closeHour+")";
    }
}