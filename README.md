# logreader
It is logfile reader in "tail -f" manner to incorporate with logstash.
To make use, setup logstash to receive plain log with TCP input plugin.
You can see the configuration at https://www.elastic.co/guide/en/logstash/current/plugins-inputs-tcp.html

## How to build
`
$ mvn package
`

## How to run
Download the pre-build logreader.jar file from the git repo or build it after cloning.

`
$ java -jar logreader.jar file.log <logstash.ip> <logstash.tcp.port>
`

* file.log - log file path
* logstash.ip - logstash server's IP or domain name
* logstash.tcp.port - logstash server's listening port for TCP input plugin

Happy try!!
