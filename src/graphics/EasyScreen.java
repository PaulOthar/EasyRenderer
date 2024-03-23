package graphics;

import java.awt.image.BufferedImage;

public abstract class EasyScreen extends EasyBitmap{
	protected double deltaTime;
	
	public EasyScreen(int width,int height) {
		super(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
		this.deltaTime = 0;
	}
	
	public void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	public abstract void render();
	
	public abstract void tick();
}
