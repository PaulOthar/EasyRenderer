package examples;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import graphics.EasyDisplay;
import graphics.EasyScreen;

public class DeltaTimeTest {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 512;
	
	public static class CustomScreen extends EasyScreen{
		double start = 0;
		double current = 0;
		
		public CustomScreen(int width, int height) {
			super(width, height);
			start = System.currentTimeMillis();
		}

		public double dotPosition = 0;
		public double dotStep = 100;
		
		public int dotX = 0;
		public int dotY = 0;
		
		public void stress() {
			for(int i = 0;i<100/1;i++) {
				for(int l = 0;l<this.pixels.length;l++) {
					this.pixels[l] = 0xff;
				}
			}
		}

		@Override
		public void render() {
			current = System.currentTimeMillis();
			this.stress();
			this.clear();
			int dotround = (int) Math.round(dotPosition);
			
			if(this.keys[KeyEvent.VK_SPACE]) {
				dotround *= 5;
			}
			
			int xspeed = 0;
			int yspeed = 0;
			
			if(this.keys[KeyEvent.VK_D]) {
				xspeed += dotround;
			}
			if(this.keys[KeyEvent.VK_A]) {
				xspeed -= dotround;
			}
			if(this.keys[KeyEvent.VK_W]) {
				yspeed -= dotround;
			}
			if(this.keys[KeyEvent.VK_S]) {
				yspeed += dotround;
			}
			
			xspeed *= 2;
			yspeed *= 2;
			
			if(xspeed != 0 && yspeed != 0) {
				xspeed /= 2;
				yspeed /= 2;
			}
			
			dotX += xspeed;
			dotY += yspeed;
			
			if(dotround >= 1) {
				dotPosition = 0;
			}
			
			int dotmath = dotX+dotY*this.width;
			
			this.pixels[dotmath] = 0xffffffff;
		}

		@Override
		public void tick() {
			dotPosition += dotStep*this.deltaTime;
		}
	}
	
	public static void main(String[] args) {
		CustomScreen screen = new CustomScreen(WIDTH/4,HEIGHT/4);
		EasyDisplay display = new EasyDisplay(WIDTH,HEIGHT,60);
		
		display.addMouseListener(null);
		
		display.setScreen(screen);
		display.setFPSCounterEnabled(true);
		
		JFrame window = new JFrame("Example");
		window.add(display);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		display.start();
	}
}
