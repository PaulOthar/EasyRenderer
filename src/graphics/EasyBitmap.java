package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class EasyBitmap {
	protected final int width;
	protected final int height;
	protected final int[] pixels;
	protected final BufferedImage image;
	
	public EasyBitmap(int width,int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
		this.image = null;
	}
	
	public EasyBitmap(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.image = img;
	}
	
	public void drawBitmap(int width,int height,int[] pixels,int offX,int offY) {
		int bitmapX = 0;
		int bitmapY = 0;
		for(int Y = 0;Y < height;Y++) {
			bitmapY = Y+offY;
			for(int X = 0;X < width;X++) {
				bitmapX = X+offX;
				if(pixels[X+Y*width] == 0) {
					continue;
				}
				this.pixels[bitmapX+bitmapY*this.width] = pixels[X+Y*width];
			}
		}
	}
	
	public void clear() {
		for(int i = 0;i<this.pixels.length;i++) {
			this.pixels[i] = 0;
		}
	}
	
	//Getters

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public String toString() {
		return null;
	}
}
