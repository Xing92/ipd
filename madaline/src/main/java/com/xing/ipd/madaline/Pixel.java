package com.xing.ipd.madaline;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel {

	public static final int PIXEL_SIZE = 10;
	public static final int PIXELS_DIMENTION = 16;
	public static final int PIXEL_OFFSET_X = 150;
	public static final int PIXEL_OFFSET_Y = 150;

	private Color color;
	private double sizeX;
	private double sizeY;
	private double x;
	private double y;
	private Rectangle rectangle;
	private Integer isSelected = 0;

	public Pixel(double mouseX, double mouseY) {
		super();
		this.color = Color.GREY;
		this.sizeX = PIXELS_DIMENTION;
		this.sizeY = PIXELS_DIMENTION;
		this.x = PIXEL_OFFSET_X + mouseX * PIXEL_SIZE;
		this.y = PIXEL_OFFSET_Y + mouseY * PIXEL_SIZE;
		this.rectangle = new Rectangle(x, y, PIXEL_SIZE, PIXEL_SIZE);
		this.rectangle.setFill(this.color);
	}

	public Integer isSelected() {
		return isSelected;
	}

	public void setSelected(Integer isSelected) {
		this.isSelected = isSelected;
		if(isSelected == 0) setColor(Color.GREY);
		if(isSelected == 1) setColor(Color.RED);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		this.rectangle.setFill(color);
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Pixel [color=" + color + ", sizeX=" + sizeX + ", sizeY=" + sizeY + ", x=" + x + ", y=" + y + "]";
	}

}
