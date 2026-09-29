package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;

public class DelayRatioAndCount implements Comparable<DelayRatioAndCount> {

    private final Text flight;
    private final FloatWritable total_delay;

    public DelayRatioAndCount (Text flight, FloatWritable total_delay) {
        this.flight = flight;
        this.total_delay = total_delay;
    }

    public Text getFlight() {
        return this.flight;
    }

    public FloatWritable getTotalDelay() {
        return this.total_delay;
    }

    public int compareTo(DelayRatioAndCount other) {
        float diff = this.total_delay.get() - other.total_delay.get();
        if(diff > 0){
            return 1;
        } else if (diff < 0) {
            return -1;
        }
        return 0;
    }
    
}
