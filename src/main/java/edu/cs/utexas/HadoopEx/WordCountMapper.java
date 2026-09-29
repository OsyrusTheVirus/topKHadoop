package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

	// Create a counter and initialize with 1
	private final IntWritable counter = new IntWritable(1);

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		String[] data = value.toString().split(",");
		context.write(new Text(data[7]), counter);
	}
}