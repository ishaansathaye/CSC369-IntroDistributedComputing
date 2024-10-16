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
            String text = null;
            // Create a map to store the products
            Map<Double, String> products = new HashMap<Double, String>();
            while ((text = reader.readLine()) != null) {
                String[] parts = text.split(",");
                // Add the product to the map
                products.put(Double.parseDouble(parts[2]), parts[0] + ", " + parts[1]); // key is price, value is productID, productName
            }
            // Sort the products by price
            TreeMap<Double, String> sortedProducts = new TreeMap<Double, String>(Collections.reverseOrder());
            sortedProducts.putAll(products);
            // Write the 10 most expensive products to a file
            File outputFile = new File("expensive10.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            int count = 0;
            for (Map.Entry<Double, String> entry : sortedProducts.entrySet()) {
                if (count < 10) {
                    writer.write(entry.getValue() + ", " + entry.getKey() + "\n");
                    count++;
                } else {
                    break;
                }
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
