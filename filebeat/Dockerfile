FROM docker.elastic.co/beats/filebeat:6.7.1
COPY filebeat.yml /usr/share/filebeat/filebeat.yml
COPY sensitiveData/*.data /data/elk/sensitiveData/
USER root
RUN chmod go-w /usr/share/filebeat/filebeat.yml \
&& chown root:filebeat /usr/share/filebeat/filebeat.yml \
&& mkdir -p /data/elk/applogs
#USER filebeat