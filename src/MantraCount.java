import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MantraCount {

    // Method to get the target date
    public static void setTargetDate(Scanner scanner, MantraData mantraData) {
        LocalDate targetDate = null;
        while (targetDate == null) {
            System.out.print("Enter the date in MM/DD/YYYY format: ");
            String dateInput = scanner.nextLine();
            try {
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
                targetDate = LocalDate.parse(dateInput, formatter1);
            } catch (DateTimeParseException e1) {
                try {
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yy");
                    targetDate = LocalDate.parse(dateInput, formatter2);
                } catch (DateTimeParseException e2) {
                    System.out.println("Invalid date format. Please use MM/DD/YYYY or M/d/yy.");
                }
            }
        }
        mantraData.setTargetDate(targetDate); // Set target date in MantraData
    }

    // Method to process the file
    public static void processFile(List<String> lines, MantraData mantraData) {
        boolean[] dateReached = {false};

        lines.forEach(line -> {
            if (line.contains("[") && line.contains(",")) {
                String datePart = line.substring(line.indexOf("[") + 1, line.indexOf(","));
                try {
                    LocalDate lineDate = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("M/d/yy"));

                    if (!dateReached[0] && !lineDate.isBefore(mantraData.getTargetDate())) {
                        dateReached[0] = true;
                    }

                    if (dateReached[0]) {
                        long nameCount = StringUtils.countMatches(line.toLowerCase(), mantraData.getNameToCount());
                        long fizCount = StringUtils.countMatches(line, "Fiz");
                        long mantrasCount = StringUtils.countMatches(line, "mantras");

                        mantraData.setTotalNameCount(mantraData.getTotalNameCount() + nameCount);
                        mantraData.setTotalFizCount(mantraData.getTotalFizCount() + fizCount);
                        mantraData.setTotalMantrasCount(mantraData.getTotalMantrasCount() + mantrasCount);

                        String to = StringUtils.substringBetween(line, "Fiz ", " ");
                        if (to != null) {
                            try {
                                mantraData.setTotalFizNumbersSum(mantraData.getTotalFizNumbersSum() + Integer.parseInt(to));
                            } catch (NumberFormatException e) {
                                // Ignore invalid numbers
                            }
                        }

                        if ((nameCount > 0 || fizCount > 0 || mantrasCount > 0) && (nameCount != fizCount || nameCount != mantrasCount || fizCount != mantrasCount)) {
                            mantraData.addDebugLine("Mismatch in line: \"" + line + "\" - " +
                                    capitalizeFirstLetter(mantraData.getNameToCount()) + " count: " + nameCount +
                                    ", Fiz count: " + fizCount +
                                    ", Mantras count: " + mantrasCount);
                        }
                    }
                } catch (DateTimeParseException e) {
                    // Ignore invalid date lines
                }
            }
        });
    }

    // Helper to capitalize the first letter
    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
