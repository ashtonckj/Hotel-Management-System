import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private String itemID;
    private String itemName;
    private String category;
    private int quantityInStock;
    private String supplierName;
    private String location;

    public Inventory(String itemID, String itemName, String category, int quantityInStock, String supplierName, String location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.category = category;
        this.quantityInStock = quantityInStock;
        this.supplierName = supplierName;
        this.location = location;
    }

    // Getters & setters 
    public String getItemID() {
        return itemID;
    }
    public String getItemName() {
        return itemName;
    }
    public String getCategory() {
        return category;
    }
    public int getQuantityInStock() {
        return quantityInStock;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public String getLocation() {
        return location;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public void setLocation(String location) {
        this.location = location;
    }    
    public static void updateInventoryQuantity(String productID, int newQuantity) {
		List<Inventory> inventoryList = loadInventory();  // Load current list
		boolean found = false;
		for (Inventory inv : inventoryList) {
			if (inv.getItemID().equals(productID)) {  
				inv.setQuantityInStock(newQuantity);  // Update the employeeStatus of the employee
				found = true;
				break; 
			}
		}
        if (found) {
            saveInventory(inventoryList);  // <-- Save changes to file
            System.out.println("Quantity updated and saved successfully.");
        } else {
            System.out.println("Product ID not found.");
        }
    }
    public static void saveInventory(List<Inventory> inventoryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Inventory.txt"))) {
            for (Inventory inv : inventoryList) {
                String line = String.join(",", inv.getItemID(), inv.getItemName(), inv.getCategory(),
                        String.valueOf(inv.getQuantityInStock()), inv.getSupplierName(), inv.getLocation());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public static List<Inventory> loadInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Inventory.txt"))) {
			String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                String itemID = parts[0];
                String itemName = parts[1];
                String category = parts[2];
                int quantityInStock = Integer.parseInt(parts[3]);
                String supplierName = parts[4];
                String location = parts[5];

                Inventory inventory = new Inventory(itemID, itemName, category, quantityInStock, supplierName, location);
                inventoryList.add(inventory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }
    public static void printInventory(List<Inventory> list){
        System.out.printf("%-5s | %-28s | %-25s | %-8s | %-20s | %-25s%n",
        "ItemID", "Item Name", "Category", "Quantity", "Supplier", "Location");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Inventory item : list) {
            System.out.printf("%-5s | %-28s | %-25s | %-8d | %-20s | %-25s%n",item.itemID, item.itemName, item.category, item.quantityInStock, item.supplierName, item.location);
        }
    }
    public static void searchProductByID(String searchID) {
        List<Inventory> inventoryList = loadInventory();
        boolean found = false;
    
        for (Inventory item : inventoryList) {
            if (item.getItemID().equalsIgnoreCase(searchID)) {
                System.out.println("Product found:");
                System.out.printf("%-5s | %-28s | %-25s | %-8s | %-20s | %-25s",
                    "ItemID", "Item Name", "Category", "Quantity", "Supplier", "Location");
                System.out.println("-".repeat(105));
                System.out.printf("%-5s | %-28s | %-25s | %-8d | %-20s | %-25s%n",
                    item.getItemID(), item.getItemName(), item.getCategory(), item.getQuantityInStock(), item.getSupplierName(), item.getLocation());
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("No product found with ID: " + searchID);
        }
    }
}