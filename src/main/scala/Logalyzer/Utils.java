package Logalyzer;
//
//import Logalyzer.Logalyzer;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
//import org.apache.hadoop.fs.Path;
//
//import Logalyzer.Mappers.*;
//import Logalyzer.Reducers.*;
//
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;

public class Utils {

//    public Job createJob(Configuration hadoopConf, String jobName, String inPath, String outPath, String mapClass, String reducerClass){
//
////        Mapper[Object, Text, Text, IntWritable] mapper = Class.forName(mapClass).asInstanceOf[Mapper[Object, Text, Text, IntWritable]]
//
//        Job job = Job.getInstance(hadoopConf, jobName);
//        job.setJarByClass(Logalyzer);
//        job.setMapperClass((Mapper<Object, Text, Text, IntWritable>)Class.forName(mapClass));
//        job.setCombinerClass(LogStatAggregator.class);
//        job.setReducerClass(LogStatAggregator.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(job, new Path(inPath));
//        FileOutputFormat.setOutputPath(job, new Path(outPath));
//        return job;
//    }

}
