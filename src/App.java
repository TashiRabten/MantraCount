import org.apache.commons.lang3.StringUtils;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.nio.charset.StandardCharsets; // Add this import for UTF-8 charset

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

        while (true) {
            try {
                // Ask for the name to count
                System.out.print("Enter the name of the mantra: ");
                String nameToCount = scanner.nextLine().toLowerCase(); // Store in lower case for case-insensitive comparison
                
                // Ask for the date in MM/DD/YYYY format
                LocalDate targetDate = null;
                while (targetDate == null) {
                    System.out.print("Enter the date in MM/DD/YYYY format: ");
                    String dateInput = scanner.nextLine();
                    try {
                        // Try parsing in MM/DD/YYYY format
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
                        targetDate = LocalDate.parse(dateInput, formatter1);
                    } catch (DateTimeParseException e1) {
                        try {
                            // Try parsing in M/d/yy format
                            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yy");
                            targetDate = LocalDate.parse(dateInput, formatter2);
                        } catch (DateTimeParseException e2) {
                            System.out.println("Invalid date format. Please use MM/DD/YYYY or M/d/yy.");
                        }
                    }
                }

                // Ask for the file name, allowing quotes
                System.out.print("Enter the file name with path: ");
                String fileName = scanner.nextLine().replace("\"", "").trim(); // Remove quotes and trim whitespace
                
                // Read all lines from the specified file
                List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

                // Compute the total counts for the specified name across all lines after reaching the date
                final LocalDate finalTargetDate = targetDate;
                long[] totals = {0, 0, 0, 0}; // [totalNameCount, totalFizCount, totalMantrasCount, totalFizNumbersSum]
                
                boolean[] dateReached = {false}; // To track if we reached the date
                List<String> debugLines = new ArrayList<>(); // To collect debug information

                lines.forEach(line -> {
                    // Extract the date from the line (between "[" and ",")
                    if (line.contains("[") && line.contains(",")) {
                        String datePart = line.substring(line.indexOf("[") + 1, line.indexOf(","));
                        try {
                            // Attempt to parse the date from the line
                            LocalDate lineDate = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("M/d/yy"));

                            // Check if we reached or passed the target date
                            if (!dateReached[0] && !lineDate.isBefore(finalTargetDate)) {
                                dateReached[0] = true; // We have reached the date
                                System.out.println("Reached target date: " + finalTargetDate);
                            }

                            // Only count occurrences if the target date has been reached
                            if (dateReached[0]) {
                                // Count occurrences of the name, ignoring case
                                long nameCount = StringUtils.countMatches(line.toLowerCase(), nameToCount);
                                long fizCount = StringUtils.countMatches(line, "Fiz");
                                long mantrasCount = StringUtils.countMatches(line, "mantras");

                                totals[0] += nameCount;
                                totals[1] += fizCount;
                                totals[2] += mantrasCount;

                                // Extract number found after "Fiz "
                                String to = StringUtils.substringBetween(line, "Fiz ", " ");
                                if (to != null) {
                                    try {
                                        totals[3] += Integer.parseInt(to); // Add the number to the sum
                                    } catch (NumberFormatException e) {
                                        // Ignore invalid number format
                                    }
                                }

                                // Check for mismatches: log only if all counts are not equal
                                if (nameCount > 0 || fizCount > 0 || mantrasCount > 0) { // Ensure at least one is counted
                                    if (nameCount != fizCount || nameCount != mantrasCount || fizCount != mantrasCount) {
                                        debugLines.add("Mismatch in line: \"" + line + "\" - " +
                                                nameToCount + " count: " + nameCount +
                                                ", Fiz count: " + fizCount +
                                                ", Mantras count: " + mantrasCount);
                                    }
                                }
                            }
                        } catch (DateTimeParseException e) {
                            // Ignore lines that don't contain a valid date format
                        }
                    }
                });

                // Print final counts
                System.out.println("Total " + nameToCount + " count: " + totals[0]);
                System.out.println("Total Fiz count: " + totals[1]);
                System.out.println("Total mantras count: " + totals[2]);
                System.out.println("Sum of mantras: " + totals[3]);

                // Print debug information if any mismatches were found
                if (!debugLines.isEmpty()) {
                    debugLines.forEach(System.out::println);
                }

            } catch (Exception e) {
                // Handle any exception such as file not found or read error
                System.out.println("An error occurred: " + e.getMessage());
            }

            // Ask the user if they want to run the program again or exit
            System.out.print("Do you want to run the program again? (yes/no): ");
            String answer = scanner.nextLine();

            if (!answer.equalsIgnoreCase("yes")) {
                break; // Exit the loop if the user does not want to continue
            }
        }

        
        scanner.close();
        System.out.println("Program exited.");
    }
}


