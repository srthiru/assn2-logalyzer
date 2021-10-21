# Logalyzer - a log analyzer

## Objective

The aim of this project is to create a distributed application to analyze logs and produce different statisctics
from it using Map Reduce.

We implement map reduce using Apache hadoop, a framework for distributed processing of large datasets that also provides
an implementation of the Map  Reduce framework in Scala.

## Tasks
The tasks specified in the assignment description are as follows:

* First, you will compute a spreadsheet or an CSV file that shows the distribution of different types of messages across predefined time intervals and injected string instances of the designated regex pattern for these log message types. 
* Second, you will compute time intervals sorted in the descending order that contained most log messages of the type ERROR with injected regex pattern string instances. Please note that it is ok to detect instances of the designated regex pattern that were not specifically injected in log messages because they can also be randomly generated. 
* Then, for each message type you will produce the number of the generated log messages. 
* Finally, you will produce the number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern.

## Input
The input log files were generated using the provided LogGenerator implementation. 

Configurations in the log-back.xml were changed to generate log files rolling ***every minute*** using the following:

`<fileNamePattern>log/LogFileGenerator.%d{yyyy-MM-dd_HH-mm}.log</fileNamePattern>`

The input log shards along with the built JAR file were deployed to run the map reduce application.

## Map Reduce application

Typical map reduce applications run with the following tasks:

* Map - Maps an input key-value pair of <Object, value> to another key-value pair, where the key and value of the output depends on the logic to be implemented.
* Partition - Dictates logic of how the output of the mapper should be assigned to reducers. By default, the partitioner assigns key-value pairs with the same key to the same reducer.
* Group - Groups similar keys to be sent to a reducer to optimize processing and sorts the keys before sending them.
* Reduce - Reduces the input key value pair by typically applying some aggregation on the values.

Mapper and Reducer classes were created for each of the tasks.

## Output

The output of the application are as follows:

Task 1: "Interval, LogType, HasPattern/No, Count"
Task 2: "Interval, Count" (sorted by count for Error messages that contain the pattern)
Task 3: "Log Type, Count"
Task 4: "Log Type, MaxChars"

Output as comma-separated values.

The output of the map reduce application will be in split into parts. These can be stitched together by using the cat command as follows:

`cat file1 file2 file3 > file.csv`

## Limitations of the implemetation
* For task 2, since it is a sorting application, number of reducers were restricted to 1 to avoid resulting to multiple part output files. Since the input to this file is already aggregated this would not be an issue, but this should be worked around for large scale processing
* The interval specified can only be configured based on seconds. Can add implementation to split the interval based on other aggregations - hour, min etc.

## Installation

Using IntelliJ
Install the Intellij IDE and the Scala plugin
Import project folder into IntelliJ
Build the JAR using sbt-assembly

Using SBT
Install Scala using SBT following the instructions from the link
Open a command prompt and navigate to the project folder
Run the following command - *sbt assembly* (make sure that sbt is added to your environment path) to build the JAR file

## Dependencies

`
"org.apache.hadoop" % "hadoop-core" % "1.2.1"

"org.slf4j" % "slf4j-api" % "2.0.0-alpha5"

"org.scalactic" %% "scalactic" % "3.2.9"

"com.typesafe" % "config" % "1.4.1"
`

## Documentation

Please find further explanation of the implementation in the docs folder and the video documentation in this https://www.youtube.com/watch?v=AooxyMQtPd8.

