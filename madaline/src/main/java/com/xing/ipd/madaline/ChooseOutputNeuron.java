package com.xing.ipd.madaline;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChooseOutputNeuron {

	private Map<String, Neuron> inputs = new HashMap<>();

	public void addInput(String sign, Neuron neuron) {
		inputs.put(sign, neuron);
	}

	public String getOutputSign() {
		Neuron chosenNeuron = inputs.entrySet().stream().findAny().get().getValue();
		String chosenSign = null;
		for (Entry<String, Neuron> input : inputs.entrySet()) {
			String sign = input.getKey();
			Neuron neuron = input.getValue();
			double neuronResult = neuron.calculateOutput();
			System.out.println(sign + "=[" + neuronResult + "]");
			if (neuronResult >= chosenNeuron.calculateOutput()) {
				chosenNeuron = neuron;
				chosenSign = sign;
			}
		}
		return chosenSign;
	}
}
