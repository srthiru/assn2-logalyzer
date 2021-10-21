# Logalyzer - a log analyzer

The aim of this project is to create a distributed application to analyze logs and produce different statisctics
from it using Map Reduce.

We implement map reduce using Apache hadoop, a framework for distributed processing of large datasets that also provides
an implementation of the Map  Reduce framework.

The input log files were generated using the LogGenerator implementation. 

Configurations in the log-back.xml were changed to generate log files rolling every minute using the following:

`<fileNamePattern>log/LogFileGenerator.%d{yyyy-MM-dd_HH-mm}.log</fileNamePattern>`

The input log shards along with the built JAR file were deployed to run the map reduce application.

Typical map reduce applications run with the following tasks:

* Map - Maps an input key-value pair of <Object, value> to another key-value pair, where the key and value of the output depends on the logic to be implemented
* Partition - Dictates logic of how the output of the mapper should be assigned to reducers
* Group - Groups similar keys to be sent to a reducer to optimize processing and sorts the keys before sending them
* Reduce - Reduces the input key value pair by typically applying some aggregation on the values

To run the project

Using IntelliJ
Install the Intellij IDE and the Scala plugin
Import project folder into IntelliJ
Build the JAR using sbt-assembly

Using SBT
Install Scala using SBT following the instructions from the link
Open a command prompt and navigate to the project folder
Run the following command - "sbt assembly" (make sure that sbt is added to your environment path) to build the JAR file

Please find further explanation of the implementation in the docs folder and the video documentation in this https://www.youtube.com/watch?v=AooxyMQtPd8.



