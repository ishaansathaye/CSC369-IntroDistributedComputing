package lab3;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SortSales {

    // function to sort array list of times in ascending order
    public static void sortTimes(ArrayList<String[]> times) {
        Collections.sort(times, new Comparator<String[]>() {
            @Override
            public int compare(String[] a, String[] b) {
                return a[0].compareTo(b[0]);
            }
        });
    }

    public static void main(String[] args) throws ParseException {
        // hashmap of key is date, value is list of (time, id) pairs
        HashMap<String, ArrayList<String[]>> sales = new HashMap<>();
        //contains id, date, time, pID, cId
        
        try {
            Scanner sc = new Scanner(new File("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab1/sales.csv"));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                String id = line[0];
                String date = line[1];
                String time = line[2];
                if (!sales.containsKey(date)) {
                    sales.put(date, new ArrayList<>());
                }
                sales.get(date).add(new String[]{time, id});
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        // for each day, sort the times in ascending order
        for (String date : sales.keySet()) {
            sortTimes(sales.get(date));
        }

        // sort the dates in ascending order
        ArrayList<Date> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for (String date : sales.keySet()) {
            dates.add(sdf.parse(date));
        }
        Collections.sort(dates);

        // write the sorted sales to a file
        try {
            PrintWriter pw = new PrintWriter(new File("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab3/sortedSales.txt"));
            for (Date date : dates) {
                String dateStr = sdf.format(date);
                pw.print(dateStr + " ");
                // comma after each time but not after the last time
                for (int i = 0; i < sales.get(dateStr).size(); i++) {
                    pw.print(sales.get(dateStr).get(i)[0]);
                    pw.print(" ");
                    pw.print(sales.get(dateStr).get(i)[1]);
                    if (i != sales.get(dateStr).size() - 1) {
                        pw.print(", ");
                    }
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }
    
}
