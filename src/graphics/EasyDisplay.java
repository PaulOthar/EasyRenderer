package graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import input.InputListener;

public class EasyDisplay extends Canvas implements Runnable  {
	private static final long serialVersionUID = 1L;

	public static final String FPS_FORMAT = "[0dFPS: %010d] [0xFPS:%08X]";
	
	private EasyScreen screen;
	
	//Thread
	private Thread thread;
	private boolean running;
	
	//Frame handler
	private int frameLimit;
	private double frameStep;
	private double deltaTime;
	private boolean showFPS;
	
	//Actual image
	private int width;
	private int height;
	
	private final InputListener input;
	
	public EasyDisplay(int width,int height,int framesPerSecond) {
		this.width = width;
		this.height = height;
		
		this.setPreferredSize(new Dimension(width,height));
		
		this.frameLimit = framesPerSecond;
		this.frameStep = 1000f/this.frameLimit;
		
		this.screen = null;
		
		this.input = new InputListener();
		
		this.addMouseMotionListener(input);
		this.addMouseListener(input);
		this.addKeyListener(input);
		this.addFocusListener(input);
	}
	
	public void setScreen(EasyScreen screen) {
		this.screen = screen;
		this.screen.setMousePosition(this.input.getMousePosition());
		this.screen.setMouseButtons(this.input.getMouseButtons());
		this.screen.setKeys(this.input.getKeys());
	}
	
	public void setFPSCounterEnabled(boolean enabled) {
		this.showFPS = enabled;
	}
	
	public void start() {
		if (this.running) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void stop() throws InterruptedException {
		if (!this.running) {
			return;
		}
		this.running = false;
		this.thread.join();
	}
	
	public void step() {
		if (this.running) {
			return;
		}
		this.run();
	}
	
	@Override
	public void run() {
		double currentTime = System.currentTimeMillis();
		double oldTime = currentTime;
		double oldSecond = currentTime;
		double toElapse = currentTime+this.frameStep;
		
		int fps = 0;
		int lastFPS = 0;
		
		do {
			currentTime = System.currentTimeMillis();
			
			fps++;
			
			if(currentTime-oldSecond >= 1000) {//if a second has elapsed
				lastFPS = fps;
				fps = 0;
				oldSecond = currentTime;
			}
			
			if(currentTime < toElapse) {//if 'frame' amount of time has not elapsed, skip
				continue;
			}
			this.deltaTime = (currentTime-oldTime)/1000;
			oldTime = currentTime;
			toElapse = currentTime+this.frameStep;
			
			//long tim = System.nanoTime();
			this.tick();
			this.render(lastFPS);
			//System.out.println(System.nanoTime()-tim);
		}while(this.running);
	}
	
	private void render(int fps) {
		BufferStrategy bufferstrategy = this.getBufferStrategy();
		if (bufferstrategy == null) {
			createBufferStrategy(3);
			return;
		}
		
		this.screen.render();
		
		Graphics g = bufferstrategy.getDrawGraphics();
		g.drawImage(this.screen.getImage(), 0, 0, this.width, this.height, null);
		
		if(this.showFPS) {
			g.setColor(Color.white);
			g.drawString(String.format(FPS_FORMAT, fps,fps), 0, 10);
		}
		
		g.dispose();
		bufferstrategy.show();
	}
	
	private void tick() {
		this.screen.setDeltaTime(this.deltaTime);
		this.screen.tick();
	}
}
