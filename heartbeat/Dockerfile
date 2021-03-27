FROM docker.elastic.co/beats/heartbeat:6.7.2
COPY heartbeat.yml /usr/share/heartbeat/heartbeat.yml
USER root
RUN chmod go-w /usr/share/heartbeat/heartbeat.yml \
&& chown root:heartbeat /usr/share/heartbeat/heartbeat.yml \
&& mkdir -p /data/elk/applogs