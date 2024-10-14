package lab3.hadoop;

public class SortingComparator extends WritableComparator {
    protected SortingComparator() {
        super(DateTimePair.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        DateTimePair pair1 = (DateTimePair) w1;
        DateTimePair pair2 = (DateTimePair) w2;
        
        // Comparing the composite key (date, time)
        return pair1.compareTo(pair2);
    }
}
