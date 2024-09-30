package lab1;

import java.io.*;
import java.util.*;

public class Product {

    public static void createProductFile(int numProducts) {
        // Create a file named products.csv
        File file = new File("lab1/products.csv");
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Create 100 product records with
        // ID, description, price
        String outputFile = "lab1/products.csv";
        String productsFile = "lab1/data/laptops.csv";
        try {

            ArrayList<String[]> productNamePrice = readProducts(productsFile);

            writeProductsCSV(outputFile, numProducts);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> readProducts(String filePath) throws IOException {
        ArrayList<String[]> productNamePrice = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(","); // split by comma
            // add 0th and 11th column to the list
            productNamePrice.add(new String[] { values[0], values[11] });
        }
        reader.close();
        return productNamePrice;
    }

    private static void writeProductsCSV(String filePath, int numProducts) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("ID,description,price\n");
        Random rand = new Random();
        ArrayList<String[]> productNamePrice = readProducts("lab1/data/laptops.csv");
        for (int i = 1; i <= numProducts; i++) {
            String[] product = productNamePrice.get(rand.nextInt(productNamePrice.size()));
            product[0] = product[0].replace("\"", "");
            writer.write(i + "," + product[0] + "," + product[1] + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        // Create a file named sales.csv
        createProductFile(100);
    }

}
