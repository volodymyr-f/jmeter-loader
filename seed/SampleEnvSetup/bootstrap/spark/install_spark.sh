cd /opt
wget -q http://d3kbcqa49mib13.cloudfront.net/$1.tgz
tar -xf $1.tgz
rm $1.tgz
mv /opt/$1 /opt/spark
ssh-keygen -t rsa -P "" -f ~/.ssh/id_rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys