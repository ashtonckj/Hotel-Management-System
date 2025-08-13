import java.util.List;
import java.util.Scanner;
public class Hotel_System {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Gym> gyms = Gym.loadGymFacility();
        List<Restaurant> restaurants = Restaurant.loadRestaurantFacility();
        List<SwimmingPool> pools = SwimmingPool.loadSwimmingPoolFacility();
        List<SpaAndMassage> spas = SpaAndMassage.loadSpaAndMassageFacility();
        List<FrontDesk> staff = FrontDesk.loadFrontDeskEmployees();
        List<EmployeeShift> shiftList = EmployeeShift.loadEmployeeShift();
        List<Employee> emp = Employee.loadEmployee();
        List<Department> dept = Department.loadDepartment();
        List<Inventory> inventoryy = Inventory.loadInventory();
        List<Manager> managers = Manager.loadManager();
        List<HousekeepingTask> tasks = HousekeepingTask.loadHousekeepingTask();
        Room room = new Room();
        Guest guest = new Guest();
        Service service = new Service();
        Reservation reservation = new Reservation();
        Invoice invoice = new Invoice();
        System.out.println("888    888                            888          888b     d888          \r\n" + //
                        "888    888                            888          8888b   d8888          \r\n" + //
                        "888    888                            888          88888b.d88888          \r\n" + //
                        "8888888888 888  888  .d88b.   .d88b.  888  .d88b.  888Y88888P888  .d88b.  \r\n" + //
                        "888    888 888  888 d88P\"88b d88P\"88b 888 d8P  Y8b 888 Y888P 888 d8P  Y8b \r\n" + //
                        "888    888 888  888 888  888 888  888 888 88888888 888  Y8P  888 88888888 \r\n" + //
                        "888    888 Y88b 888 Y88b 888 Y88b 888 888 Y8b.     888   \"   888 Y8b.     \r\n" + //
                        "888    888  \"Y88888  \"Y88888  \"Y88888 888  \"Y8888  888       888  \"Y8888  \r\n" + //
                        "                         888      888                                     \r\n" + //
                        "                    Y8b d88P Y8b d88P                                     \r\n" + //
                        "                     \"Y88P\"   \"Y88P\" ");
        System.out.println("=".repeat(73));
        System.out.println(" ".repeat(25)+"LOG IN -- Page");
        System.out.println("=".repeat(73)); 
        System.out.print("EmployeeID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Employee loggedIn = null;

        // check if employee exists and is active
        for (Employee e : emp) {
            if (e.getEmployeeID().equalsIgnoreCase(employeeId) && e.getStatus().equalsIgnoreCase("Active")&& password.equals(e.getEmployeeIC())) {
                loggedIn = e;
                break;
            }
        }
        if (loggedIn == null) {
            System.out.println("Login failed: Invalid ID or inactive employee.");
            return;
        }
        Manager loggedInManager = null;
        for (Manager m : managers) {
            if (m.getEmployeeID().equalsIgnoreCase(employeeId)) {
                loggedInManager = m;
                break;
            }
        }
        if (loggedInManager != null) {
            System.out.println("Manager Access Detected.");
            System.out.println("Welcome, " + loggedInManager.getEmployeeName() + "!");
            String department = loggedIn.getDepartmentName();
            System.out.print("Would you like to generate a report for your department? (yes/[any key]): ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("yes")) {
                switch (department) {
                    case "FrontDesk":
                        Report.generateReservationReport(scanner);
                        break;
                    case "Inventory":
                        Report.generateInventoryReport(scanner);
                        break;
                    default:
                        System.out.println("No report defined for this department.");
                }
                return; 
            }
        }
        System.out.println("\nRegular Employee Version");
        System.out.println("Welcome, " + loggedIn.getEmployeeName() + "!");
        String department = loggedIn.getDepartmentName();
        switch (department) {
            case "Housekeeping":
                System.out.println("Accessing Housekeeping System...");
                HousekeepingTask.printTable(tasks, scanner);
                boolean menuH = true;
                while (menuH){
                    System.out.println("=".repeat(30));
                    System.out.println(" ".repeat(10)+"Menu");
                    System.out.println("=".repeat(30));
                    System.out.println("1) Assign a task\n2) All task status\n3) Update Task status\n4) Exit\nChoice: ");
                    String choiceH = scanner.nextLine();
                    switch (choiceH){
                        case "1":
                            HousekeepingTask.AssignHousekeepingTask(tasks, scanner);
                            break;
                        case "2":
                            HousekeepingTask.printTask();
                            break;
                        case "3":
                            HousekeepingTask.updateTaskStatus(tasks, scanner);
                            break;
                        case "4":
                            menuH = false;
                            break;
                    }
                }
                break;
            case "FrontDesk":
                System.out.println("Accessing Front Desk System...");
                boolean menu = true;
                while (menu){
                    System.out.println("=".repeat(30));
                    System.out.println(" ".repeat(10)+"Menu");
                    System.out.println("=".repeat(30));
                    System.out.print("1) Edit employee status\n2) Search for employee shift\n3) Generate room table summary\n4) Search Available Rooms");
                    System.out.print("\n5) Search Guest\n6) Register Guest\n7) Display Services\n8) Generate New Reservation");
                    System.out.print("\n9) Invoice Generator\n10) Generate New Invoice\n11) Generate Invoice Table\n12) Exit\nChoice: ");
                    String action = scanner.nextLine();
                    switch (action){
                        case "1":
                            Employee.employeeStatusEdit(); 
                            Department.updateNoOfEmployees(emp);
                            break;
                        case "2":
                            System.out.print("Enter the employeeID: "); 
                            String id = scanner.nextLine();
                            String result = EmployeeShift.searchShift(shiftList, id);
                            System.out.println(result);
                            break;
                        case "3":
                            room.GenerateRoomTypeSummaryAvailability();
                            break;
                        case "4":
                            System.out.println();
                            room.SearchRoomTypeAvailability();
                            break;
                        case "5":
                            System.out.println();
                            guest.SearchGuest();
                            break;
                        case "6":
                            guest.RegisterGuest();
                            break;
                        case "7":
                            service.displayServices();
                            break;
                        case "8":
                            reservation.generateNewReservation();
                            break;
                        case "9":
                            invoice.InvoiceGenerator();
                            break;
                        case "10":
                            invoice.generateInvoiceForReservation();
                            break;
                        case "11":
                            invoice.generateInvoiceForm();
                            break;
                        case "12":
                            menu = false;
                            break;
                        default:
                            System.out.println("Invalid input");
                    }
                }
                break;
            case "Inventory":
                System.out.println("Accessing Inventory System...");
                boolean loop = true;
                while (loop){
                    System.out.println("=".repeat(30));
                    System.out.println(" ".repeat(10)+"Menu");
                    System.out.println("=".repeat(30));
                    System.out.print("1) Inventory List\n2) Check specific product Information\n3) Update product information\n4) Exit\nChoice: ");
                    String selection = scanner.nextLine();
                    if (selection.equalsIgnoreCase("1")||selection.equalsIgnoreCase("2")||selection.equalsIgnoreCase("3")||selection.equalsIgnoreCase("4")) {
                        switch(selection){
                            case "1":
                                Inventory.printInventory(inventoryy);
                                break;
                            case "2":
                                System.out.print("Enter the product ID to search: ");
                                String productID = scanner.nextLine();
                                Inventory.searchProductByID(productID);
                                break;
                            case "3":
                                    System.out.print("Enter productID: ");
                                    String option = scanner.nextLine();
                                    System.out.print("Enter updated quantity: ");
                                    int quantity = scanner.nextInt();
                                    scanner.nextLine();//avoid menu loop again
                                    Inventory.updateInventoryQuantity(option, quantity);
                                    break;
                            case "4":
                                    loop = false;
                                    break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                    }
                }
                break;
            case "Spa & Massage":
                System.out.println("Menu");
                System.out.print("1) Open and close time\nChoice: ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("1")){
                    checkFacilityList("Spa & Massage", spas);
                }
                else{
                    System.out.println("Wrong input.");
                }
                break;
            default:
                System.out.println("No specific system assigned to this department.");
        }
    }
    private static <T> void checkFacilityList(String title, List<T> list) {
        System.out.println("\n" + title + " Facilities:");
        if (list == null || list.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        for (T item : list) {
            System.out.println(item); 
        }
    }
}
