import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Toilet implements Facility {
    private String facilityID;
    private String facilityName;
    private String facilityFloor;

    public Toilet(String facilityID, String facilityName, String facilityFloor) {
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
   
    public static List<Toilet> loadToiletFacility() {
        List<Toilet> ToiletList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Toilet.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                String facilityID= parts[0].trim();
                String facilityName= parts[1].trim();
                String facilityFloor= parts[2].trim();

                Toilet tl = new Toilet(facilityID, facilityName,facilityFloor);
                ToiletList.add(tl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return ToiletList;
    }
}