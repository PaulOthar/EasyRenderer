package examples;

import javax.swing.JFrame;

import graphics2.EasyDisplay;
import graphics2.EasyScreen;

public class Graphics2Test {
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
		public double dotStep = 50;
		
		public void stress() {
			for(int i = 0;i<10000/1;i++) {
				for(int l = 0;l<this.pixels.length;l++) {
					this.pixels[l] = 0xff;
				}
			}
		}

		@Override
		public void render() {
			current = System.currentTimeMillis();
			this.clear();
			int dotround = (int) Math.round(dotPosition);
			int dotmath = (dotround + dotround*this.width);
			if(dotmath >= this.pixels.length) {
				dotPosition = 0;
				dotmath = 0;
				System.out.println((current-start)/1000);
				start = current;
			}
			this.pixels[dotmath] = 0xffffffff;
		}

		@Override
		public void tick() {
			dotPosition += dotStep*this.deltaTime;
		}
	}
	
	public static void main(String[] args) {
		CustomScreen screen = new CustomScreen(WIDTH,HEIGHT);
		EasyDisplay display = new EasyDisplay(WIDTH,HEIGHT,60);
		display.setScreen(screen);
		display.setFPSCounterEnabled(true);
		
		JFrame window = new JFrame("Example");
		window.add(display);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WIDTH,HEIGHT);
		window.setResizable(false);
		window.setVisible(true);
		
		display.start();
	}
}
