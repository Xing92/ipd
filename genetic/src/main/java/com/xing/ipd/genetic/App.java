package com.xing.ipd.genetic;

import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	public static final double SCREEN_HEIGHT = 450;
	public static final double SCREEN_WIDTH = 700;
	public static final double AXIS_Y_SIZE = 13;
	public static final double AXIS_X_SIZE = 3;
	Group root;
	Scene scene;
	Button nextGenerationButton;
	Label generationNumberTextField;
	Label meanValueTextField;
	Label bestGeneTextField;

	static GeneticLogic gl;
	Group genePoints = new Group();
	Group functionPoints = new Group();
	List<Gene> genes;

	public static void main(String[] args) {
		System.out.println("Hello World!");
		launch();
		System.out.println("Goodbye World!");
	}

	@Override
	public void start(Stage stage) throws Exception {
		root = new Group();
		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		root.getChildren().add(genePoints);
		root.getChildren().add(functionPoints);
		nextGenerationButton = new Button("Next Generation");
		root.getChildren().add(nextGenerationButton);
		generationNumberTextField = new Label("Generation number: 0");
		generationNumberTextField.setLayoutY(30);

		meanValueTextField = new Label("Mean fitness: 0");
		meanValueTextField.setLayoutY(30);
		meanValueTextField.setLayoutX(180);

		bestGeneTextField = new Label("Best gene fit: 0, X: 0");
		bestGeneTextField.setLayoutY(0);
		bestGeneTextField.setLayoutX(180);

		root.getChildren().add(generationNumberTextField);
		root.getChildren().add(meanValueTextField);
		root.getChildren().add(bestGeneTextField);

		Line lineX = new Line(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);
		root.getChildren().add(lineX);

		Line lineY = new Line(10, 0, 10, SCREEN_HEIGHT);
		root.getChildren().add(lineY);

		drawFunction();
		gl = new GeneticLogic(20);
		genes = gl.getGenes();
		drawGenes();

		stage.setScene(scene);
		stage.setTitle("XING - Genetic Algorithm");
		stage.show();

		nextGenerationButton.setOnAction(createNextGenerationListener());
	}

	private EventHandler createNextGenerationListener() {
		return ae -> {

			for (int i = 0; i < 1000; i++) {
				genePoints.getChildren().clear();
				genes = gl.generateNewPopulation();
				generationNumberTextField.setText("Generation number: " + gl.getGenerationNumber());
				bestGeneTextField.setText(String.format("Best gene fitness:%.3f, X:%.3f",
						gl.findBestGene().evalueateGene(), gl.findBestGene().getValue()));
				meanValueTextField.setText(String.format("Mean fitness: %.3f", gl.getMeanFitness()));
				drawGenes();
			}
		};
	}

	private void drawGenes() {
		for (Gene gene : genes) {
			Point point = new Point(gene.getValue(), gene.evalueateGene());
			point.setColor(Color.BLUE);
			point.setSize(5);
			genePoints.getChildren().add(point.getCircle());
		}
	}

	private void drawFunction() { // f(x) = (e^x * sin(10*pi*x) + 1) / x
		double start = 0.5;
		double end = 2.5;
		double interval = 0.001;

		for (double x = start; x < end; x += interval) {
			double y = (Math.pow(Math.E, x) * Math.sin(Math.PI * 10 * x) + 1) / x;
			Point point = new Point(x, y);
			Circle circle = point.getCircle();
			functionPoints.getChildren().add(circle);
		}
	}
}
