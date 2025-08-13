import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrontDesk extends Employee{
    private String deskNum;

    public FrontDesk(String employeeId, String employeeName, String employeePosition, String deskNum){
        super(employeeId, employeeName, employeePosition);
        this.deskNum = deskNum;
    }
    public static List<FrontDesk> loadFrontDeskEmployees() {
        List<FrontDesk> frontDeskList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("frontdesk.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String employeeId = parts[0];
                String employeeName = parts[1];
                String employeePosition = parts[2];
                String deskNum = parts[3];

                FrontDesk fd = new FrontDesk(employeeId, employeeName, employeePosition,deskNum);
                frontDeskList.add(fd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return frontDeskList;
    }
    public String getDeskNum(){
        return deskNum;
    }
    public String toString(){
        return "\nEmployee ID: "+super.getEmployeeID()+"\nEmployee Name: "+super.getEmployeeName()+"\nDesk Number: "+this.deskNum;
    }
}
