package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountTopKDriver extends Configured implements Tool {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new WordCountTopKDriver(), args);
		System.exit(res);
	}

	/**
	 * 
	 */
	public int run(String args[]) {
		try {
			Configuration conf = new Configuration();
			
			// --------- task 1 ----------

			Job job = new Job(conf, "WordCount");
			job.setJarByClass(WordCountTopKDriver.class);

			// specify a Mapper
			job.setMapperClass(WordCountMapper.class);

			// specify a Reducer
			job.setReducerClass(WordCountReducer.class);

			// specify output types
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);

			// specify input and output directories
			FileInputFormat.addInputPath(job, new Path(args[0]));
			job.setInputFormatClass(TextInputFormat.class);

			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			job.setOutputFormatClass(TextOutputFormat.class);

			if (!job.waitForCompletion(true)) {
				return 1;
			}

			Job job2 = new Job(conf, "TopK");
			job2.setJarByClass(WordCountTopKDriver.class);

			// specify a Mapper
			job2.setMapperClass(TopKMapper.class);

			// specify a Reducer
			job2.setReducerClass(TopKReducer.class);

			// specify output types
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(IntWritable.class);

			// set the number of reducer to 1
			job2.setNumReduceTasks(1);

			// specify input and output directories
			FileInputFormat.addInputPath(job2, new Path(args[1]));
			job2.setInputFormatClass(KeyValueTextInputFormat.class);

			FileOutputFormat.setOutputPath(job2, new Path(args[2]));
			job2.setOutputFormatClass(TextOutputFormat.class);

			if (!job2.waitForCompletion(true)) {
				return 1;
			}

			// --------- TASK 2 ------------

			Job job3 = new Job(conf, "DelayRatio");
			job3.setJarByClass(WordCountTopKDriver.class);

			// specify a Mapper
			job3.setMapperClass(DelayRatioMapper.class);

			// specify a Reducer
			job3.setReducerClass(DelayRatioReducer.class);

			// specify output types
			job3.setOutputKeyClass(Text.class);
			job3.setOutputValueClass(DelayAndCountWritable.class);

			// specify input and output directories
			FileInputFormat.addInputPath(job3, new Path(args[0]));
			job3.setInputFormatClass(TextInputFormat.class);

			FileOutputFormat.setOutputPath(job3, new Path(args[3]));
			job3.setOutputFormatClass(TextOutputFormat.class);

			if (!job3.waitForCompletion(true)) {
				return 1;
			}

			Job job4 = new Job(conf, "DelayRatioTopK");
			job4.setJarByClass(WordCountTopKDriver.class);

			// specify a Mapper
			job4.setMapperClass(DelayRatioTopKMapper.class);

			// specify a Reducer
			job4.setReducerClass(DelayRatioTopKReducer.class);

			// specify output types
			job4.setOutputKeyClass(Text.class);
			job4.setOutputValueClass(FloatWritable.class);

			// set the number of reducer to 1
			job4.setNumReduceTasks(1);

			// specify input and output directories
			FileInputFormat.addInputPath(job4, new Path(args[3]));
			job4.setInputFormatClass(KeyValueTextInputFormat.class);

			FileOutputFormat.setOutputPath(job4, new Path(args[4]));
			job4.setOutputFormatClass(TextOutputFormat.class);

			return (job4.waitForCompletion(true) ? 0 : 1);

		} catch (InterruptedException | ClassNotFoundException | IOException e) {
			System.err.println("Error during driver job.");
			e.printStackTrace();
			return 2;
		}
	}
}
