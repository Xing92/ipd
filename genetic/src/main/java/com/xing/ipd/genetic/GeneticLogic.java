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
	private double crossoverRate = 0.10;
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
		System.out.println("START");
		System.out.println(genes);
		List<Double> roulette = generateRoulette(genes);
		List<Gene> newGenes = rouletteGenes(roulette);
		System.out.println(newGenes);
		List<Gene> crossoverGenes = crossoverGenes(newGenes);
		System.out.println(crossoverGenes);
		List<Gene> mutatedGenes = mutateGenes(crossoverGenes);
		genes = mutatedGenes;
		System.out.println(genes);
		System.out.println("FINISH");
		System.out.println("X=" + findBestGene(genes).getValue() + ", Y=" + findBestGene(genes).evalueateGene());
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
		double min = genes.stream().mapToDouble(gene -> gene.evalueateGene()).min().getAsDouble();
		double totalFit = genes.stream().mapToDouble(gene -> gene.evalueateGene(min)).sum();
		if (totalFit == 0) {
			totalFit = genes.stream().mapToDouble(gene -> gene.evalueateGene(min - 1)).sum();
		}

		List<Double> roulette = new ArrayList();
		double previous = 0;
		System.out.println("====");
		for (Gene gene : genes) {
			if(totalFit == 0) {
				System.err.println("GRRRRR!");
			}
			roulette.add((previous + gene.evalueateGene(min)) / totalFit);
			previous += gene.evalueateGene(min);
		}
		System.out.println(roulette);
		System.out.println("Total fitness = " + totalFit);
		return roulette;
	}

	private List<Gene> mutateGenes(List<Gene> genes) {
		List<Gene> mutatedGenes = new ArrayList();
		for (Gene gene : genes) {
			if (gene == null)
				System.out.println(genes);
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
				if (gene == null)
					System.out.println(genes);
				gene = gene.clone();
				secondGene = secondGene.clone();
				int crossoverPoint = rand.nextInt(gene.getChromosomes().length);
				for (int i = crossoverPoint; i < gene.getChromosomes().length; i++) {
					boolean tempChromosome = gene.getChromosomes()[i];
					gene.getChromosomes()[i] = secondGene.getChromosomes()[i];
					secondGene.getChromosomes()[i] = tempChromosome;
				}
			}
			crossoverGenes.add(gene);
		}
		return crossoverGenes;
	}

	private List<Gene> rouletteGenes(List<Double> roulette) {
		List<Gene> newGenes = new ArrayList();
		for (int i = 0; i < genes.size(); i++) {
			newGenes.add(rouletteOneGene(roulette));
		}
		return newGenes;
	}

	private Gene rouletteOneGene(List<Double> roulette) {
		Gene gene = genes.get(0);
		double random = rand.nextDouble();
		for (int i = 0; i < roulette.size(); i++) {
			double previous = 0;
			if (i != 0) {
				previous = roulette.get(i - 1);
			}
			if (roulette.get(i) >= random && random >= previous) {
				gene = genes.get(i);
			}
		}
		if (gene == null) {
			// System.out.println("Gene is null. Debug:===");
			// System.out.println(genes);
			// System.out.println(random);
			// System.out.println(roulette);
			// System.out.println("Debug End:============");
		}
		return gene;
	}
	
	public int getGenerationNumber() {
		return generationNumber;
	}

}
