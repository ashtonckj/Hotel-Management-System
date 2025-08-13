public class FamilySuite extends Room {
    private String familySuiteSize;
    private String familySuiteBeds;
    private String familySuiteView;
    private String familySuiteCapacity;

    public FamilySuite() {
        familySuiteSize = "58 m² / 624 ft²";
        familySuiteBeds = "2 Queen Bed & 1 Single Bed";
        familySuiteCapacity = "Max 5 Person";
        familySuiteView = "My View :D";
    }

    public String toString() {
        return familySuiteSize + ", " + familySuiteBeds + ", " + familySuiteCapacity + ", " + familySuiteView;
    }
}