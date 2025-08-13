import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwimmingPool implements Facility {
    private String facilityID;
    private String facilityName;
    private String description;
    private String facilityFloor;
    private String closeDay;
    private String poolType;
    private String depthRange;
    private String lifeguardOnDuty;

    public SwimmingPool(String facilityID, String facilityName, String description, String facilityFloor, String closeDay, String poolType, String depthRange, String lifeguardOnDuty) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.description = description;
        this.facilityFloor = facilityFloor;
        this.closeDay = closeDay;
        this.poolType = poolType;
        this.depthRange = depthRange;
        this.lifeguardOnDuty = lifeguardOnDuty;
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
    public String getCloseDay() {
        return closeDay;
    }
    public String getPoolType() {
        return poolType;
    }
    public String getDepthRange() {
        return depthRange;
    }
    public String isLifeguardOnDuty() {
        return lifeguardOnDuty;
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
    public void setCloseDay(String closeDay) {
        this.closeDay = closeDay;
    }
    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }
    public void setDepthRange(String depthRange) {
        this.depthRange = depthRange;
    }
    public void setLifeguardOnDuty(String lifeguardOnDuty) {
        this.lifeguardOnDuty = lifeguardOnDuty;
    }

    public static List<SwimmingPool> loadSwimmingPoolFacility() {
        List<SwimmingPool> SwimmingPoolList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("SwimmingPool.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                String facilityID = parts[0].trim();
                String facilityName = parts[1].trim();
                String description = parts[2].trim();
	            String facilityFloor= parts[3].trim();
	            String closeDay= parts[4].trim();
                String poolType= parts[5].trim();
                String depthRange= parts[6].trim();
                String liftguardDuty= parts[7].trim();

                SwimmingPool sp = new SwimmingPool(facilityID, facilityName, description,facilityFloor,closeDay,poolType,depthRange,liftguardDuty);
                SwimmingPoolList.add(sp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SwimmingPoolList;
    }

    public boolean swimmingPoolOpenDay(LocalDate date) {
        String today = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        return !today.equalsIgnoreCase(closeDay);
    }
   
    @Override
    public String toString() {
        return "\n"+facilityName +"\nDescription: "+description+ "\nFloor: " + facilityFloor + "\nClosed date: " +closeDay+"\nLife Guard Duty: "+lifeguardOnDuty;
    }
}
