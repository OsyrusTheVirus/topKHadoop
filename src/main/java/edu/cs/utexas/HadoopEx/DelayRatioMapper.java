package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DelayRatioMapper extends Mapper<Object, Text, Text, DelayAndCountWritable>{
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] data = value.toString().split(",");
        try {
            context.write(new Text(data[4]), new DelayAndCountWritable(Integer.parseInt(data[11]), 1));
        } catch (NumberFormatException e) {
        }
    }
}

//YEAR, MONTH,DAY,DAY_OF_WEEK,AIRLINE,FLIGHT_NUMBER,TAIL_NUMBER,ORIGIN_AIRPORT,DESTINATION_AIRPORT,SCHEDULED_DEPARTURE,DEPARTURE_TIME,DEPARTURE_DELAY,TAXI_OUT,WHEELS_OFF,SCHEDULED_TIME,ELAPSED_TIME,AIR_TIME,DISTANCE,WHEELS_ON,TAXI_IN,SCHEDULED_ARRIVAL,ARRIVAL_TIME,ARRIVAL_DELAY,DIVERTED,CANCELLED,CANCELLATION_REASON,AIR_SYSTEM_DELAY,SECURITY_DELAY,AIRLINE_DELAY,LATE_AIRCRAFT_DELAY,WEATHER_DELAY
//2015, 1,    1,  4,          AS,     98,           N407AS,      ANC,          SEA,                0005,               2354,          -11,            21,      0015,      205,           194,         169,     1448,    0404,      4,0430,0408,-22,0,0,,,,,,
