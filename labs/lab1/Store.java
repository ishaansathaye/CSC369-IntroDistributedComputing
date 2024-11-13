package lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.io.FileWriter;

public class Store {

    // createStoreFile: create a file named store.csv
    public static void createStoreFile(Integer numStores) {
        // Create a file named store.txt
        File file = new File("lab1/store.csv");
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

        String addressesFile = "lab1/data/addresses.csv";
        String storeNamesFile = "lab1/data/storeNames.csv";
        String outputFile = "lab1/store.csv";

        try {
            ArrayList<String> storeNames = readStoreNames(storeNamesFile);
            ArrayList<String[]> addresses = readAddresses(addressesFile);

            writeStoreCSV(outputFile, addresses, storeNames, numStores);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // readStoreNames: read only "name" column from storeNames.csv
    private static ArrayList<String> readStoreNames(String filePath) throws IOException {
        ArrayList<String> storeNames = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";"); // split by semicolon
            storeNames.add(values[1].trim());
        }
        reader.close();
        return storeNames;
    }

    // readAddresses: read address, city, state, zip from addresses.csv
    public static ArrayList<String[]> readAddresses(String filePath) throws IOException {
        ArrayList<String[]> addresses = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine(); // skip the header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            addresses.add(values);
        }
        reader.close();
        return addresses;
    }

    // generateUniquePhoneNumber: generate a unique phone number
    public static String generateUniquePhoneNumber(Set<String> phoneNumbers) {
        Random random = new Random();
        String phoneNumber = "";
        do {
            phoneNumber = String.format("%010d", random.nextInt(1000000000));
        } while (phoneNumbers.contains(phoneNumber));
        phoneNumbers.add(phoneNumber);
        return phoneNumber;
    }

    // writeStoreCSV: write to store.csv
    private static void writeStoreCSV(String filePath, ArrayList<String[]> addresses, ArrayList<String> storeNames, int numEntries) throws IOException {
        FileWriter csvWriter = new FileWriter(filePath);
        // Do not write header
        // csvWriter.append("ID,storeName,address,city,ZIP,state,phoneNumber\n");

        Random random = new Random();
        // Unique phone numbers
        Set<String> phoneNumbers = new HashSet<String>();

        for (int i = 1; i <= numEntries; i++) {
            String storeName = storeNames.get(random.nextInt(storeNames.size()));
            // replace all commas in the store name with empty string
            storeName = storeName.replace(",", "");
            String[] addressData = addresses.get(random.nextInt(addresses.size()));

            String phoneNumber = generateUniquePhoneNumber(phoneNumbers);

            // Write data to file in csv format
            csvWriter.append(String.valueOf(i))
                .append(",")
                .append(storeName.replace("\"", ""))
                .append(",")
                .append(addressData[0].replace("\"", ""))
                .append(",")
                .append(addressData[1].replace("\"", ""))
                .append(",")
                .append(addressData[2].replace("\"", ""))
                .append(",")
                .append(addressData[3].replace("\"", ""))
                .append(",")
                .append(phoneNumber)
                .append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }


    public static void main(String[] args) {
        // Create a file named store.csv
        createStoreFile(100);
    }
    
}