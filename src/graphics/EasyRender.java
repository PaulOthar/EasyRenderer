package graphics;

import graphicTools.EasyColor;

/**
 * EasyRender is a simple bitmap with some draw routines.
 * @author PaulOthar
 */
public class EasyRender {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	/**
	 * Takes in a width and a height in pixels, to create a bitmap(Array of Integers).
	 * @param width in pixels
	 * @param height in pixels
	 */
	public EasyRender(int width,int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	/**
	 * Fills the bitmap with a static rgb gradiant image.<br>
	 * The gradiant flows from top to bottom.
	 */
	public void draw_filled_gradiant() {
		int i = 0;
		float scaleStep = 1f/this.pixels.length;
		for(i = 0;i<this.pixels.length;i++) {
			this.pixels[i] = EasyColor.rgbScale(i*scaleStep);
		}
	}
	
	/**
	 * Fills the bitmap with a moving rgb gradiant image, based on a <strong>normalized</strong> movement value.<br>
	 * The gradiant flows from top to bottom.<br><br>
	 * Positive movement means the image will flow upwards.<br>
	 * Negative movement means the image will flow downwards.
	 * @param movement step (0-1)
	 */
	public void draw_filled_stepped_gradiant(float movement) {
		int i = 0;
		float scaleStep = 1f/this.pixels.length;
		float scale = 0;
		for(i = 0;i<this.pixels.length;i++) {
			scale = i*scaleStep+movement;
			if(scale > 1) {
				scale -= 1;
			}
			else if(scale < 0) {
				scale += 1;
			}
			this.pixels[i] = EasyColor.cyclicRGBScale(scale);
		}
	}
	
	/**
	 * Fills the bitmap with a single color.
	 * @param color
	 */
	public void draw_filled_colored(int color) {
		int i = 0;
		for(i = 0;i<this.pixels.length;i++) {
			this.pixels[i] = color;
		}
	}
	
	/**
	 * Fills the specified pixels on the bitmap with a static rgb gradiant image.<br>
	 * The gradiant flows from top to bottom.
	 * 
	 * @param pattern
	 */
	public void draw_pattern_gradient(int[] pattern) {
		int i = 0;
		float scaleStep = 1f/pattern.length;
		for(i = 0;i<pattern.length;i++) {
			this.pixels[pattern[i]] = EasyColor.rgbScale(i*scaleStep);
		}
	}
	
	/**
	 * Fills the specified pixels on the bitmap with a moving rgb gradiant image, based on a <strong>normalized</strong> movement value.<br>
	 * The gradiant flows from top to bottom.<br><br>
	 * Positive movement means the image will flow upwards.<br>
	 * Negative movement means the image will flow downwards.
	 * 
	 * @param pattern
	 * @param movement step (0-1)
	 */
	public void draw_pattern_stepped_gradient(int[] pattern,float movement) {
		int i = 0;
		float scaleStep = 1f/pattern.length;
		float scale = 0;
		for(i = 0;i<pattern.length;i++) {
			scale = i*scaleStep+movement;
			if(scale > 1) {
				scale -= 1;
			}
			else if(scale < 0) {
				scale += 1;
			}
			this.pixels[pattern[i]] = EasyColor.cyclicRGBScale(scale);
		}
	}
	
	/**
	 * Fills the specified pixels on the bitmap with a single color.
	 * @param pattern
	 * @param color
	 */
	public void draw_pattern_colored(int[] pattern,int color) {
		int i = 0;
		for(i = 0;i<pattern.length;i++) {
			this.pixels[pattern[i]] = color;
		}
	}
	
	/**
	 * Fills the specified area with the contents of external bitmap
	 * @param render
	 * @param x (Origin)
	 * @param y (Origin)
	 */
	public void draw_bitmap(int[] pixels,int width,int height,int x,int y) {
		int bitmapY = 0;
		int bitmapX = 0;
		
		int Y = 0;
		int X = 0;
		for(Y = 0;Y<height;Y++) {
			bitmapY = Y+y;
			for(X = 0;X<width;X++) {
				bitmapX = X+x;
				this.pixels[bitmapX+bitmapY*this.width] = pixels[X+Y*width];
			}
		}
	}
	
	/**
	 * Fills the specified area with the contents of another Render
	 * @param render
	 * @param x (Origin)
	 * @param y (Origin)
	 */
	public void draw_render(EasyRender render,int x,int y) {
		this.draw_bitmap(render.pixels, render.width, render.height, x, y);
	}
}
