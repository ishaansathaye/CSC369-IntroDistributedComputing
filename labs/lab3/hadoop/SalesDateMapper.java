import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesDateMapper extends Mapper<LongWritable, Text, DateTimePair, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] tokens = line.split(",");
        String id = tokens[0].trim();
        String date = tokens[1].trim();
        String time = tokens[2].trim();
        context.write(new DateTimePair(date, time), new Text(time + " " + id));
    }
}
