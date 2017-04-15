cd /opt
wget -q https://jmeter-plugins.org/downloads/file/ServerAgent-$1.zip
unzip ServerAgent-$1.zip -d /opt/server-agent/
rm -f ServerAgent-$1.zip

cd /opt/server-agent
mkdir logs
cd logs
touch server-agent.log