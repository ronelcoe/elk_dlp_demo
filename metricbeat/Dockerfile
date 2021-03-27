FROM docker.elastic.co/beats/metricbeat:6.7.1
COPY metricbeat.yml /usr/share/metricbeat/metricbeat.yml
USER root
RUN chmod go-w /usr/share/metricbeat/metricbeat.yml \
&& chown root:metricbeat /usr/share/metricbeat/metricbeat.yml \
#&& /usr/share/metricbeat/metricbeat modules enable kubernetes
#USER metricbeat