package com.app.elk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReferentialHash {
	protected static List<String> referentialHash = null;
	private static Map<String, List<String>> dataMap = null; 
	private static ReferentialHash referentialDataInstance = null;
	
	public static ReferentialHash getInstance() {
		if (referentialDataInstance == null)
			referentialDataInstance = new ReferentialHash();
		
		return referentialDataInstance;
	}

	protected void loadReferentialData(String hashReferencePath) {
		dataMap = new HashMap<String, List<String>>();
		try {
			List<Path> files = Files.list(Paths.get(hashReferencePath)).filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".data")).collect(Collectors.toList());
			for(Path file : files) {
				dataMap.put(file.getFileName().toString().substring(0, file.getFileName().toString().indexOf(".data")), Files.lines(Paths.get(file.toString()), StandardCharsets.ISO_8859_1).sorted()
					.distinct().collect(Collectors.toList()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, List<String>> getDataMap() {
		return dataMap;
	}
}
