package com.xing.ipd.oneneuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	private static final double SCREEN_HEIGHT = 450;
	private static final double SCREEN_WIDTH = 450;
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
		stage.setScene(scene);
		stage.show();

		generatePointsButton.setOnAction(ae -> {
			System.out.println("Clicked");
			points.addAll(generatePoints(100));
			root.getChildren().addAll(points.stream().filter(point -> !root.getChildren().contains(point.getCircle()))
					.map(point -> point.getCircle()).collect(Collectors.toList()));
		});

		runOneEpochButton.setOnAction(ae -> {
			if(neuron == null) {
				neuron = new Neuron(2);
				System.out.println("Creating new Neuron: " + neuron);
			}
			root.getChildren().remove(line);
			
		});

	}

	private List<Point> generatePoints(int amount) {
		List<Point> nodes = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < amount; i++) {
			double x = 10 + (SCREEN_WIDTH - 10) * r.nextDouble();
			double y = (x - 20) + ((x + 20) - (x - 20)) * r.nextDouble();
			Point point = new Point(x, y);
			nodes.add(point);
		}
		return nodes;
	}

	private static void test() {
		System.out.println("Hello World!");

		Neuron n1 = new Neuron(5);
		Neuron n2 = new Neuron(3);

		System.out.println(n1);
		n1.addInput(3);
		n1.addInput(5);
		n1.addInput(3);
		n1.addInput(3);
		n1.addInput(5);
		System.out.println(n1.calculate());
		System.out.println("=====");
		n2.addInput(3);
		n2.addInput(5);
		n2.addInput(3);
		System.out.println(n2);
		System.out.println(n2.calculate());

		System.out.println("Good bye World...");
	}

}
