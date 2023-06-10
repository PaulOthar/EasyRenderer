package graphics;

/**
 * EasyScreen is an extention to EasyRender, that needs to implement a render method.<br>
 * @author PaulOthar
 */
public abstract class EasyScreen extends EasyRender{
	
	/**
	 * Straight foward superclass call.<br>
	 * Takes in a width and a height in pixels, to create a bitmap(Array of Integers).
	 * @param width in pixels
	 * @param height in pixels
	 */
	public EasyScreen(int width,int height) {
		super(width,height);
	}

	/**
	 * This method will be called each cycle of the graphics update.<br>
	 * So this must be implemented containing everything that will be done to render the desired image.
	 */
	public abstract void render();
}
