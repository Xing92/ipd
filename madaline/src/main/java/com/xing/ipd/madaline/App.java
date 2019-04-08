package com.xing.ipd.madaline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class App extends Application {

	private static final double SCREEN_HEIGHT = 450;
	private static final double SCREEN_WIDTH = 450;

	private static List<Pixel> pixels = new ArrayList<>();

	private Group root;
	private Scene scene;
	private Button predictButton;
	private Button clearButton;
	private Button saveButton;
	private TextField letterField;
	private DrawingHelper drawingHelper;

	private static final String patternFile = "C:\\Pawel\\git\\ipd\\madaline\\patterns.txt";
//	private static final String patternFile = "C:\\Pawel\\git\\ipd\\madaline\\outputs.txt";
	private static final String testFile = "C:\\Pawel\\git\\ipd\\madaline\\tests.txt";
	private static final String outputFile = "C:\\Pawel\\git\\ipd\\madaline\\outputs.txt";

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		root = new Group();
		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		predictButton = new Button();
		predictButton.setText("Predict");
		root.getChildren().add(predictButton);
		clearButton = new Button();
		clearButton.setText("Clear");
		clearButton.setLayoutX(75);
		root.getChildren().add(clearButton);
		saveButton = new Button();
		saveButton.setText("Save");
		saveButton.setLayoutX(150);
		root.getChildren().add(saveButton);
		letterField = new TextField();
		letterField.setLayoutY(30);
		root.getChildren().add(letterField);
		createPixels();
		pixels.forEach(pixel -> root.getChildren().add(pixel.getRectangle()));

		stage.setScene(scene);
		stage.show();

		scene.setOnMouseDragged(createDrawPixelOnMousePressListener());
		predictButton.setOnAction(createPredictListener());
		clearButton.setOnAction(createClearListener());
		saveButton.setOnAction(createSaveListener());
	}

	private EventHandler<ActionEvent> createSaveListener() {
		return e-> {
			FileParser outputFileParser = new FileParser(outputFile);
			outputFileParser.savePixelsToFile(letterField.getText(), pixels);
		};
	}

	private EventHandler<ActionEvent> createClearListener() {
		return e -> {
			drawingHelper.clearPixels();
		};
	}

	private EventHandler createDrawPixelOnMousePressListener() {
		return e -> {
			MouseEvent me = (MouseEvent) e;
			System.out.println(me.getX());
			Pixel pixel = drawingHelper.getPixelFromMouse(me.getX(), me.getY());
			if (pixel != null) {
				pixel.setSelected(1);
			}
		};
	}

	private EventHandler createPredictListener() {
		return e -> {
			test();
		};
	}

	private void createPixels() {
		for (int y = 0; y < Pixel.PIXELS_DIMENTION; y++) {
			for (int x = 0; x < Pixel.PIXELS_DIMENTION; x++) {
				Pixel pixel = new Pixel(x, y);
				pixels.add(pixel);
			}
		}
		drawingHelper = new DrawingHelper(pixels);

	}

	public static void test() {
		FileParser patternFileParser = new FileParser(patternFile);
		FileParser testFileParser = new FileParser(testFile);

		Map<String, List<Integer>> patterns = patternFileParser.retrievePatterns();
		Map<String, List<Integer>> tests = testFileParser.retrievePatterns();

		ChooseOutputNeuron con = new ChooseOutputNeuron();

		List<Neuron> neurons = new ArrayList<>();
		patterns.forEach((k, v) -> neurons.add(new Neuron(k, v)));

		neurons.forEach(neuron -> con.addInput(neuron.getSign(), neuron));

		// neurons.forEach(neuron -> neuron.setInputs(tests.get("S")));
		neurons.forEach(neuron -> neuron
				.setInputs(pixels.stream().map(pixel -> pixel.isSelected()).collect(Collectors.toList())));

		String sign = con.getOutputSign();
		System.out.println(sign);
	}

}
