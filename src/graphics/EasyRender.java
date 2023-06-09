package graphics;

import graphicTools.EasyColor;

public class EasyRender {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	public EasyRender(int width,int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	public void draw() {
		int i = 0;
		float scaleStep = 1f/this.pixels.length;
		for(i = 0;i<this.pixels.length;i++) {
			this.pixels[i] = EasyColor.rgbScale(i*scaleStep);
		}
	}
	
	public void draw(float movement) {
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
	
	public void draw(int color) {
		int i = 0;
		for(i = 0;i<this.pixels.length;i++) {
			this.pixels[i] = color;
		}
	}
	
	public void draw(int[] pattern) {
		int i = 0;
		float scaleStep = 1f/pattern.length;
		for(i = 0;i<pattern.length;i++) {
			this.pixels[pattern[i]] = EasyColor.rgbScale(i*scaleStep);
		}
	}
	
	public void draw(int[] pattern,float movement) {
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
	
	public void draw(int[] pattern,int color) {
		int i = 0;
		for(i = 0;i<pattern.length;i++) {
			this.pixels[pattern[i]] = color;
		}
	}
	
	public void draw(EasyRender render,int xOrigin,int yOrigin) {
		int yPix = 0;
		int xPix = 0;
		
		int y = 0;
		int x = 0;
		for(y = 0;y<render.height;y++) {
			yPix = y+yOrigin;
			for(x = 0;x<render.width;x++) {
				xPix = x+xOrigin;
				
				pixels[xPix+yPix*this.width] = render.pixels[x+y*render.width];
			}
		}
	}
}
