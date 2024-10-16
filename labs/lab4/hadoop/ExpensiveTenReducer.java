import java.io.*;
import java.util.TreeSet;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class ExpensiveTenReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
    private static final int TEN = 10;
    private TreeSet<Product> top = new TreeSet<Product>();

    public void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            String valueAsString = value.toString().trim();
            String[] tokens = valueAsString.split(",");
            double price = Double.parseDouble(tokens[2]);
            top.add(new Product(Integer.parseInt(tokens[0]), tokens[1], price));
            
            // keep only the top 10 most expensive products
            if (top.size() > TEN) {
                top.pollLast(); // same as top.remove(top.last())
            }
        }

        for (Product p : top) {
            context.write(NullWritable.get(), new Text(p.toString()));
        }
    }
}
