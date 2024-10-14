import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class GroupingComparator extends WritableComparator {
    protected GroupingComparator() {
        super(DateTimePair.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        DateTimePair pair1 = (DateTimePair) w1;
        DateTimePair pair2 = (DateTimePair) w2;
        
        // Comparing the natural key
        return pair1.getDate().compareTo(pair2.getDate());
    }
    
}
