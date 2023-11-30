package examples;

import javax.swing.JFrame;

import graphicTools.EasyDrafter;
import graphics.EasyDisplay;
import graphics.EasyScreen;

public class Main {
	public static void main(String[] args) {
		int width = 500;
		int height = 500;
		
		EasyScreen screen = new EasyScreen(width,height) {
			public float step = 1f/1000;
			public float currentStep = 0;
			int[] trianglePattern = EasyDrafter.buildUnsortedTrianglePattern(width, height, width/2, height/6, width/4, height-height/3, width-width/4, height-height/3);
			@Override
			public void render() {
				currentStep += step;
				if(currentStep > 1) {
					currentStep = 0;
				}
				this.draw(currentStep);
				this.draw(trianglePattern,-currentStep);
			}
		};
		EasyDisplay display = new EasyDisplay(screen) {
			private static final long serialVersionUID = 1L;

			@Override
			public void tick() {}
		};
		
		JFrame window = new JFrame("Example");
		window.add(display);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(width,height);
		window.setResizable(false);
		window.setVisible(true);
		
		display.start();
	}
}
