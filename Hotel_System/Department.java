import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Department {
	private String departmentID;
	private String departmentName;
	private int NoOfEmployee;
	
	public Department(){
    	this.departmentID = "";
		this.departmentName = "";
		this.NoOfEmployee = 0;
    }	
    public Department(String departmentID, String departmentName, int NoOfEmployee) {
    	this.departmentID = departmentID;
		this.departmentName = departmentName;
		this.NoOfEmployee = NoOfEmployee;
    }
    //For Employee info
    public Department(String departmentName) {
    	this.departmentName = departmentName;
    }
    public static List<Department> loadDepartment() {
        List<Department> DeptList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("department.txt"))) {
			String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String departmentID = parts[0];
                String departmentName = parts[1];     
				int NoOfEmployee = Integer.parseInt(parts[2]);  
                Department dept = new Department(departmentID,departmentName,NoOfEmployee);
                DeptList.add(dept);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DeptList;
    }
    public int getNoOfEmployee(){
        return NoOfEmployee;
    }
    public String getDepartmentName(){
        return departmentName;
    }
    public void setNoOfEmployeeFromEmployeeList(List<Employee> employeeList) {
        int count = 0;
        for (Employee emp : employeeList) {
            if (this.departmentName.equals(emp.getDepartmentName()) && "Active".equalsIgnoreCase(emp.getStatus())) {
                count++;
            }
        }
        this.NoOfEmployee = count;
    }
    public static void updateNoOfEmployees(List<Employee> employeeList) {
        List<Department> deptList = loadDepartment();
        for (Department dept : deptList) {
            dept.setNoOfEmployeeFromEmployeeList(employeeList);
        }
        try (PrintWriter writer = new PrintWriter("department.txt")) {
            for (Department dept : deptList) {
                writer.println(dept.departmentID + "," + dept.departmentName + "," + dept.NoOfEmployee);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Updated number of employees in department.txt.");
    }
    public String toString(){
        return "\nDepartment ID: "+this.departmentID+"\nDepartment name: "+this.departmentName+"\nNumber of employees: "+this.NoOfEmployee;
    }
    
}