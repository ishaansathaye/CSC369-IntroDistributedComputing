// Program that finds the total number of sales for each day.
// Result should be sorted by date
// Write result to a file named "TotalSales.txt"
// Use sales.csv as input file -> in lab1/sales.csv
// Use hash map to store total sales for each day
// csv file has ID,date,time,storeID,customerID

import java.io.*;
import java.util.*;

public class TotalSales {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab1/sales.csv"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab2/TotalSales.txt"));
            String line;
            String[] data;
            HashMap<String, Integer> sales = new HashMap<String, Integer>();
            while ((line = reader.readLine()) != null) {
                data = line.split(",");
                // if date is in the hashmap, add +1 to the value
                if (sales.containsKey(data[1])) {
                    sales.put(data[1], sales.get(data[1]) + 1);
                } else {
                    sales.put(data[1], 1);
                }
            }
            // sort the hashmap by date
            List<String> sortedKeys = new ArrayList<String>(sales.keySet());
            Collections.sort(sortedKeys);
            for (String key : sortedKeys) {
                writer.write(key + " " + sales.get(key) + "\n");
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
             
    } 
}