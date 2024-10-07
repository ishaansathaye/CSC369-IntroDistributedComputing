import java.io.*;

import javax.naming.Context;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer.*;
import org.w3c.dom.Text;

// Takes date and count of 1 as input and outputs the date with the total count

public class TotalCountReducer 
  extends Reducer<Text, IntWritable, Text, IntWritable> {
   
   @Override
   public void reduce(Iterable<Text> date, Iterable<IntWritable> count, Context context) 
        throws IOException, InterruptedException {
        double sum=0;        
        for(IntWritable c: count){
          sum += c.get();          
        } 
        context.write(date, new IntWritable(sum));
    }
}
