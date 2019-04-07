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
import javafx.scene.input.MouseEvent;
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

		stage.setScene(scene);
		stage.show();

		scene.setOnMouseClicked(createGeneratePointOnMouseClickListener());
		generatePointsButton.setOnAction(createGeneratePointsListener());
		runOneEpochButton.setOnAction(createRunOneEpochListener());

	}

	private EventHandler createGeneratePointOnMouseClickListener() {
		return e -> {
			MouseEvent me = (MouseEvent) e;
			Point point = Point.generatePointForGlobalCoord(me.getX(), me.getY());
			points.add(point);
			root.getChildren().add(point.getCircle());
		};
	}
	
	private EventHandler createGeneratePointsListener() {
		return ae -> {
			points.addAll(Point.generatePoints(1, false));
			root.getChildren().addAll(points.stream().filter(point -> !root.getChildren().contains(point.getCircle()))
					.map(point -> point.getCircle()).collect(Collectors.toList()));
		};
	}

	private EventHandler createRunOneEpochListener() {
		return ae -> {
			if (neuron == null) {
				neuron = new Neuron(1);
				System.out.println("Creating new Neuron: " + neuron);
			}
			double guessedValue = 0;
			Collections.shuffle(points);
			for (int i = 0; i < 100; i++) { // TODO: for multiple epochs
				for (Point point : points) {
					guessedValue = oneIteration(point);
					// line.setRotate(-angleFromA(guessedValue));
					neuron.train(point.getX(), point.getY(), guessedValue);
				}
			}
			drawLine();
		};
	}

	private double oneIteration(Point point) {
		neuron.addInput(point.x);
		double guessedValue = 0;
		guessedValue = neuron.calculate();
		return guessedValue;
	}

	private void drawLine() {
		if (line == null) {
			line = new Line();
			root.getChildren().add(line);
		}
		double x1 = SCREEN_WIDTH / 2;
		neuron.addInput(x1);
		double y1 = neuron.calculate();

		double x2 = - SCREEN_WIDTH / 2;
		neuron.addInput(x2);
		double y2 = neuron.calculate();

		line.setStartX(x1 + App.SCREEN_WIDTH / 2);
		line.setStartY(-y1 + App.SCREEN_HEIGHT / 2);
		line.setEndX(x2 + App.SCREEN_WIDTH / 2);
		line.setEndY(-y2 + App.SCREEN_HEIGHT / 2);

		line.setStroke(Color.BLUE);
		line.setStrokeWidth(3);
	}

}
