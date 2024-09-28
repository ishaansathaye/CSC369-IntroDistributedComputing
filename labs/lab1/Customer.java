package lab1;

import java.io.*;
import java.util.*;

public class Customer {

    // createCustomerFile: create a file named customer.csv
    public static void createCustomerFile(Integer numCustomers) {
        // Create a file named customer.txt
        File file = new File("lab1/customer.csv");
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

        String namesDatesFile = "lab1/data/birthdays.csv";
        String addressesFile = "lab1/data/addresses.csv";
        String outputFile = "lab1/customer.csv";

        try {
            ArrayList<String[]> namesDates = readNamesDates(namesDatesFile);
            ArrayList<String[]> addresses = Store.readAddresses(addressesFile);

            writeCustomerCSV(outputFile, namesDates, addresses, numCustomers);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // readNamesDates: read lastname, firstname, birthDate from birthdays.csv
    public static ArrayList<String[]> readNamesDates(String filePath) throws IOException {
        ArrayList<String[]> namesDates = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            namesDates.add(new String[] {values[0].trim(), values[1].trim(), values[3].trim()});
        }
        reader.close();
        return namesDates;
    }

    // writeCustomerCSV: write customer.csv with ID, name, birthDate, address, city, ZIP, state, phoneNumber
    private static void writeCustomerCSV(String filePath, ArrayList<String[]> namesDates, ArrayList<String[]> addresses, int numCustomers) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write("ID,name,birthDate,address,city,zip,state,phoneNumber\n");
        Random rand = new Random();
        // Unique phone numbers
        Set<String> phoneNumbers = new HashSet<String>();
        for (int i = 0; i < numCustomers; i++) {
            String[] nameDate = namesDates.get(rand.nextInt(namesDates.size()));
            String[] address = addresses.get(rand.nextInt(addresses.size()));
            String phoneNumber = Store.generateUniquePhoneNumber(phoneNumbers);
            String name = nameDate[1].replace("\"", "") + " " + nameDate[0].replace("\"", "");
            String birthDate = nameDate[2].replace("-", "/");
            writer.write((i + 1) + "," + name + "," 
            + birthDate.replace("\"", "") + "," + address[0].replace("\"", "") + ","
            + address[1].replace("\"", "") + "," + address[2].replace("\"", "") + ","
            + address[3].replace("\"", "") + "," + phoneNumber + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        // Create a file named customer.csv
        createCustomerFile(1000);
    }
    
}
