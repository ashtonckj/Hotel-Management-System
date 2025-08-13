public class SingleRoom extends Room {
    private String singleRoomSize;
    private String singleRoomBeds;
    private String singleRoomView;
    private String singleRoomCapacity;

    public SingleRoom() {
        singleRoomSize = "25 m² / 269 ft²";
        singleRoomBeds = "1 Single Bed";
        singleRoomCapacity = "Max 1 Person";
        singleRoomView = "My View :D";
    }

    public String toString() {
        return singleRoomSize + ", " + singleRoomBeds + ", " + singleRoomCapacity + ", " + singleRoomView;
    }
}