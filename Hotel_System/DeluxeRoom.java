public class DeluxeRoom extends Room {
    private String deluxeRoomSize;
    private String deluxeRoomBeds;
    private String deluxeRoomView;
    private String deluxeRoomCapacity;

    public DeluxeRoom() {
        deluxeRoomSize = "45 m² / 484 ft²";
        deluxeRoomBeds = "2 Queen Bed";
        deluxeRoomCapacity = "Max 4 Person";
        deluxeRoomView = "My View :D";
    }

    public String toString() {
        return deluxeRoomSize + ", " + deluxeRoomBeds + ", " + deluxeRoomCapacity + ", " + deluxeRoomView;
    }
}