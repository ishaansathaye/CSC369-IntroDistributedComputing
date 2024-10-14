import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class DateTimePair implements WritableComparable<DateTimePair> {
    private final Text date = new Text();
    private final Text time = new Text();

    public DateTimePair() {}

    public DateTimePair(String date, String time) {
        this.date.set(date);
        this.time.set(time);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        date.write(out);
        time.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        date.readFields(in);
        time.readFields(in);
    }

    @Override
    public int compareTo(DateTimePair other) {
        int res = date.compareTo(other.date);
        if (res == 0){
            return time.compareTo(other.time);
        }
        return res;
    }

    public Text getDate() {
        return date;
    }

    public Text getTime() {
        return time;
    }

}