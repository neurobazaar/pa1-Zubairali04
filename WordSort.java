import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class WordSort {

    public static void main(String[] args) {
        String inputBasePath = "CountedDataset";
        String outputBasePath = "SortedDataset";

        try {
            for (int dataSetCounter = 1; dataSetCounter <= 5; dataSetCounter++) {
                for (int folderCounter = 1; folderCounter <= 16; folderCounter++) {
                    String inputFolderPath = String.format("%s%d/folder%d/", inputBasePath, dataSetCounter, folderCounter);
                    String outputFolderPath = String.format("%s%d/folder%d/", outputBasePath, dataSetCounter, folderCounter);

                    processFiles(inputFolderPath, outputFolderPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFiles(String inputFolderPath, String outputFolderPath) throws IOException {
        File inputFolder = new File(inputFolderPath);
        File outputFolder = new File(outputFolderPath);

        // Create the output folder if it doesn't exist
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        File[] files = inputFolder.listFiles();
        if (files == null) {
            System.out.println("Input folder is empty or does not exist.");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                processFile(file, outputFolderPath);
            }
        }
    }

    private static void processFile(File inputFile, String outputFolderPath) {
        String outputFilePath = outputFolderPath + inputFile.getName();
        Map<String, Integer> wordCountMap = new HashMap<>();
        extractWordCounts(inputFile, wordCountMap);

        try {
            TreeMap<String, Integer> sortedWordCountMap = new TreeMap<>(wordCountMap);

            PrintWriter writer = new PrintWriter (outputFilePath, StandardCharsets.UTF_8);
            for (Map.Entry<String, Integer> entry : sortedWordCountMap.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
            writer.close();
            System.out.println("Word counts sorted and saved to '" + outputFilePath + "'");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void extractWordCounts(File file, Map<String, Integer> wordCountMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    wordCountMap.put(word, count);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
