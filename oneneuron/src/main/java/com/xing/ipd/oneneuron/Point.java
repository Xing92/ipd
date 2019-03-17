package com.xing.ipd.oneneuron;

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
		this.circle = new Circle(x, y, size, this.color);
	}

	public Circle getCircle() {
		return circle;
	}
}
