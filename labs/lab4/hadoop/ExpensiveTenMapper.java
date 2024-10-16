import java.io.*;
import java.util.TreeSet;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class ExpensiveTenMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    private static final int TEN = 10;
    private TreeSet<Product> top = new TreeSet<Product>();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();
        String[] tokens = line.split(",");

        double price = Double.parseDouble(tokens[2]);
        top.add(new Product(Integer.parseInt(tokens[0]), tokens[1], price));

        if (top.size() > TEN) {
            top.pollLast(); // same as top.remove(top.last())
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        for (Product p : top) {
            context.write(NullWritable.get(), new Text(p.toString()));
        }
    }
    
}
