import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeShift extends Employee {
	private String restDay;
	private String shiftTime;
	
    public EmployeeShift(String employeeId,String employeeName, String employeePosition, String restDay, String shiftTime) {
    	super(employeeId, employeeName,employeePosition);
    	this.restDay = restDay;
		this.shiftTime = shiftTime;
    }
    public static List<EmployeeShift> loadEmployeeShift() {
        List<EmployeeShift> empShiftList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("employeeShift.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String employeeId = parts[0];
                String employeeName = parts[1];
                String employeePosition = parts[2];
                String restDay = parts[3];
                String shiftTime = parts[4];

                EmployeeShift es = new EmployeeShift(employeeId, employeeName, employeePosition,restDay,shiftTime);
                empShiftList.add(es);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empShiftList;
    }
    public static String searchShift(List<EmployeeShift> list, String ID) {
        String seperator = "=====================";
        for (EmployeeShift emp : list) {
            if (emp.getEmployeeID().equalsIgnoreCase(ID)) {
                return  seperator+"\nEmployee "+ ID+"\nName: "+emp.getEmployeeName()+"\nPosition: "+emp.getPosition()+"\nShift Time:" + emp.shiftTime + "\nRest Day: " + emp.restDay+"\n"+seperator;
            }
        }
        return "Employee not found or ID doesn't match.";
    }
    public String toString(){
        return "Shift Date: "+this.restDay+"Shift Time: "+this.shiftTime;
    }
    
}