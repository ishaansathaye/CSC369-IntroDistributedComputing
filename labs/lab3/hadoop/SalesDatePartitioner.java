import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SalesDatePartitioner extends Partitioner<DateTimePair, Text> {
    @Override
    public int getPartition(DateTimePair pair, Text time, int numberOfPartitions) {
        return Math.abs(pair.getDate().hashCode() % numberOfPartitions);
    }
}
