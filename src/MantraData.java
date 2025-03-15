import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MantraData {
    private LocalDate targetDate;
    private String nameToCount;
    private long totalNameCount;
    private long totalFizCount;
    private long totalMantrasCount;
    private long totalFizNumbersSum;
    private List<String> debugLines;

    // Constructor
    public MantraData() {
        this.debugLines = new ArrayList<>();
    }
    
    public void resetCounts() {
        this.totalNameCount = 0;
        this.totalFizCount = 0;
        this.totalMantrasCount = 0;
        this.totalFizNumbersSum = 0;
        this.debugLines.clear();  // Reset the debug lines as well
    }

    // Getters and Setters
    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public String getNameToCount() {
        return nameToCount;
    }

    public void setNameToCount(String nameToCount) {
        this.nameToCount = nameToCount.toLowerCase(); // Store in lowercase for case-insensitive matching
    }

    public long getTotalNameCount() {
        return totalNameCount;
    }

    public void setTotalNameCount(long totalNameCount) {
        this.totalNameCount = totalNameCount;
    }

    public long getTotalFizCount() {
        return totalFizCount;
    }

    public void setTotalFizCount(long totalFizCount) {
        this.totalFizCount = totalFizCount;
    }

    public long getTotalMantrasCount() {
        return totalMantrasCount;
    }

    public void setTotalMantrasCount(long totalMantrasCount) {
        this.totalMantrasCount = totalMantrasCount;
    }

    public long getTotalFizNumbersSum() {
        return totalFizNumbersSum;
    }

    public void setTotalFizNumbersSum(long totalFizNumbersSum) {
        this.totalFizNumbersSum = totalFizNumbersSum;
    }

    public List<String> getDebugLines() {
        return debugLines;
    }

    public void addDebugLine(String line) {
        this.debugLines.add(line);
    }
}
