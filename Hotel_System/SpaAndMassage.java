import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SpaAndMassage implements Facility {
    private String facilityID;
    private String facilityName;
    private String facilityFloor;
    private LocalTime openHour;
    private LocalTime closeHour;
    
    public SpaAndMassage(String facilityID, String facilityName, String facilityFloor, LocalTime openHour,LocalTime closeHour) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
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
    public void setFacilityFloor(String facilityFloor) {
        this.facilityFloor = facilityFloor;
    }
    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }
    public void setCloseHour(LocalTime closeHour) {
        this.closeHour = closeHour;
    }
    
    public static List<SpaAndMassage > loadSpaAndMassageFacility() {
        List<SpaAndMassage > SpaAndMassageList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("SpaAndMassage.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                String facilityID= parts[0].trim();
                String facilityName= parts[1].trim();
                String facilityFloor= parts[2].trim();
	            LocalTime openHour= LocalTime.parse(parts[3].trim());
	            LocalTime closeHour= LocalTime.parse(parts[4].trim());

                SpaAndMassage  sm= new SpaAndMassage (facilityID, facilityName,facilityFloor,openHour,closeHour);
                SpaAndMassageList.add(sm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SpaAndMassageList;
    }
    public boolean spaAndMassageOpenTime(LocalTime time) {
        return !time.isBefore(openHour) && !time.isAfter(closeHour);
    }
    public boolean isOpenNow() {
        return spaAndMassageOpenTime(LocalTime.now());
    }

    @Override
    public String toString() {
        return facilityName + " " + facilityFloor + " (" + openHour + "-" + closeHour+")";
    }
}