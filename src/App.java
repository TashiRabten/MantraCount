import java.util.List;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;


public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        MantraData mantraData = new MantraData();

        // Set target date
        MantraCount.setTargetDate(scanner, mantraData);

        // Set name to count
        System.out.print("Enter the name of the mantra: ");
        mantraData.setNameToCount(scanner.nextLine());
        

        // Read file
        System.out.print("Enter the file name with path: ");
        String pathName = scanner.nextLine().replace("\"", "").trim();

        List<String> lines = Files.readAllLines(Paths.get(pathName), StandardCharsets.UTF_8);

        // Process file
        MantraCount.processFile(lines, mantraData);

        // Display results
        System.out.println("Total " + mantraData.getNameToCount() + " count: " + mantraData.getTotalNameCount());
        System.out.println("Total Fiz count: " + mantraData.getTotalFizCount());
        System.out.println("Total Mantras count: " + mantraData.getTotalMantrasCount());
        System.out.println("Sum of mantras: " + mantraData.getTotalFizNumbersSum());
        if (!mantraData.getDebugLines().isEmpty()) {
            mantraData.getDebugLines().forEach(System.out::println);
        }

        scanner.close();
    }
}
