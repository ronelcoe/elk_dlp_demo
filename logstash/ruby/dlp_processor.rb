# encoding: utf-8
require "logstash/filters/base"
require "logstash/namespace"
require "logstash-filter-dlp_processor"
require "java"

class LogStash::filters::DLPProcessor < LogStash::Filters::Base
  config_name "dlp_processor"

  def self.javaClass() com.bnp.logstash.dlp.DLPProcessor.java_class; end
end
