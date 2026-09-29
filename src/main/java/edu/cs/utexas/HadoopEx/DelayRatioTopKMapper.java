package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DelayRatioTopKMapper extends Mapper<Text, Text, Text, FloatWritable>{
    private final static int TOP_K = 3;
    private PriorityQueue<DelayRatioAndCount> pq = new PriorityQueue<>(TOP_K);

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        float total_delay = Float.parseFloat(value.toString());
        pq.add(new DelayRatioAndCount(new Text(key), new FloatWritable(total_delay)));
        if(pq.size() > TOP_K) pq.poll();
    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        while(!pq.isEmpty()) {
            DelayRatioAndCount flight_and_delay_ratio = pq.poll();
            context.write(flight_and_delay_ratio.getFlight(), flight_and_delay_ratio.getTotalDelay());
        }
    }
}
