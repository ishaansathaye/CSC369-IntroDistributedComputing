package lab3;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SortSales {

    // function to sort array list of times in ascending order
    public static void sortTimes(ArrayList<String> times) {
        Collections.sort(times, new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                    return t1.compareTo(t2);
                }
            });
    }

    public static void main(String[] args) throws ParseException {
        HashMap<String, ArrayList<String>> sales = new HashMap<>();
        //contains id, date, time, pID, cId
        try {
            Scanner sc = new Scanner(new File("/Users/ishaansathaye/Git/CalPolyCourses/CSC369-IntroDistributedComputing/labs/lab1/sales.csv"));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (!sales.containsKey(line[1])) {
                    sales.put(line[1], new ArrayList<String>());
                }
                sales.get(line[1]).add(line[2]);
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
                for (String time : sales.get(dateStr)) {
                    pw.print(time + " ");
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }
    
}
