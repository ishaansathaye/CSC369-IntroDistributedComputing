package lab1;

import java.io.*;
import java.util.*;

public class Sales {

    public static void createSalesFile(int numSales) {
        // Create a file named sales.csv
        File file = new File("lab1/sales.csv");
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

        // Create 2000 sales records with
        // ID, date, time, storeID, customerID
        // There should be at least 1 sale for every customer and every store
        String outputFile = "lab1/sales.csv";
        try {

            writeSalesCSV(outputFile, numSales);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Generate random time for each sale
    private static String generateRandomTime() {
        Random rand = new Random();
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        int second = rand.nextInt(60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private static void writeSalesCSV(String filePath, int numSales) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("ID,date,time,storeID,customerID\n");
        Random rand = new Random();
        // Get dates from Customer.java
        ArrayList<String[]> namesDates = Customer.readNamesDates("lab1/data/birthdays.csv");
        ArrayList<String> dates = new ArrayList<String>();
        for (String[] nameDate : namesDates) {
            String date = nameDate[2];
            if (date.length() < 12) {
                continue;
            }
            // Add the formatted date (replace '-' with '/')
            dates.add(date.replace("-", "/"));
        }

        // Store IDs go from 1 to 100
        // Customer IDs go from 1 to 1000
        for (int i = 1; i <= numSales; i++) {
            String date = dates.get(rand.nextInt(dates.size()));
            date = date.substring(1, date.length() - 1);
            String time = generateRandomTime();
            String storeID = Integer.toString(rand.nextInt(100) + 1);
            String customerID = Integer.toString(rand.nextInt(1000) + 1);
            writer.write(i + "," + date + "," + time + "," + storeID + "," + customerID + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        // Create a file named sales.csv
        createSalesFile(2000);
    }

}
