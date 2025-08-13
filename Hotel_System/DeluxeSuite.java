public class DeluxeSuite extends Room {
    private String deluxeSuiteSize;
    private String deluxeSuiteBeds;
    private String deluxeSuiteView;
    private String deluxeSuiteCapacity;

    public DeluxeSuite() {
        deluxeSuiteSize = "50 m² / 538 ft²";
        deluxeSuiteBeds = "1 King Bed & 1 Queen Bed";
        deluxeSuiteCapacity = "Max 5 Person";
        deluxeSuiteView = "My View :D";
    }

    public String toString() {
        return deluxeSuiteSize + ", " + deluxeSuiteBeds + ", " + deluxeSuiteCapacity + ", " + deluxeSuiteView;
    }
}