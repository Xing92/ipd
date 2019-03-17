package com.xing.ipd.oneneuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

	private List<Double> inputs = new ArrayList<Double>();
	private List<Double> weights = new ArrayList<Double>();

	public Neuron(int size) {
		for(int index = 0; index < size; index++) {
			weights.add(randomizeWeight(-1, 1));
		}
	}
	
	public void addInput(double input) {
		inputs.add(input);
	}

	private double randomizeWeight(double min, double max) {
		Random r = new Random();
		double randomValue = min + (max - min) * r.nextDouble();
		return randomValue;
	}
	
	public double calculate() {
		double result = 0;
		for (int index = 0; index < inputs.size(); index++) {
			result += weights.get(index) * inputs.get(0);
		}
		return result;
	}

	@Override
	public String toString() {
		return "Neuron [inputs=" + inputs + ", weights=" + weights + "]";
	}
	
	
}