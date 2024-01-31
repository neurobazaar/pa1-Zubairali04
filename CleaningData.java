import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CleaningData {

    private static String cleanText(String input) {
        input = input.replace("\r", "")
                     .replaceAll("[\t\n\f\r]+", "\n")
                     .replaceAll("[\t\n\f\r\\-_:*\".]+", "");
        return input;
    }

    private static void processFilesInDirectory(String datasetPath, String cleanedDatasetPath) {
        for (int folderCount = 1; folderCount <= 16; folderCount++) {
            String inputFolderPath = datasetPath + "folder" + folderCount + "/";
            String outputFolderPath = cleanedDatasetPath + "folder" + folderCount + "/";

            File inputFolder = new File(inputFolderPath);
            File outputFolder = new File(outputFolderPath);

            if (!inputFolder.isDirectory()) {
                System.err.println("Input directory does not exist: " + inputFolderPath);
                continue;
            }

            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            File[] files = inputFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        try {
                            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                            String cleanedContent = cleanText(content);
                            String outputFilePath = outputFolderPath + file.getName();
                            Files.write(Paths.get(outputFilePath), cleanedContent.getBytes(StandardCharsets.UTF_8));
                            System.out.println("Cleaned file: " + outputFilePath);
                        } catch (IOException e) {
                            System.err.println("Error processing file: " + file.getAbsolutePath());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String datasetBasePath = "Dataset";
        String cleanedDatasetBasePath = "CleanedDataset";

        long startTime = System.currentTimeMillis();

        for (int datasetCount = 1; datasetCount <= 5; datasetCount++) {
            String datasetPath = datasetBasePath + datasetCount + "/";
            String cleanedDatasetPath = cleanedDatasetBasePath + datasetCount + "/";
            processFilesInDirectory(datasetPath, cleanedDatasetPath);
        }

        long endTime = System.currentTimeMillis();
        double elapsedTimeSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Total elapsed time: " + elapsedTimeSeconds + " seconds");
    }
}
