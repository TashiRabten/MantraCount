import java.util.List;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        MantraData mantraData = new MantraData();
        boolean continueRunning = true;

        while (continueRunning) {
            // Set target date
            MantraCount.setTargetDate(scanner, mantraData);

            // Set name to count
            System.out.print("Enter the name of the mantra: ");
            mantraData.setNameToCount(scanner.nextLine().trim());

            // Read file with validation
            List<String> lines = null;
            while (lines == null) {
                System.out.print("Enter the file name with path: ");
                String pathName = scanner.nextLine().replace("\"", "").trim();

                try {
                    lines = Files.readAllLines(Paths.get(pathName), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    System.out.println("Error: File not found or cannot be read. Please enter a valid file path.");
                }
            }

            // Clear previous debug lines before processing
            mantraData.resetCounts();

            // Process file
            MantraCount.processFile(lines, mantraData);

            // Display results
            System.out.println("\nResults:");
            System.out.println("Total " + mantraData.getNameToCount() + " count: " + mantraData.getTotalNameCount());
            System.out.println("Total Fiz count: " + mantraData.getTotalFizCount());
            System.out.println("Total Mantras count: " + mantraData.getTotalMantrasCount());
            System.out.println("Sum of mantras: " + mantraData.getTotalFizNumbersSum());

            // Display mismatches if any
            if (!mantraData.getDebugLines().isEmpty()) {
                System.out.println("\nMismatches Found:");
                mantraData.getDebugLines().forEach(System.out::println);
            }

            // Ask if the user wants to continue
            continueRunning = askToContinue(scanner);
        }

        System.out.println("\nGoodbye!");
        try {
            Thread.sleep(2000); // Pause for 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }


        
        scanner.close();
    }

    private static boolean askToContinue(Scanner scanner) {
        while (true) {
            System.out.print("\nDo you want to continue? (yes/no): ");
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("no") || userChoice.equals("n")) {
                return false; // Exit the loop
            } else if (userChoice.equals("yes") || userChoice.equals("y")) {
                return true; // Continue running
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }
}
