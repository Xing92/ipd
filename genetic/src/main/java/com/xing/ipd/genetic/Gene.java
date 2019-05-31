package com.xing.ipd.genetic;

import java.util.Arrays;
import java.util.Random;

public class Gene {

	private static final double start = 0.5;
	private static final double finish = 2.5;
	private static final double v2_11 = 2047;

	private static int idCounter = 1;
	private int id;
	private boolean[] chromosomes = new boolean[11]; // 2^10 < x < 2^11
	private static Random rand = new Random(10);

	public Gene() {
		id = idCounter;
		idCounter++;
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i] = rand.nextBoolean();
		}
	}

	public Gene(boolean[] chromosomes) {
		id = idCounter;
		idCounter++;
		for(int i=0; i<chromosomes.length; i++) {
			this.chromosomes[i] = chromosomes[i];
		}
	}

	public double getValue() {
		int power = 0;
		double result = 0;
		for (int i = chromosomes.length - 1; i >= 0; i--) {
			if (chromosomes[i]) {
				result += Math.pow(2, power);
			}
			power++;
		}
		result = start + (finish - start) * result / v2_11;
		return result;
	}

	public double evalueateGene() {
		double x = getValue();
		double y = (Math.pow(Math.E, x) * Math.sin(Math.PI * 10 * x) + 1) / x;
		return y;
	}

	public double evalueateGene(double offset) {
		double x = getValue();
		double y = (Math.pow(Math.E, x) * Math.sin(Math.PI * 10 * x) + 1) / x - offset;
		return y;
	}

	@Override
	public String toString() {
		return "Gene [id=" + id + ", chromosomes=" + Arrays.toString(chromosomes) + "]";
	}

	public boolean[] getChromosomes() {
		return chromosomes;
	}

	public Gene clone() {
		return new Gene(chromosomes);
	}

	public int getId() {
		return id;
	}

}
