package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DelayRatioReducer extends Reducer<Text, DelayAndCountWritable, Text, FloatWritable>{
    public void reduce(Text key, Iterable<DelayAndCountWritable> values, Context context) throws IOException, InterruptedException {
        float total_delay = 0.0f;
        float flights = 0.0f;
        for(DelayAndCountWritable value : values){
            total_delay += value.getDelay().get();
            flights += value.getCount().get();
        }
        context.write(key, new FloatWritable(total_delay/flights));
    }
}
