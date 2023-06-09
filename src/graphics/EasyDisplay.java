package graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public abstract class EasyDisplay extends Canvas implements Runnable{
	public static int WIDTH;
	public static int HEIGHT;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage img;
	
	private EasyScreen screen;
	
	private int[] pixels;
	
	private EasyDisplay(int width,int heigth) {
		EasyDisplay.WIDTH = width;
		EasyDisplay.HEIGHT = heigth;
		this.img = new BufferedImage(width,heigth,BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	public EasyDisplay(EasyScreen screen) {
		this(screen.width,screen.height);
		this.screen = screen;
	}
	
	//Implemented Methods
	
	public void start(){
		if(this.running) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void stop() throws InterruptedException {
		if(!this.running) {
			return;
		}
		this.running = false;
		this.thread.join();
	}

	@Override
	public void run() {
		while(this.running) {
			this.tick();
			this.render();
		}
	}
	
	public void render() {
		BufferStrategy bufferstrategy = this.getBufferStrategy();
		if(bufferstrategy == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render();
		
		int i = 0;
		for(i = 0;i<EasyDisplay.WIDTH*EasyDisplay.HEIGHT;i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bufferstrategy.getDrawGraphics();
		g.drawImage(img, 0, 0, EasyDisplay.WIDTH, EasyDisplay.HEIGHT,null);
		g.dispose();
		bufferstrategy.show();
	}
	
	//Methods to be implemented
	
	public abstract void tick();
}
