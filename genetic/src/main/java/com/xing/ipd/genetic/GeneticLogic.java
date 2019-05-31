package com.xing.ipd.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GeneticLogic {

	private static int generationNumber = 0;
	private int populationSize;
	private double crossoverRate = 0.15;
	private double mutationRate = 0.005;
	private List<Gene> genes = new ArrayList();
	private Gene bestGene;
	Random rand;

	public GeneticLogic(int populationSize) {
		this.populationSize = populationSize;
		rand = new Random();
		generateGenes();
	}

	private void generateGenes() {
		for (int i = 0; i < populationSize; i++) {
			genes.add(new Gene());
		}
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public List<Gene> generateNewPopulation() {
		generationNumber++;
		List<Double> roulette = generateRoulette(genes);
		List<Gene> rouletteGenes = rouletteGenes(roulette);
		List<Gene> crossoverGenes = crossoverGenes(rouletteGenes);
		List<Gene> mutatedGenes = mutateGenes(crossoverGenes);
		genes = mutatedGenes;
		return genes;
	}

	private Gene findBestGene(List<Gene> genes) {
		Gene bestGene = null;
		bestGene = genes.get(0);
		for (Gene gene : genes) {
			if (gene.evalueateGene() > bestGene.evalueateGene()) {
				bestGene = gene;
			}
		}
		return bestGene;
	}

	private List<Double> generateRoulette(List<Gene> genes) {
		List<Double> roulette = new ArrayList();
		double min = genes.stream().mapToDouble(gene -> gene.evalueateGene()).min().getAsDouble() - 0.01;
		double totalFit = genes.stream().mapToDouble(gene -> gene.evalueateGene(min)).sum();

		double previous = 0;
		for (Gene gene : genes) {
			roulette.add((previous + gene.evalueateGene(min)) / totalFit);
			previous += gene.evalueateGene(min);
		}
		return roulette;
	}

	private List<Gene> rouletteGenes(List<Double> roulette) {
		List<Gene> newGenes = new ArrayList();
		for (int i = 0; i < genes.size(); i++) {
			newGenes.add(rouletteOneGene(roulette));
		}
		return newGenes;
	}

	private Gene rouletteOneGene(List<Double> roulette) { // TODO There is an issue
		Gene gene = genes.get(0);
		double random = rand.nextDouble();
		for (int i = 0; i < roulette.size(); i++) {
			double previous = 0;
			if (i != 0) {
				previous = roulette.get(i - 1);
			}
			if (roulette.get(i) >= random && random >= previous) {
				gene = genes.get(i).clone();
			}
		}
		return gene;
	}

	private List<Gene> mutateGenes(List<Gene> genes) {
		List<Gene> mutatedGenes = new ArrayList();
		for (Gene gene : genes) {
			gene = gene.clone();
			mutatedGenes.add(gene);
			for (int i = 0; i < gene.getChromosomes().length; i++) {
				if (rand.nextDouble() < mutationRate) {
					gene.getChromosomes()[i] = !gene.getChromosomes()[i];
				}
			}
		}
		return mutatedGenes;
	}

	private List<Gene> crossoverGenes(List<Gene> genes) {
		List<Gene> crossoverGenes = new ArrayList();
		for (Gene gene : genes) {
			if (rand.nextDouble() < crossoverRate) {
				Gene secondGene = genes.get(rand.nextInt(genes.size()));
				gene = gene.clone();
				int crossoverPoint = rand.nextInt(gene.getChromosomes().length);
				for (int i = crossoverPoint; i < gene.getChromosomes().length; i++) {
					gene.getChromosomes()[i] = secondGene.getChromosomes()[i];
				}
			}
			crossoverGenes.add(gene);
		}
		return crossoverGenes;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

}
