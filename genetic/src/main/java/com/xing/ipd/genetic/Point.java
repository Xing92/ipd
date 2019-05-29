package com.xing.ipd.genetic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point {

	double x;
	double y;
	public double size;
	public Color color;
	public Circle circle;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.size = 2;
		this.color = Color.RED;
		this.circle = new Circle(x * App.SCREEN_WIDTH / App.AXIS_X_SIZE,
				-y * App.SCREEN_HEIGHT / App.AXIS_Y_SIZE + App.SCREEN_HEIGHT / 2, size, this.color);
	}

	public void setSize(double size) {
		circle.setScaleX(size);
		circle.setScaleY(size);
	}
	
	public void setColor(Color color) {
		circle.setFill(color);
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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
