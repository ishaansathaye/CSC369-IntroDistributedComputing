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
            ArrayList<String[]> storeIDs = readStoreIDs("lab1/store.csv");
            ArrayList<String[]> customerIDs = readCustomerIDs("lab1/customer.csv");

            writeSalesCSV(outputFile, storeIDs, customerIDs, numSales);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> readStoreIDs(String filePath) throws IOException {
        ArrayList<String[]> storeIDs = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            storeIDs.add(new String[] {values[0].trim()});
        }
        reader.close();
        return storeIDs;
    }

    private static ArrayList<String[]> readCustomerIDs(String filePath) throws IOException {
        ArrayList<String[]> customerIDs = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            customerIDs.add(new String[] {values[0].trim()});
        }
        reader.close();
        return customerIDs;
    }

    // Generate random time for each sale
    private static String generateRandomTime() {
        Random rand = new Random();
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        int second = rand.nextInt(60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private static void writeSalesCSV(String filePath, ArrayList<String[]> storeIDs, ArrayList<String[]> customerIDs, int numSales) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("ID,date,time,storeID,customerID\n");
        Random rand = new Random();
        // Get dates from Customer.java
        ArrayList<String[]> namesDates = Customer.readNamesDates("lab1/data/birthdays.csv");
        ArrayList<String> dates = new ArrayList<String>();
        for (String[] nameDate : namesDates) {
            dates.add(nameDate[2].replace("-", "/"));
        }
        
        int storePointer = 0;
        int customerPointer = 0;
        

        
    }
        

    public static void main(String[] args) {
        // Create a file named sales.csv
        createSalesFile(2000);
    }
    
}
