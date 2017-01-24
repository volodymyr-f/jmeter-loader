cd /opt
wget -q http://apache.ip-connect.vn.ua/kafka/0.10.1.1/kafka_2.11-0.10.1.1.tgz
tar -xf kafka_2.11-0.10.1.1.tgz
rm kafka_2.11-0.10.1.1.tgz
mv /opt/kafka_2.11-0.10.1.1 /opt/kafka
cd /opt/kafka
mkdir logs
touch zoo-server.log
touch kafka-server.log