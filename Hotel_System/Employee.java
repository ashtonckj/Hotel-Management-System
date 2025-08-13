import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee extends Department{
	private String employeeId;
	private String employeeName;
	private String employeeIC;
	private String employeePhoneNum;
	private String employeeEmailAddress;
	private String employeePosition;
	private double employeeSalary;
	private String employeeStatus;
	
    public Employee() {
    	this.employeeId = "";
		this.employeeName = "";
		this.employeeIC = "";
		this.employeePhoneNum = "";
		this.employeeEmailAddress = "";
		this.employeePosition = "";
		this.employeeSalary = 0.0;
		this.employeeStatus = "";
    }
    public Employee(String departmentName, String employeeId, String employeeName, String employeeIC, String employeePhoneNum, String employeePosition, String employeeEmailAddress, double employeeSalary, String employeeStatus) {
    	super(departmentName);
    	this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeIC = employeeIC;
		this.employeePhoneNum = employeePhoneNum;
		this.employeeEmailAddress = employeeEmailAddress;
		this.employeePosition = employeePosition;
		this.employeeSalary = employeeSalary;
		this.employeeStatus = employeeStatus;
    }
    //For employee shift and manager and supervisor
    public Employee(String employeeId, String employeeName, String employeePosition){
    	this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeePosition = employeePosition;
    }
	public static List<Employee> loadEmployee() {
        List<Employee> EmployeeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Employee.txt"))) {
			String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String departmentName = parts[0];
                String employeeId = parts[1];
				String employeeName= parts[2];
                String employeeIC = parts[3];
				String employeePhoneNum = parts[4];      
				String employeeEmailAddress = parts[5];
				String employeePosition = parts[6];        
				double employeeSalary = Double.parseDouble(parts[7]);  
				String employeeStatus = parts[8];

                Employee employee = new Employee(departmentName, employeeId, employeeName, employeeIC, employeePhoneNum, employeeEmailAddress, employeePosition, employeeSalary, employeeStatus);
                EmployeeList.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EmployeeList;
    }
	public String getEmployeeName(){
		return employeeName;
	}
	public String getEmployeeID(){
		return employeeId;
	}
	public String getEmployeeIC(){
		return employeeIC;
	}
	public String getPosition(){
		return employeePosition;
	}
	public void setPhoneNumber(String employeePhoneNum){
		this.employeePhoneNum =employeePhoneNum;
	}
	public void setEmailAddress(String employeeEmailAddress){
		this.employeeEmailAddress = employeeEmailAddress;
	}
	public double calculateAnnualSalary(){
		return this.employeeSalary*12;
	}
	public static void employeeStatusEdit(){
		Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Employee ID: ");
        String empID = scanner.nextLine();

        System.out.print("Enter new employeeStatus (Active/Terminated): ");
        String newStatus = scanner.nextLine();

        Employee.updateStatusInFile(empID, newStatus);
		scanner.close();
	}
	public String getStatus(){
		return employeeStatus;
	}
	public void setStatus(String employeeStatus){
		this.employeeStatus = employeeStatus;
	}
	public static void updateStatusInFile(String employeeIdToUpdate, String newStatus) {
		List<Employee> employeeList = loadEmployee();  // Load current list
		boolean found = false;
		for (Employee emp : employeeList) {
			if (emp.getEmployeeID().equals(employeeIdToUpdate)) {  
				emp.setStatus(newStatus);  // Update the employeeStatus of the employee
				found = true;
				break; 
			}
		}

		if (!found) {
			System.out.println("Employee with ID " + employeeIdToUpdate + " not found.");
			return;
		}

		// Rewrite the file with updated employee list
		try (PrintWriter writer = new PrintWriter("Employee.txt")) {
			for (Employee emp : employeeList) {
				writer.println(emp.getDepartmentName() + "," +emp.employeeId + "," +emp.employeeName + "," +emp.employeeIC + "," +
					emp.employeePhoneNum + "," +emp.employeeEmailAddress + "," +emp.employeePosition + "," +emp.employeeSalary + "," +emp.employeeStatus
				);
			}
			System.out.println("Status updated successfully in file.");
		} catch (IOException e) {
			System.out.println("Error updating file: " + e.getMessage());
		}
	}
    public String toString(){
        return "\nEmployee Id: "+this.employeeId+"\nEmployee employeeName: "+this.employeeName+"\nDepartment name: "+super.getDepartmentName()+"\nIc Num: "+this.employeeIC+"\nPhone Number: "+this.employeePhoneNum+"\nEmail Address: "+this.employeeEmailAddress+"\nPosition: "+this.employeePosition+"\nSalary: RM"+this.employeeSalary+"\nStatus: "+this.employeeStatus;
    }
    
}
