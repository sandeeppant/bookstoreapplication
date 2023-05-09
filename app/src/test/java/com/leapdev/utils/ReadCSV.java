package com.leapdev.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ReadCSV {

	private static final Logger logger = LogManager.getLogger(ReadCSV.class);

	public static List<String[]> readAllData(String inputFile) {
		List<String[]> allData = null;
		try {
			File file = new File(ReadCSV.class.getClassLoader().getResource(inputFile).getFile());
			FileReader filereader = new FileReader(file);
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			allData = csvReader.readAll();
		} catch (Exception e) {
			logger.error("Error reading data", e);
		}
		return allData;
	}

	public static Map<String, String> readDataByRow(String inputFile, String key) throws IOException {
		List<String[]> allData = readAllData(inputFile);
		String[] rowFound = null;
		for (String[] data : allData) {
			if (data[0].equals(key)) {
				rowFound = data;
				break;
			}
		}
		Map<Integer, String> header = getHeader(inputFile);
		Map<String, String> lineMap = new LinkedHashMap<>();
		for (int i = 0; i < rowFound.length; i++) {
			lineMap.put(header.get(i), rowFound[i]);
		}
		return lineMap;
	}

	public static Map<Integer, String> getHeader(String filePath) throws IOException {

		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		int index = 0;
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(ReadCSV.class.getClassLoader().getResourceAsStream(filePath)));
		String headers = bufferedReader.readLine();
		bufferedReader.close();

		String[] headerNames = null;
		if (headers != null) {
			headerNames = headers.split(",");
		}

		for (String header : headerNames) {
			headerMap.put(index++, header);
		}
		return headerMap;
	}
}
