FROM docker.elastic.co/logstash/logstash:6.7.1
WORKDIR /data/elk
COPY pipelines.yml /usr/share/logstash/pipelines.yml
COPY logstash.yml /usr/share/logstash/logstash.yml
COPY pipeline/*.conf /data/elk/pipeline/
COPY hashedData/*.data /data/elk/hashedData/

USER root
RUN chmod go-w /usr/share/logstash/logstash.yml \
&& yum -y update \
&& yum -y install gem \
&& chown -R logstash:logstash /data

COPY ruby/logstash-filter-dlp_encryptor.rb /usr/share/logstash/lib/logstash-filter-dlp_encryptor.rb
COPY ruby/logstash-filter-dlp_encryptor.gemspec /usr/share/logstash/logstash-filter-dlp_encryptor.gemspec
COPY ruby/dlp_encryptor.rb /usr/share/logstash/logstash-core/lib/logstash/filters/dlp_encryptor.rb
COPY ruby/logstash-filter-dlp_processor.rb /usr/share/logstash/lib/logstash-filter-dlp_processor.rb
COPY ruby/logstash-filter-dlp_processor.gemspec /usr/share/logstash/logstash-filter-dlp_processor.gemspec
COPY ruby/dlp_processor.rb /usr/share/logstash/logstash-core/lib/logstash/filters/dlp_processor.rb
COPY logstash-core-6.7.4.jar /usr/share/logstash/logstash-core/lib/jars/logstash-core-6.7.4.jar

USER logstash
WORKDIR /usr/share/logstash
RUN gem build logstash-filter-dlp_encryptor.gemspec \
&& gem build logstash-filter-dlp_processor.gemspec \
&& ./bin/logstash-plugin install --no-verify --local logstash-filter-dlp_encryptor-1.0.gem \
&& ./bin/logstash-plugin install --no-verify --local logstash-filter-dlp_processor-1.0.gem
