package examples;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import graphicTools.IsometricDrafter;
import graphicTools.IsometricMap;
import graphics.EasyDisplay;
import graphics.EasyScreen;
import utils.Bitmap;
import utils.FileManager;

public class IsometricTileTest {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 512;
	
	public static class CustomScreen extends EasyScreen{
		private ArrayList<Bitmap> assets;
		private int[] sketch;
		
		private double spacePosition = 0;
		private double spaceStep = 100;
		
		private int spaceX;
		private int spaceY;
		
		public CustomScreen(int width, int height) throws IOException {
			super(width, height);
			this.assets = new ArrayList<>();
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/strongf2.png")));
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/stronglw.png")));
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/strongrw.png")));
			
			this.sketch = IsometricMap.buildMap(FileManager.readFileText(FileManager.selectFile("./resources/map.txt")));
			
			//this.assets.get(1).drawOutline(0xff0000);
		}
		
		@Override
		public void render() {
			this.clear();
			/*
			
			int potencia = 1000;
			//10K choras muito
			for(int x = 0;x<potencia;x++) {
				for(int z = 0;z<potencia;z++) {
					IsometricDrafter.drawTile(this.assets.get(0),this, x, 0, z, this.spaceX, this.spaceY);
				}
			}
			
			IsometricDrafter.drawTile(this.assets.get(1),this, 0, 0, 0, this.spaceX, this.spaceY);
			//this.drawBitmap(this.assets.get(1), 32, 32);
			//this.drawBitmap(this.assets.get(2), 32, 32);
			
			*/
			
			for(int i = 0;i<1;i++) {
				IsometricDrafter.drawTileMap(this.sketch, this.assets.get(0), this.assets.get(1), this.assets.get(2), this, this.spaceX, this.spaceY);	
			}
			this.drawCross(0xff0000, 10);
		}

		@Override
		public void tick() {
			this.spacePosition = this.deltaTime*this.spaceStep;
			int roundStep = (int) Math.round(this.spacePosition);
			
			int xspeed = 0;
			int yspeed = 0;
			
			if(this.keys[KeyEvent.VK_SPACE]) {
				roundStep *= 5;
			}
			
			if(this.keys[KeyEvent.VK_D]) {
				xspeed += roundStep;
			}
			if(this.keys[KeyEvent.VK_A]) {
				xspeed -= roundStep;
			}
			if(this.keys[KeyEvent.VK_W]) {
				yspeed -= roundStep;
			}
			if(this.keys[KeyEvent.VK_S]) {
				yspeed += roundStep;
			}
			
			if(xspeed != 0 && yspeed != 0) {
				xspeed /= 2;
				yspeed /= 2;
			}
			
			this.spaceX += xspeed;
			this.spaceY += yspeed;
			
			if(this.spacePosition >= 1) {
				this.spacePosition = 0;
			}
			
			if(this.keys[KeyEvent.VK_F12]) {
				try {
					System.out.println("Printed");
					FileManager.saveBitmap(this, FileManager.createFile("resources/print.png"), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		CustomScreen screen;
		try {
			screen = new CustomScreen(WIDTH,HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		EasyDisplay display = new EasyDisplay(WIDTH,HEIGHT,60);
		
		display.addMouseListener(null);
		
		display.setScreen(screen);
		display.setFPSCounterEnabled(true);
		
		JFrame window = new JFrame("Isometric tiles example");
		window.add(display);
		window.setIconImage(ImageIO.read(new File("resources/box.png")));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		display.start();
	}
}
