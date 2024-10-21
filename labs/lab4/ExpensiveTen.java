package lab4;

import java.io.*;
import java.util.*;

public class ExpensiveTen {
    public static void main(String[] args) {
        // Read the file
        File file = new File("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab1/products.csv");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            List<String> products = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                products.add(line);
            }
            // Sort the products by price
            Collections.sort(products, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String[] parts1 = o1.split(",");
                    String[] parts2 = o2.split(",");
                    return Double.compare(Double.parseDouble(parts2[2]), Double.parseDouble(parts1[2]));
                }
            });
            // Write the 10 most expensive products to a file
            File expensiveTen = new File("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab4/expensiveTen.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(expensiveTen));
            for (int i = 0; i < 10; i++) {
                writer.write(products.get(i));
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
