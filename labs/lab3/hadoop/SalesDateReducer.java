import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesDateReducer extends Reducer<DateTimePair, Text, Text, Text> {
    @Override
    public void reduce(DateTimePair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String res = "";
        // only add comma after each time id pair but not after the last pair
        int count = 0;
        for (Text value : values) {
            if (count == 0 || count == 1) {
                res += value.toString() + " ";
            } else {
                res += value.toString() + ", ";
                count = -1;
            }
            count++;
        }
        // Remove the trailing comma and space
        res = res.substring(0, res.length() - 2);
        context.write(new Text(key.getDate()), new Text(res));
    }
    
}