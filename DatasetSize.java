import java.io.File;

public class DatasetSize {

    public static void main(String[] args) {
        for(int datasetCount = 1; datasetCount <= 5; datasetCount++) {
            String dataset = "Dataset" + datasetCount;
            File folder = new File(dataset);
            long sizeInBytes = calculateFolderSize(folder);
            double sizeInMB = (double) sizeInBytes / (1024 * 1024); // convert bytes to megabytes
            double sizeInMiB = (double) sizeInMB * 1.04858; // convert bytes to mebibytes

            System.out.println("Size of Dataset" + datasetCount + " in MB: " + sizeInMB);
            System.out.println("Size of Dataset" + datasetCount + " in MiB: " + sizeInMiB);
        }
    }

    public static long calculateFolderSize(File folder) {
        long size = 0;
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += calculateFolderSize(file);
                    }
                }
            }
        } else if (folder.isFile()) {
            size += folder.length();
        }
        return size;
    }
}
