cd /etc/cassandra/conf

echo "concurrent_counter_writes: 32" >> cassandra.yaml
echo "counter_write_request_timeout_in_ms: 5000" >> cassandra.yaml
echo "broadcast_rpc_address: $1" >> cassandra.yaml
sed -i "s/rpc_address: localhost/rpc_address: 0.0.0.0/g" cassandra.yaml
sed -i "s/# broadcast_address: 1.2.3.4/broadcast_address: $1/g" cassandra.yaml
sed -i "s/127.0.0.1/$1/g" cassandra.yaml
sed -i "s/listen_address: localhost/listen_address: $1/g" cassandra.yaml
sed -i "s/# broadcast_address: 1.2.3.4/broadcast_address: $1/g" cassandra.yaml
