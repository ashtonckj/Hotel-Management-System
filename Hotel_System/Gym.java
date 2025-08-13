import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Gym implements Facility {
    private String facilityID;
    private String facilityName;
    private String facilityFloor;
    private LocalTime openHour;
    private LocalTime closeHour;
    
    public Gym(String facilityID, String facilityName, String facilityFloor, LocalTime openHour, LocalTime closeHour) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.facilityFloor = facilityFloor;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    // Getter & Setter methods
    public String getFacilityID() {
        return facilityID;
    }
    
    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }
    
    public String getFacilityName() {
        return facilityName;
    }
    
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    
    public String getFacilityFloor() {
        return facilityFloor;
    }
    
    public void setFacilityFloor(String facilityFloor) {
        this.facilityFloor = facilityFloor;
    }
    
    public LocalTime getOpenHour() {
        return openHour;
    }
    
    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }
    
    public LocalTime getCloseHour() {
        return closeHour;
    }
    
    public void setCloseHour(LocalTime closeHour) {
        this.closeHour = closeHour;
    }

    public static List<Gym > loadGymFacility() {
        List<Gym > GymList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Gym.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                String facilityID= parts[0].trim();
                String facilityName= parts[1].trim();
                String facilityFloor= parts[2].trim();
	            LocalTime openHour= LocalTime.parse(parts[3].trim());
	            LocalTime closeHour= LocalTime.parse(parts[4].trim());

                Gym  gm = new Gym (facilityID,facilityName,facilityFloor,openHour,closeHour);
                GymList.add(gm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return GymList;
    }

    public boolean gymOpenTime(LocalTime now) {
        return !now.isBefore(openHour) && !now.isAfter(closeHour);
    }
    
    public boolean isOpenNow() {
        return gymOpenTime(LocalTime.now());
    }
   
    @Override
    public String toString() {
        return facilityName + " " + facilityFloor + " (" + openHour + "-" + closeHour+")";
    }

}