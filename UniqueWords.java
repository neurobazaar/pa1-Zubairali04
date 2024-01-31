import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UniqueWords {
   public UniqueWords() {
   }

   public static void main(String[] var0) {
      String var1 = "CleanedDataset";
      String var2 = "CountedDataset";
      processDatasets(var1, var2);
   }

   private static void processDatasets(String var0, String var1) {
      for(int var2 = 1; var2 <= 5; ++var2) {
         for(int var3 = 1; var3 <= 16; ++var3) {
            String var4 = var0 + var2 + "/folder" + var3 + "/";
            String var5 = var1 + var2 + "/folder" + var3 + "/";
            processFiles(var4, var5);
         }
      }

   }

   private static void processFiles(String var0, String var1) {
      File var2 = new File(var0);
      File var3 = new File(var1);
      if (!var3.exists()) {
         var3.mkdirs();
      }

      File[] var4 = var2.listFiles();
      if (var4 == null) {
         System.out.println("Input folder is empty or does not exist.");
      } else {
         File[] var5 = var4;
         int var6 = var4.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File var8 = var5[var7];
            if (var8.isFile()) {
               processFile(var8, var1);
            }
         }

      }
   }

   private static void processFile(File var0, String var1) {
      String var2 = var1 + var0.getName();
      HashMap var3 = new HashMap();
      extractUniqueWords(var0, var3);

      try {
         PrintWriter var4 = new PrintWriter(var2, StandardCharsets.UTF_8);

         try {
            Iterator var5 = var3.entrySet().iterator();

            while(true) {
               String var10001;
               if (!var5.hasNext()) {
                  PrintStream var10000 = System.out;
                  var10001 = var0.getName();
                  var10000.println("Unique words counted in '" + var10001 + "' and saved to '" + var2 + "'");
                  break;
               }

               Map.Entry var6 = (Map.Entry)var5.next();
               var10001 = (String)var6.getKey();
               var4.println(var10001 + ": " + var6.getValue());
            }
         } catch (Throwable var8) {
            try {
               var4.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         var4.close();
      } catch (IOException var9) {
         System.out.println("Error writing to file: " + var9.getMessage());
      }

   }

   private static void extractUniqueWords(File var0, Map<String, Integer> var1) {
      try {
         BufferedReader var2 = new BufferedReader(new FileReader(var0));

         String var3;
         try {
            while((var3 = var2.readLine()) != null) {
               String[] var4 = var3.split("[^\\w-]+");
               String[] var5 = var4;
               int var6 = var4.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String var8 = var5[var7];
                  var1.put(var8, (Integer)var1.getOrDefault(var8, 0) + 1);
               }
            }
         } catch (Throwable var10) {
            try {
               var2.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }

            throw var10;
         }

         var2.close();
      } catch (IOException var11) {
         System.out.println("Error reading file: " + var11.getMessage());
      }

   }
}