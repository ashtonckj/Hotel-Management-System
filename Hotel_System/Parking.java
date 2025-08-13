import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parking implements Facility{
    private String facilityID;
    private String facilityName;
    private String facilityFloor;

    public Parking(String facilityID, String facilityName, String facilityFloor) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.facilityFloor = facilityFloor;
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

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public void setFacilityFloor(String facilityFloor) {
        this.facilityFloor = facilityFloor;
    }

    public static List<Parking> loadParkingFacility() {
        List<Parking> ParkingList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Parking.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");

                String facilityID= parts[0].trim();
                String facilityName= parts[1].trim();
                String facilityFloor= parts[2].trim();

                Parking pk = new Parking(facilityID, facilityName,facilityFloor);
                ParkingList.add(pk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ParkingList;
    }

    
}