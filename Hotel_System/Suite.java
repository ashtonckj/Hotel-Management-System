public class Suite extends Room {
    private String suiteSize;
    private String suiteBeds;
    private String suiteView;
    private String suiteCapacity;

    public Suite() {
        suiteSize = "35 m² / 377 ft²";
        suiteBeds = "1 King Bed";
        suiteCapacity = "Max 2 Person";
        suiteView = "My View :D";
    }

    public String toString() {
        return suiteSize + ", " + suiteBeds + ", " + suiteCapacity + ", " + suiteView;
    }
}