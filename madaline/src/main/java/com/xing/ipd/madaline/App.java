package com.xing.ipd.madaline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

	private static final String patternFile = "C:\\Pawel\\git\\ipd\\madaline\\patterns.txt";
	private static final String testFile = "C:\\Pawel\\git\\ipd\\madaline\\tests.txt";

	public static void main(String[] args) {
		FileParser patternFileParser = new FileParser(patternFile);
		FileParser testFileParser = new FileParser(testFile);

		Map<String, List<Integer>> patterns = patternFileParser.retrievePatterns();
		Map<String, List<Integer>> tests = testFileParser.retrievePatterns();

		ChooseOutputNeuron con = new ChooseOutputNeuron();

		List<Neuron> neurons = new ArrayList<>();
		patterns.forEach((k, v) -> neurons.add(new Neuron(k, v)));

		neurons.forEach(neuron -> con.addInput(neuron.getSign(), neuron));

		neurons.forEach(neuron -> neuron.setInputs(tests.get("X")));

		String sign = con.getOutputSign();
		System.out.println(sign);
	}
}
