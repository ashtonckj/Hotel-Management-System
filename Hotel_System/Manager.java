import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    private String departmentManaged;

    public Manager(){
        this.departmentManaged = "";
    }
    public Manager(String employeeId, String employeeName, String departmentManaged, String employeePosition){
        super(employeeId, employeeName,employeePosition);
        this.departmentManaged = departmentManaged;
    } 
    public static List<Manager> loadManager() {
        List<Manager> ManagerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("manager.txt"))) {
			String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String employeeId = parts[0];
				String employeeName= parts[1];
                String departmentName = parts[2];
				String employeePosition = parts[3];        

                Manager employee = new Manager(employeeId, employeeName, departmentName, employeePosition);
                ManagerList.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ManagerList;
    }
    public String toString(){
        return super.toString()+"\nDepartment Managed: "+this.departmentManaged;
    }
}
