package com.xing.ipd.oneneuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point {
	public double x;
	public double y;
	public double size;
	public Color color;
	public Circle circle;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.size = 2;
		this.color = Color.RED;
		this.circle = new Circle(x + App.SCREEN_WIDTH / 2, -y + App.SCREEN_HEIGHT / 2, size, this.color);
	}

	public Circle getCircle() {
		return circle;
	}

	public List<Double> getCoords() {
		List<Double> pointCoords = new ArrayList();
		pointCoords.add(x);
		pointCoords.add(y);
		return pointCoords;
	}

	public static List<Point> generatePoints(int amount, boolean perfect) {
		List<Point> points = new ArrayList<>();
		Random r = new Random();
		if (perfect) {
			for (int i = 0; i < amount; i++) {
				double xy = App.SCREEN_WIDTH * r.nextDouble() - App.SCREEN_WIDTH / 2;
				Point point = new Point(xy, xy);
				points.add(point);
			}
		} else {
			for (int i = 0; i < amount; i++) {
				double x = -App.SCREEN_WIDTH / 2 + App.SCREEN_WIDTH * r.nextDouble();
				double y = (x - 20) + ((x + 20) - (x - 20)) * r.nextDouble();
				Point point = new Point(x, y);
				points.add(point);
			}
		}
		return points;
	}
	
	public static List<Point> generatePositivePoints(int amount, boolean perfect) {
		List<Point> points = new ArrayList<>();
		Random r = new Random();
		if (perfect) {
			for (int i = 0; i < amount; i++) {
				double xy = App.SCREEN_WIDTH * r.nextDouble() / 2;
				Point point = new Point(xy, xy);
				points.add(point);
			}
		} else {
			for (int i = 0; i < amount; i++) {
				double x = App.SCREEN_WIDTH * r.nextDouble() / 2;
				double y = (x - 20) + ((x + 20) - (x - 20)) * r.nextDouble();
				Point point = new Point(x, y);
				points.add(point);
			}
		}
		return points;
	}
}
