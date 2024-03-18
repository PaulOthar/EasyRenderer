package examples;

import java.io.IOException;

import javax.swing.JFrame;

import graphicTools.EasyDrafter;
import graphics.EasyDisplay;
import graphics.EasyRender;
import graphics.EasyScreen;

public class Main {
	public static void main(String[] args) {
		int width = 1024;
		int height = 512;
		
		EasyScreen screen = new EasyScreen(width,height) {
			public float step = 1f/1000;
			public float currentStep = 0;
			int[] trianglePattern = EasyDrafter.buildUnsortedTrianglePattern(width, height, width/2, height/6, width/4, height-height/3, width-width/4, height-height/3);
			
			EasyRender bricked = null;
			int count = 0;
			@Override
			public void render() {
				/*
				currentStep += step;
				if(currentStep > 1) {
					currentStep = 0;
				}
				this.draw_filled_stepped_gradiant(currentStep);
				this.draw_pattern_stepped_gradient(trianglePattern,-currentStep);
				*/
				
				if(bricked == null) {
					try {
						bricked = EasyRender.readImage("resources/brickedFloor.png");
					} catch (IOException e) {
						e.printStackTrace();
					};
				}
				
				if(count+bricked.pixels.length >= this.pixels.length) {
					count = 0;
					
				}
				else {
					count ++;
				}
				this.draw_render(bricked, count, 10);
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
