package com.xing.ipd.madaline;

import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingHelper {
	private List<Pixel> pixels;

	public DrawingHelper(List<Pixel> pixels) {
		this.pixels = pixels;
	}

	public Pixel getPixelFromMouse(double mouseX, double mouseY) {
		for (Pixel pixel : pixels) {
			if (isInsideRange(pixel, mouseX, mouseY)) {
				return pixel;
			}
		}
		return null;
	}

	private boolean isInsideRange(Pixel pixel, double mouseX, double mouseY) {
		if (mouseX >= pixel.getX() && mouseX <= pixel.getX() + pixel.getSizeX() && mouseY >= pixel.getY()
				&& mouseY <= pixel.getY() + pixel.getSizeY()) {
			return true;
		}
		return false;
	}

	public void clearPixels() {
		pixels.forEach(pixel -> pixel.setSelected(0));
	}



}
