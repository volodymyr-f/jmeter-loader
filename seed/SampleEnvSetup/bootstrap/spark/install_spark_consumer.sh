cd /opt
git clone https://github.com/volodymyr-f/spark-structured-streaming-loader.git
cd spark-structured-streaming-loader
mkdir logs 
cd logs 
touch spark-consumer.log

cd /opt/spark-structured-streaming-loader/src/main/scala/com/loader/
# Set up cassandra host
sed -i "s/192.168.33.11/$1/g" KafkaConsumer.scala
# Set up cassandra keyspace
sed -i "s/into loader/into $2/g" KafkaConsumer.scala
# Set up cassandra table
sed -i "s/page_count_hourly/$3/g" KafkaConsumer.scala
# Set up kafka host
sed -i "s/192.168.33.10/$4/g" KafkaConsumer.scala
# Set up kafka topic
sed -i "s/loader_1/$5/g" KafkaConsumer.scala
# Set up mesos socket
sed -i "s/local\[2\]/$6/g" KafkaConsumer.scala
cd /opt/spark-structured-streaming-loader

sbt assembly