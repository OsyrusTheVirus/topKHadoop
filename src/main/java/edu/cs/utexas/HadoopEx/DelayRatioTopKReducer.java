package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DelayRatioTopKReducer extends Reducer<Text, FloatWritable, Text, FloatWritable>{
    private final static int TOP_K = 3;
    private PriorityQueue<FlightAndDelayRatio> pq = new PriorityQueue<>(TOP_K);

    @Override
    protected void reduce(Text key, Iterable<FloatWritable> values,
            Reducer<Text, FloatWritable, Text, FloatWritable>.Context context)
            throws IOException, InterruptedException {
        for (FloatWritable value : values) pq.add(new FlightAndDelayRatio(key, value));
        while (pq.size() > TOP_K) pq.poll();
    }

    @Override
    protected void cleanup(Reducer<Text, FloatWritable, Text, FloatWritable>.Context context)
            throws IOException, InterruptedException {
        while(!pq.isEmpty()) {
            FlightAndDelayRatio flight_and_delay_ratio = pq.poll();
            context.write(flight_and_delay_ratio.getFlight(), flight_and_delay_ratio.getTotalDelay());
        }
    }
}
