package graphics;

public abstract class EasyScreen extends EasyRender{
	
	public EasyScreen(int width,int height) {
		super(width,height);
	}

	public abstract void render();
}
