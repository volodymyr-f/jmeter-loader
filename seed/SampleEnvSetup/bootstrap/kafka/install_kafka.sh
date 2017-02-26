cd /opt
wget -q http://apache.ip-connect.vn.ua/kafka/0.10.2.0/kafka_2.12-0.10.2.0.tgz
tar -xf kafka_2.12-0.10.2.0.tgz
rm kafka_2.12-0.10.2.0.tgz
mv /opt/kafka_2.12-0.10.2.0 /opt/kafka
cd /opt/kafka
mkdir logs
touch zoo-server.log
touch kafka-server.log