# encoding: utf-8
require "logstash/filters/base"
require "logstash/namespace"
require "logstash-filter-dlp_encryptor"
require "java"

class LogStash::filters::DLPEncryptor < LogStash::Filters::Base
  config_name "dlp_encryptor"

  def self.javaClass() com.bnp.logstash.dlp.DLPEncryptor.java_class; end
end
