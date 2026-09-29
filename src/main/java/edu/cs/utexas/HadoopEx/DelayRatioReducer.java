package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class DelayRatioReducer extends Reducer<Text, TupleWritable, Text, FloatWritable>{
    @Override
    protected void reduce(Text key, Iterable<TupleWritable> values,
            Reducer<Text, TupleWritable, Text, FloatWritable>.Context context)
            throws IOException, InterruptedException {
        
        float total_delay = 0.0f;
        float flights = 0.0f;
        for(TupleWritable value : values){
            total_delay += ((IntWritable) value.get(0)).get();
            flights += ((IntWritable) value.get(1)).get();
        }
        context.write(key, new FloatWritable(total_delay/flights));
    }
}
