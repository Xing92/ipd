package com.xing.ipd.oneneuron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.EventHandler;
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
		Line perfect = new Line(0, SCREEN_HEIGHT, SCREEN_WIDTH, 0);
		root.getChildren().add(coord1);
		root.getChildren().add(coord2);
		root.getChildren().add(perfect);

		line = new Line(0, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT / 2);
		line.setStroke(Color.BLUE);
		line.setStrokeWidth(3);
		root.getChildren().add(line);
		stage.setScene(scene);
		stage.show();

		generatePointsButton.setOnAction(createGeneratePointsListener());
		runOneEpochButton.setOnAction(createRunOneEpochListener());

	}

	private EventHandler createGeneratePointsListener() {
		return ae -> {
			points.addAll(Point.generatePoints(1, false));
			// points.addAll(Point.generatePositivePoints(1, true));
			root.getChildren().addAll(points.stream().filter(point -> !root.getChildren().contains(point.getCircle()))
					.map(point -> point.getCircle()).collect(Collectors.toList()));
		};
	}

	private EventHandler createRunOneEpochListener() {
		return ae -> {
			if (neuron == null) {
				neuron = new Neuron(2);
				System.out.println("Creating new Neuron: " + neuron);
			}
			System.out.println("=====Start=====");
			double guessedValue = 0;
			Collections.shuffle(points);
			// for (int i = 0; i < 100; i++) { //TODO: for multiple epochs
			for (Point point : points) {
				guessedValue = oneIteration(point);
				line.setRotate(-angleFromA(guessedValue));
				neuron.train(point.getCoords(), realResult(point), guessedValue);
			}
			// }
			System.out.println("=====Finish=====");
		};
	}

	private double oneIteration(Point point) {
		neuron.addInput(point.x);
		neuron.addInput(point.y);
		System.out.println("For Point: [" + point.x + " ; " + point.y + "]");
		double guessedValue = 0;
		guessedValue = neuron.calculate();
		System.out.println("Calculated: [" + guessedValue + "]");
		return guessedValue;
	}

	private double angleFromA(double a) {
		double result = Math.atan(a) * (180 / Math.PI);
		return result;
	}

	private double realPointTangent(Point point) { 
		double x = point.x;
		double y = point.y;

		double result = Math.atan(y / x) * (180 / Math.PI);

		return result;
	}

	private double realResult(Point point) { 
		double x = point.x;
		double y = point.y;

		double result = y / x;

		return result;
	}

}
