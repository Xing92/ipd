package com.xing.ipd.oneneuron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	public static final double SCREEN_HEIGHT = 450;
	public static final double SCREEN_WIDTH = 450;
	private static List<Point> points = new ArrayList();
	private static Line line;
	private static Neuron neuron;
	Group root;
	Scene scene;
	Button generatePointsButton;
	Button runOneEpochButton;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		root = new Group();
		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		generatePointsButton = new Button();
		generatePointsButton.setText("Generate points");
		runOneEpochButton = new Button();
		runOneEpochButton.setText("Run Epoch");
		runOneEpochButton.setLayoutX(150);
		root.getChildren().add(generatePointsButton);
		root.getChildren().add(runOneEpochButton);

		Line coord1 = new Line(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
		Line coord2 = new Line(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);
		root.getChildren().add(coord1);
		root.getChildren().add(coord2);

		line = new Line(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);
		line.setStroke(Color.BLUE);
		line.setStrokeWidth(3);
		root.getChildren().add(line);
		stage.setScene(scene);
		stage.show();

		generatePointsButton.setOnAction(ae -> {
			System.out.println("Clicked");
			points.addAll(Point.generatePoints(10));
			root.getChildren().addAll(points.stream().filter(point -> !root.getChildren().contains(point.getCircle()))
					.map(point -> point.getCircle()).collect(Collectors.toList()));
		});

		runOneEpochButton.setOnAction(ae -> {
			if (neuron == null) {
				neuron = new Neuron(2);
				System.out.println("Creating new Neuron: " + neuron);
			}
			double guessedValue = 0;
			for (Point point : points) {
				neuron.addInput(point.x);
				neuron.addInput(point.y);
				System.out.println("For Point: [" + point.x + " ; " + point.y + "]");
				guessedValue = neuron.calculate();
				System.out.println("Calculated: [" + guessedValue + "]");
				List<Double> pointCoords = new ArrayList();
				pointCoords.add(point.x);
				pointCoords.add(point.y);
				neuron.calculateAndAdjustWeights(pointCoords, realPointTangent(point), guessedValue);
			}

			line.setRotate(guessedValue); // TODO: Rotation according to calculation

		});

	}

	private double realPointTangent(Point point) {
		double x = point.x;
		double y = point.y;
		double result = Math.atan(y / x);

		return result;
	}

}
