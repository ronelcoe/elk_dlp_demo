package com.gm.elk;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.PathParam;

@Path("/dlp")
public class DLPMultiLogger {
    private static Random rd = new Random();
    private String referenceSensitiveData;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private boolean localTesting = false;
    private DateFormat logDateFormat = new SimpleDateFormat("YYY-MM-dd HH:mm:ss,SSS");
    String logTemplate = " []  INFO [APM1,,,] 13924 --- [       Thread-5] com.gm.elk.public class DLPLogger  [208] : ";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/load/{dataPath}")
    public String loadReferentialData(@PathParam("dataPath") String dataPath) {
        referenceSensitiveData = localTesting ? dataPath.replace("_", "\\") : dataPath.replace("_", "/");
        String startTime = dateFormat.format(new Date());
		ReferentialHash.getInstance().loadReferentialData(referenceSensitiveData);
		StringBuilder sb = new StringBuilder();
		ReferentialHash.getInstance().getDataMap().forEach((k,v)-> sb.append("key: " + k + " size: " + v.size() + " "));
        return "Started to load sensitive data: " + referenceSensitiveData + " from " + startTime + " to " + dateFormat.format(new Date()) + " with size: " + sb;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/generateLog/{logPathAndCount}")
    public String generateLog(@PathParam("logPathAndCount") String logPathAndCount) throws IOException {
        String log = null; 
        int lineCount = 0;

        if (localTesting) {
            log = logPathAndCount.replace("_", "\\").substring(0, logPathAndCount.indexOf(".log") + 4);
            lineCount = Integer.valueOf(logPathAndCount.replace("_", "\\").substring(logPathAndCount.indexOf(".log") + 4, logPathAndCount.length()));
        } else {
            log = logPathAndCount.replace("_", "/").substring(0, logPathAndCount.indexOf(".log") + 4);
            lineCount = Integer.valueOf(logPathAndCount.replace("_", "/").substring(logPathAndCount.indexOf(".log") + 4, logPathAndCount.length()));
        } 

        String startTime = dateFormat.format(new Date());
        generateLog(log, lineCount);
        return "Started to generate log : " + log + " from " + startTime + " to " + dateFormat.format(new Date()) + " with lines of " + lineCount;
    }

	public String generateLog(String applicationLog, int logCount) throws IOException {
		FileWriter fw = null;
        String dataKey = localTesting ? applicationLog.substring(applicationLog.lastIndexOf("\\") + 1, applicationLog.length()-4) :
            applicationLog.substring(applicationLog.lastIndexOf("/") + 1, applicationLog.length()-4);
		try {
			fw = new FileWriter(applicationLog, true);
			while (logCount-- > 0) {
				int millis = new Random().nextInt(1000);
				//if (rd.nextBoolean() == true)
					//thowException(millis);

				logSimpleMessage(millis, fw, dataKey);
			}
		} catch (IOException e) {
			fw.write(e.getMessage());
		} finally {
			fw.close();
		}
		return "Generated logs finished...";
	}

	private void thowException(int millis) {
		if (millis % 7 == 0)
			throw new RuntimeException("Throwing random exception...");
	}

	private void logSimpleMessage(int millis, FileWriter fw, String dataKey) throws IOException {
		if (millis % 2 == 0) {
			fw.write(logDateFormat.format(new Date()) + logTemplate + generateSensitiveData(dataKey) + "Slept for " + millis + generateSensitiveData(dataKey) + ".To set up the "
					+ generateSensitiveData(dataKey) + "Kibana dashboards for Metricbeat, use the appropriate command "
					+ "for your system" + generateSensitiveData(dataKey)
					+ ". The command shown here loads the dashboards from the" + generateSensitiveData(dataKey)
					+ " Metricbeat package. For more options, " + generateSensitiveData(dataKey) + generateSensitiveData(dataKey)
					+ "such as loading customized dashboards" + generateSensitiveData(dataKey) + "\n");
		} else if (millis % 3 == 0) {
			fw.write(logDateFormat.format(new Date()) + logTemplate + "All details here are sensitive data: ->" + generateSensitiveData(dataKey)
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + generateSensitiveData(dataKey) 
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + generateSensitiveData(dataKey)
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + generateSensitiveData(dataKey)
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + generateSensitiveData(dataKey)
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + "\n");
		} else if (millis % 5 == 0) {
			fw.write(logDateFormat.format(new Date()) + logTemplate + generateSensitiveData(dataKey) + "Hello! Looks like youre enjoying the discussion,"
					+ generateSensitiveData(dataKey) + "but you havent signed up for an account yet."
					+ generateSensitiveData(dataKey)
					+ "When you create an account, we remember exactly what youve read, so you always come right back where you left off. "
					+ "You also get notifications," + generateSensitiveData(dataKey) + "here and via email, "
					+ generateSensitiveData(dataKey) + "whenever someone replies to you. " + "And you"
					+ generateSensitiveData(dataKey) + generateSensitiveData(dataKey) + "can like posts to share the love. \n");
		} else {
			fw.write(logDateFormat.format(new Date()) + logTemplate + generateSensitiveData(dataKey) + "Metricbeat helps you monitor your servers and the services they host"
					+ generateSensitiveData(dataKey) + "by collecting metrics from the operating system and services."
					+ generateSensitiveData(dataKey) + "To get started with your own" + generateSensitiveData(dataKey)
					+ "Metricbeat setup, install and configure these related products" + generateSensitiveData(dataKey)
					+ "\n");
		}
	}

	//TODO pass key
	private String generateSensitiveData(String dataKey) {
		List<String> refData = ReferentialHash.getInstance().getDataMap().get(dataKey);
		return rd.nextBoolean() == true ? " " + refData.get(rd.nextInt(refData.size()-1)) + " " : "";
	}
}