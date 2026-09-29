package edu.cs.utexas.HadoopEx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class DelayAndCountWritable implements Writable {

    private final IntWritable delay;
    private final IntWritable count;

    public DelayAndCountWritable () {
        this.delay = new IntWritable(0);
        this.count = new IntWritable(1);
    }

    public DelayAndCountWritable (int delay, int count) {
        this.delay = new IntWritable(delay);
        this.count = new IntWritable(count);
    }

    public IntWritable getDelay() {
        return this.delay;
    }

    public IntWritable getCount() {
        return this.count;
    }


    @Override
    public void readFields(DataInput in) throws IOException {
        delay.readFields(in);
        count.readFields(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        delay.write(out);
        count.write(out);
    }
    
}
