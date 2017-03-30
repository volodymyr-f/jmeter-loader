cd /etc/cassandra/conf

sed -i "s/rpc_address: localhost/rpc_address: 0.0.0.0/g" cassandra.yaml
sed -i "s/# broadcast_address: 1.2.3.4/broadcast_address: $1/g" cassandra.yaml
sed -i "s/127.0.0.1/$1/g" cassandra.yaml
sed -i "s/listen_address: localhost/listen_address: $1/g" cassandra.yaml
sed -i "s/# broadcast_address: 1.2.3.4/broadcast_address: $1/g" cassandra.yaml
