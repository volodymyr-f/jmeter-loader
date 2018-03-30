cd /opt
wget -q http://apache.ip-connect.vn.ua/kafka/0.10.2.1/kafka_$1.tgz
tar -xf kafka_$1.tgz
rm kafka_$1.tgz
mv /opt/kafka_$1 /opt/kafka

cd /opt/kafka

mkdir logs

cd logs
touch zoo-server.log
touch kafka-server.log

cd /opt/kafka/config

echo "advertised.host.name=$2" >> server.properties
echo "advertised.port=$3" >> server.properties