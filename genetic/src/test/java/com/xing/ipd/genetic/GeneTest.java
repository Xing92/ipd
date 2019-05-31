package com.xing.ipd.genetic;

import java.util.Arrays;

import javax.print.attribute.standard.Chromaticity;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GeneTest {

	@Test
	public void geneClone() {
		Gene geneOne = new Gene();
		Gene geneTwo = geneOne.clone();

		Assert.assertTrue(Arrays.equals(geneOne.getChromosomes(), geneTwo.getChromosomes()));

		geneOne.getChromosomes()[5] = !geneOne.getChromosomes()[5];
		Assert.assertFalse(Arrays.equals(geneOne.getChromosomes(), geneTwo.getChromosomes()));

		geneOne.getChromosomes()[5] = !geneOne.getChromosomes()[5];
		Assert.assertTrue(Arrays.equals(geneOne.getChromosomes(), geneTwo.getChromosomes()));
	}
}
