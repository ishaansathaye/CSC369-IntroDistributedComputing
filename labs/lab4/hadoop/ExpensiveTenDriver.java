import org.apache.log4j.Logger;
import org.w3c.dom.Text;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class ExpensiveTenDriver extends Configured implements Tool {
    private static final Logger THE_LOGGER = Logger.getLogger(ExpensiveTenDriver.class);

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(ExpensiveTenDriver.class);
        job.setJobName("ExpensiveTenDriver");

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(ExpensiveTenMapper.class);
        job.setReducerClass(ExpensiveTenReducer.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean status = job.waitForCompletion(true);
        THE_LOGGER.info("run(): status="+status);
        return status ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            THE_LOGGER.warn("usage ExpensiveTenDriver <input> <output>");
            System.exit(1);
        }
        THE_LOGGER.info("inputDir="+args[0]);
        THE_LOGGER.info("outputDir="+args[1]);
        int returnStatus = ToolRunner.run(new ExpensiveTenDriver(), args);
        THE_LOGGER.info("returnStatus="+returnStatus);
        System.exit(returnStatus);
    }
    
}
