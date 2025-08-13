public class FamilyRoom extends Room {
    private String familyRoomSize;
    private String familyRoomBeds;
    private String familyRoomView;
    private String familyRoomCapacity;

    public FamilyRoom() {
        familyRoomSize = "42 m² / 452 ft²";
        familyRoomBeds = "1 King Bed & 2 Single Bed";
        familyRoomCapacity = "Max 4 Person";
        familyRoomView = "My View :D";
    }

    public String toString() {
        return familyRoomSize + ", " + familyRoomBeds + ", " + familyRoomCapacity + ", " + familyRoomView;
    }
}