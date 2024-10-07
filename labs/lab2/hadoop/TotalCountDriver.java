import org.apache.log4j.Logger;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class TotalCountDriver extends Configured implements Tool {
   private static final Logger THE_LOGGER = Logger.getLogger(TotalCountDriver.class);

   public int run(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(TotalCountDriver.class);
        job.setJobName("TotalCountDriver");
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setMapperClass(TotalCountMapper.class);
        job.setReducerClass(TotalCountReducer.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean status = job.waitForCompletion(true);
        THE_LOGGER.info("run(): status="+status);
        return status ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            THE_LOGGER.warn("usage TotalCountDriver <input> <output>");
            System.exit(1);
        }
        THE_LOGGER.info("inputDir="+args[0]);
        THE_LOGGER.info("outputDir="+args[1]);
        int returnStatus = ToolRunner.run(new TotalCountDriver(), args);
        THE_LOGGER.info("returnStatus="+returnStatus);
        System.exit(returnStatus);
    }
}