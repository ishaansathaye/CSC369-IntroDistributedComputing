package lab1;

import java.io.*;
import java.util.*;

public class LineItem {

    public static void createLineItemFile(int numLineItems) {
        // Create a file named sales.csv
        File file = new File("lab1/lineItems.csv");
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

        // Create 4000 line item records with
        // ID, saleID, productID, quantity
        String outputFile = "lab1/lineItems.csv";
        try {
            writeLineItemsCSV(outputFile, numLineItems);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeLineItemsCSV(String filePath, int numLineItems) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("ID,saleID,productID,quantity\n");
        Random rand = new Random();
        for (int i = 1; i <= numLineItems; i++) {
            writer.write(i + "," + rand.nextInt(2000) + "," + rand.nextInt(100) + "," + (rand.nextInt(10) + 1) + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        // Create a file named sales.csv
        createLineItemFile(4000);
    }

}
