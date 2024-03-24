package graphics;

import java.awt.image.BufferedImage;

import utils.Bitmap;

public abstract class EasyScreen extends Bitmap{
	protected double deltaTime;
	
	protected int[] mousePosition;
	protected boolean[] mouseButtons;
	protected boolean[] keys;
	
	public EasyScreen(int width,int height) {
		super(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
		this.deltaTime = 0;
	}
	
	public void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	public void setMousePosition(int[] mousePosition) {
		this.mousePosition = mousePosition;
	}

	public void setMouseButtons(boolean[] mouseButtons) {
		this.mouseButtons = mouseButtons;
	}

	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}
	
	public abstract void render();
	
	public abstract void tick();
}
