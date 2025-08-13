import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;

public class HousekeepingTask{
    private String taskId;          
    private String taskName;        
    private String roomNumber;       // Room assigned for cleaning
    private String employeeAssigned;       // Employee ID or name
    private LocalDate dateAssigned;  // Date the task was created
    private String housekeepingTaskStatus;           // E.g., "Pending", "In Progress", "Completed"

    public HousekeepingTask(String taskId, String taskName){
        this.taskId = taskId;
        this.taskName = taskName;
        this.roomNumber = "";
        this.employeeAssigned = "";
        this.dateAssigned = LocalDate.now();
        this.housekeepingTaskStatus = "";
    }
    public static List<HousekeepingTask> loadHousekeepingTask() {
        List<HousekeepingTask> HousekeepingTaskList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("housekeepingTask.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String taskId = parts[0];
                String taskName = parts[1];

                HousekeepingTask housekeepingTask = new HousekeepingTask(taskId, taskName);
                HousekeepingTaskList.add(housekeepingTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HousekeepingTaskList;
    }
    public static List<HousekeepingTask> loadAssignedTasks() {
        List<HousekeepingTask> assignedTasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("assignedTasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String roomNumber = parts[0];
                    String taskId = parts[1];
                    String taskName = parts[2];
                    String employeeAssigned = parts.length > 3 ? parts[3] : "";
                    String status = parts.length > 4 ? parts[4] : "";
    
                    HousekeepingTask task = new HousekeepingTask(taskId, taskName);
                    task.roomNumber = roomNumber;
                    task.employeeAssigned = employeeAssigned;
                    if(status.isEmpty()){
                        status = "Not Assigned";
                    }
                    task.housekeepingTaskStatus = status;
                    assignedTasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assignedTasks;
    }
    public static List<String> loadValidRoomIds() {
        List<String> validRoomIds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Room.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    validRoomIds.add(parts[0].trim()); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading room.txt");
            e.printStackTrace();
        }
        return validRoomIds;
    }
    public static void printTable(List<HousekeepingTask> taskList, Scanner scanner) {
        System.out.println("===============================================");
        System.out.printf("%-10s | %-30s\n", "Task ID", "Task Name");
        System.out.println("===========+===================================");
        for (HousekeepingTask task : taskList) {
            System.out.printf("%-10s | %-30s\n", task.taskId, task.taskName);
        }
    }
    public static void AssignHousekeepingTask(List<HousekeepingTask> taskList, Scanner scanner){
        System.out.print("Do you want to assigned a housekeeping task? (Y/[any key]): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            System.out.print("Enter the Task ID to assign: ");
            String taskId = scanner.nextLine();
            for (HousekeepingTask task : taskList) {
                if (task.taskId.equalsIgnoreCase(taskId)) {
                    System.out.print("Insert roomId to assigned housekeeping task: ");
                    String roomId = scanner.nextLine();
                    List<String> validRooms = loadValidRoomIds();
                    if (!validRooms.contains(roomId)) {
                        System.out.println("Room ID does not exist. Assignment cancelled.");
                        return;
                    }
                    task.roomNumber = roomId;
                    task.updateStatusInFile();
                    break;
                }
            }
            System.out.println("No such task exist");
        }
    }
    public static void updateTaskStatus(List<HousekeepingTask> taskList, Scanner scanner){
        System.out.println("============================================================");
        System.out.printf("%-10s | %-25s | %-10s | %-10s\n", "Task ID", "Task Name", "Room ID", "Status");
        System.out.println("===========+===========================+===========+=========");

        List<HousekeepingTask> assignedTaskList = loadAssignedTasks();
        for (HousekeepingTask task : assignedTaskList) {
            if (!task.housekeepingTaskStatus.equalsIgnoreCase("Completed")) {
                System.out.printf("%-10s | %-25s | %-10s | %-10s\n", task.taskId, task.taskName, task.roomNumber, task.housekeepingTaskStatus);
            }
        }

        System.out.print("Enter your employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Enter the roomId you want to update: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Enter the taskId you want to update: ");
        String taskId = scanner.nextLine();
        System.out.print("Enter status (Completed/In Progress): ");
        String status = scanner.nextLine();
        if (!status.equalsIgnoreCase("Completed") && !status.equalsIgnoreCase("In Progress")) {
            System.out.println("Invalid input for status");
            return;
        }

        boolean found = false;
        for (HousekeepingTask task : assignedTaskList) {
            if (task.roomNumber.equalsIgnoreCase(roomNumber) && task.taskId.equalsIgnoreCase(taskId)) {
                task.employeeAssigned = employeeId;
                task.housekeepingTaskStatus = status;
                task.updateStatusInFile(); 
                found = true;
                System.out.println("Status updated successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("No task found for the provided room number and task ID.");
        }

}
public static void printTask(){
    System.out.println("============================================================");
    System.out.printf("%-10s | %-25s | %-10s | %-10s\n", "Task ID", "Task Name", "Room ID", "Status");
    System.out.println("===========+===========================+===========+=========");

    List<HousekeepingTask> assignedTaskList = loadAssignedTasks();
    for (HousekeepingTask task : assignedTaskList) {
        System.out.printf("%-10s | %-25s | %-10s | %-10s\n", task.taskId, task.taskName, task.roomNumber, task.housekeepingTaskStatus);
    }
}
    public void updateStatusInFile() {
		List<String> updatedLines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("assignedTasks.txt"))) {
        String line;
        boolean updated = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[1].equalsIgnoreCase(this.taskId)) {
                String updatedLine = String.join(",", this.roomNumber, this.taskId, this.taskName, this.employeeAssigned, this.housekeepingTaskStatus);
                updatedLines.add(updatedLine);
                updated = true;
            } else {
                updatedLines.add(line); 
            }
        }
        if (!updated) {
            String newLine = String.join(",", this.roomNumber, this.taskId, this.taskName, this.employeeAssigned, this.housekeepingTaskStatus);
            updatedLines.add(newLine);
        }
    } catch (IOException e) {
        System.out.println("Error reading assignedTasks.txt");
        return;
    }
    // Overwrite file with updated content
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("assignedTasks.txt"))) {
        for (String updatedLine : updatedLines) {
            writer.write(updatedLine);
            writer.newLine();
        }
        System.out.println("Assigned task updated in file.");
    } catch (IOException e) {
        System.out.println("Failed to update assigned task.");
        e.printStackTrace();
    }
}
    public String toString(){
        return "TaskId: "+this.taskId+"\nTask Name: "+this.taskName+"\nRoom Number: "+this.roomNumber+"\nEmployee Assigned: "+this.employeeAssigned+"DAte Assigned: "+this.dateAssigned+"/nStatus: "+this.housekeepingTaskStatus;  
    }
}


