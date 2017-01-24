cd /opt
wget -q --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-linux-x64.tar.gz"
tar xf jdk-8u111-linux-x64.tar.gz
rm jdk-8u111-linux-x64.tar.gz
mv /opt/jdk1.8.0_111 /opt/java
alternatives --install /usr/bin/java java /opt/java 2
update-alternatives --install /usr/bin/java java /opt/java/jre/bin/java 1100