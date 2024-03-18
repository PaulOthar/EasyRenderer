package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * EasyDisplay is an extention of the AWT Canvas.<br>
 * It is a thread by itself<br>
 * 
 * @author PaulOthar
 */
@SuppressWarnings("serial")
public abstract class EasyDisplay extends Canvas implements Runnable {
	public int width;
	public int height;

	private Thread thread;
	private boolean running = false;

	private BufferedImage img;

	private EasyScreen screen;

	private int[] pixels;

	private int FPS = 0;
	private int lastFPS = 0;

	private EasyDisplay(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		this.img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Initializes this EasyDisplay based on a existing EasyScreen.
	 * 
	 * @param screen
	 */
	public EasyDisplay(EasyScreen screen) {
		this(screen.width, screen.height);
		this.screen = screen;
	}

	// Implemented Methods

	/**
	 * Starts the thread execution.<br>
	 * Can only be stopped by the stop method.
	 */
	public void start() {
		if (this.running) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * Kills the thread execution.
	 * 
	 * @throws InterruptedException
	 */
	public void stop() throws InterruptedException {

		if (!this.running) {
			return;
		}
		this.running = false;
		this.thread.join();
	}

	/**
	 * Thread implemented method.<br>
	 * SHOULD NOT BE DIRECTLY CALLED!
	 */
	@Override
	public void run() {
		// Exclusive FPS Related
		double newTime = System.currentTimeMillis();
		double oldTime = newTime;
		double frameStep = newTime;
		// Exclusive FPS Related

		while (this.running) {
			// Exclusive FPS Related

			newTime = System.currentTimeMillis();

			FPS++;

			if (newTime - oldTime >= 1000) {
				lastFPS = FPS;
				FPS = 0;
				oldTime = newTime;
			}
			// Exclusive FPS Related
			
			if((newTime - frameStep) <= 16) {
				screen.render();
				continue;
			}
			frameStep = newTime;

			this.tick();
			this.render();
		}
	}

	/**
	 * Renders the current screen.
	 */
	private void render() {
		BufferStrategy bufferstrategy = this.getBufferStrategy();
		if (bufferstrategy == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render();

		int i = 0;
		for (i = 0; i < this.width * this.height; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bufferstrategy.getDrawGraphics();
		g.drawImage(img, 0, 0, this.width, this.height, null);
		// FPS related start
		g.setColor(Color.white);
		g.drawString("FPS : " + lastFPS, 0, 10);
		// FPS related end
		g.dispose();
		bufferstrategy.show();
	}

	// Methods to be implemented

	/**
	 * Ticks any user implemented functionality.<br>
	 * Such as ingame time.
	 */
	public abstract void tick();
}
