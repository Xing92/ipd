package com.xing.ipd.madaline;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Neuron {

	private List<Double> inputs = new ArrayList<Double>();
	private List<Double> weights = new ArrayList<Double>();
	private String sign;

	public Neuron(String sign, List<Integer> weights) {
		this.sign = sign;
		int numberOfOnes = weights.stream().filter(weight -> weight == 1).collect(Collectors.toList()).size();
		weights.stream().forEach(weight -> {
			this.weights.add(weight / Math.sqrt((double) numberOfOnes)); // Question : should I do Math.sqrt() there?
		});
	}

	public void setInputs(List<Integer> inputs) {
		int numberOfOnes = inputs.stream().filter(input -> input == 1).collect(Collectors.toList()).size();
		this.inputs = inputs.stream().map(v -> (double) v / Math.sqrt(numberOfOnes)).collect(Collectors.toList());
	}

	public double calculateOutput() {
		double sum = 0;
		for (int i = 0; i < weights.size(); i++) {
			sum += inputs.get(i) * weights.get(i);
		}
		return sum;
	}

	public String getSign() {
		return sign;
	}

}
