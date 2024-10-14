import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesDateReducer extends Reducer<DateTimePair, Text, Text, Text> {
    @Override
    public void reduce(DateTimePair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String res = "";
        for (Text value : values) {
            res += value.toString() + ", ";
        }
        // Remove the trailing comma and space
        res = res.substring(0, res.length() - 2);
        context.write(new Text(key.getDate()), new Text(res));
    }
    
}
