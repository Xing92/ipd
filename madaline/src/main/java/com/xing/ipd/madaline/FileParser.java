package com.xing.ipd.madaline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

	private static final Pattern letterSignPattern = Pattern.compile("^(\\w{1})$"); // TODO: change names
	private static final Pattern letterPattern = Pattern.compile("^([01]{4})$"); // TODO: change names
	private static final Pattern endPattern = Pattern.compile("^==========$"); // TODO: change names

	private String fileName;
	private BufferedReader reader;
	private List<String> lines = new ArrayList<>();

	public FileParser(String fileName) {
		this.fileName = fileName;
	}

	private void retrieveLines() {
		try {
			this.reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, List<Integer>> retrievePatterns() {
		Map<String, List<Integer>> patterns = new HashMap<>();
		retrieveLines();

		String letterSign = ""; // TODO: change names
		List<Integer> letterInteger = new ArrayList<>();
		for (String line : lines) {
			Matcher letterMatcher = letterPattern.matcher(line); // TODO: change names
			Matcher letterSignMatcher = letterSignPattern.matcher(line); // TODO: change names
			Matcher endMatcher = endPattern.matcher(line); // TODO: change names
			if (letterSignMatcher.find()) {
				letterSign = letterSignMatcher.group(1);
			} else if (letterMatcher.find()) {
				String groupOne = letterMatcher.group(1);
				for (char c : groupOne.toCharArray()) {
					letterInteger.add(Integer.parseInt(String.valueOf(c)));
				}
			} else if (endMatcher.find()) {
				patterns.put(letterSign, letterInteger);
				letterInteger = new ArrayList<>();
				letterSign = "";
			}
		}

		return patterns;
	}
}
