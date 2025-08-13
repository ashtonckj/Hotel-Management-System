import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Housekeeper extends Employee{
    private String roomAssigned;
    private boolean isOnDuty;

    public Housekeeper(String employeeId, String employeeName,String employeePosition){
        super(employeeId, employeeName, employeePosition);
        this.roomAssigned = "";
        this.isOnDuty = false;
    }
    public static List<Housekeeper> loadHousekeeperEmployees(String filename) {
        List<Housekeeper> HousekeeperList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String employeeId = parts[0];
                String name = parts[1];
                String employeePosition = parts[2];

                Housekeeper hk = new Housekeeper(employeeId, name, employeePosition);
                HousekeeperList.add(hk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HousekeeperList;
    }
    public void clockIn() {
        isOnDuty = true;
    }
    public void clockOut() {
        isOnDuty = false;
    }
    public boolean isAvailable() {
        return isOnDuty;
    }
    public void setRoomAssigned(String roomAssigned){
        this.roomAssigned = roomAssigned;
    }
    public String getRoomAssigned(){
        return roomAssigned;
    }
    public String toString(){
        return super.toString()+"Room Assigned: "+this.roomAssigned;
    }
}
