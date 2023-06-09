package example;

import javax.swing.JFrame;

import graphicTools.EasyColor;
import graphicTools.EasyDrafter;
import graphics.EasyDisplay;
import graphics.EasyScreen;

public class Main {
	public static void main(String[] args) {
		EasyScreen screen = new EasyScreen(500,500) {
			public float step = 1f/1000;
			public float currentStep = 0;
			int[] fillPattern =  EasyDrafter.buildFillPattern(width, height);
			int[] trianglePattern = EasyDrafter.buildUnsortedTrianglePattern(width, height, width/2, height/6, width/4, height-height/3, width-width/4, height-height/3);
			@Override
			public void render() {
				currentStep += step;
				if(currentStep > 1) {
					currentStep = 0;
				}
				EasyDrafter.draw(this,fillPattern,EasyColor.cyclicRGBScale(currentStep));
				EasyDrafter.draw(this,trianglePattern,EasyColor.cyclicRGBScale(1-currentStep));
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
		window.setSize(500,500);
		window.setResizable(false);
		window.setVisible(true);
		
		display.start();
	}
}
