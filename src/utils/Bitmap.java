package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap {
	protected final int width;
	protected final int height;
	protected final int[] pixels;
	protected final BufferedImage image;
	
	public Bitmap(int width,int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
		this.image = null;
	}
	
	public Bitmap(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.image = img;
	}
	
	public void setPixel(int color,int x,int y) {
		this.pixels[x+y*this.width] = color;
	}
	
	public void drawOutline(int color) {
		for(int i = 0;i<width;i++) {
			this.pixels[i] = color;
			this.pixels[i+(this.width-1)*this.height] = color;
		}
		for(int i = 0;i<height;i++) {
			this.pixels[i*this.width] = color;
			this.pixels[this.width-1+i*this.width] = color;
		}
	}
	
	public void drawCross(int color,int size) {
		int w = width/2;
		int h = height/2;
		for(int i = w-size;i<w+size;i++) {
			this.setPixel(color, i, h);
		}
		for(int i = h-size;i<h+size;i++) {
			this.setPixel(color, w, i);
		}
	}
	
	public void drawBitmap(Bitmap bitmap,int offX,int offY) {
		this.drawBitmapContents(bitmap.getWidth(), bitmap.getHeight(), bitmap.getPixels(), offX, offY);
	}
	
	public void drawBitmapContents(int width,int height,int[] pixels,int offX,int offY) {
		int bitmapX = 0;
		int bitmapY = 0;
		for(int Y = 0;Y < height;Y++) {
			bitmapY = Y+offY;
			if(bitmapY >= this.height) {
				return;
			}
			else if(bitmapY < 0) {
				continue;
			}
			for(int X = 0;X < width;X++) {
				bitmapX = X+offX;
				
				if(bitmapX >= this.width) {
					break;
				}
				else if(bitmapX < 0) {
					continue;
				}
				
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
