# SampleEnvSetup

This application allows you send messages to kafka topics.

First of all you should have installed next tools:

* Vagrant
* jmeter 4.0

When jmeter is installed you have to add environment path variable: %JMETER_HOME% 
If you work under Windows please ensure that all files which contains bash script commands have EOL conversation - Unix.
Open command line at SampleEnvSetup directory and run command:
 
* vagrant up

Ensure that kafka successfully installed. Then you have to run:
* mvn clean install  

Ensure that additional libraries were added to jmeter library/ext.
After that you can open jmeter-Loader.jmx file by jmeter. 
Ensure that user defined variable "project_home" correctly set.
Run jmeter testplan and check if messages is sent to kafka topics. 