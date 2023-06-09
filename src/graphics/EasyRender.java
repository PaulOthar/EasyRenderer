package graphics;

public class EasyRender {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	public EasyRender(int width,int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	public void draw(EasyRender render,int xOffset,int yOffset) {
		int yPix = 0;
		int xPix = 0;
		
		int y = 0;
		int x = 0;
		for(y = 0;y<render.height;y++) {
			yPix = y+yOffset;
			for(x = 0;x<render.width;x++) {
				xPix = x+xOffset;
				
				pixels[xPix+yPix*this.width] = render.pixels[x+y*render.width];
			}
		}
	}
}
