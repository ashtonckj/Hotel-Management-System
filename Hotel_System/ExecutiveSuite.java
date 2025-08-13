public class ExecutiveSuite extends Room {
    private String executiveSuiteSize;
    private String executiveSuiteBeds;
    private String executiveSuiteView;
    private String executiveSuiteCapacity;

    public ExecutiveSuite() {
        executiveSuiteSize = "40 m² / 431 ft²";
        executiveSuiteBeds = "1 Queen Bed & 1 Single Bed";
        executiveSuiteCapacity = "Max 3 Person";
        executiveSuiteView = "My View :D";
    }

    public String toString() {
        return executiveSuiteSize + ", " + executiveSuiteBeds + ", " + executiveSuiteCapacity + ", " + executiveSuiteView;
    }
}