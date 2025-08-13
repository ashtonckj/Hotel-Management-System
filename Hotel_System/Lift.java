import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lift implements Facility{
    private String facilityID;
    private String facilityName;
    private String description;

    public Lift (String facilityID, String facilityName, String description) {
        this.facilityID = facilityID;
        this.facilityName = facilityName;
        this.description = description;
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

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public static List<Lift> loadLiftFacility() {
        List<Lift> LiftList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Lift.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");

                String facilityID = parts[0].trim();
                String facilityName = parts[1].trim();
                String description = parts[2].trim();

                Lift lf = new Lift(facilityID, facilityName, description );
               LiftList.add(lf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LiftList;
    }

}
