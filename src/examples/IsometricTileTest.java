package examples;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import graphics.EasyDisplay;
import graphics.EasyScreen;
import utils.Bitmap;
import utils.FileManager;

public class IsometricTileTest {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 512;
	
	public static class CustomScreen extends EasyScreen{
		private ArrayList<Bitmap> assets;
		
		private double spacePosition = 0;
		private double spaceStep = 100;
		
		private int spaceX;
		private int spaceY;
		
		public CustomScreen(int width, int height) throws IOException {
			super(width, height);
			this.assets = new ArrayList<>();
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/f.png")));
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/lw.png")));
			this.assets.add(FileManager.readBitmap(FileManager.selectFile("./resources/rw.png")));
			
			//this.assets.get(0).drawOutline(0xff0000);
			this.assets.get(1).drawOutline(0xff00);
			this.assets.get(2).drawOutline(0xff);
		}
		
		public void drawTile(Bitmap bitmap,int x,int y,int z,int offsetX,int offsetY) {
			int X = (x-z)*(bitmap.getWidth()>>1);
			int Y = ((x+y+z+1)*(bitmap.getHeight()))>>2;
			
			X += this.width>>1;
			Y += this.height>>1;
			
			X += offsetX;
			Y += offsetY;
			
			if(X > this.width || Y > this.height || X < -bitmap.getWidth() || Y < -bitmap.getHeight()) {
				return;
			}
			
			this.drawBitmap(bitmap, X, Y);
		}

		@Override
		public void render() {
			this.clear();
			//10K choras muito
			for(int x = 0;x<1000;x++) {
				for(int z = 0;z<1000;z++) {
					this.drawTile(this.assets.get(0), x, 0, z, this.spaceX, this.spaceY);
				}
			}
			
			this.drawTile(this.assets.get(1), 0, 0, 0, this.spaceX, this.spaceY);
			this.drawTile(this.assets.get(1), 0, -2, 0, this.spaceX, this.spaceY);
			//this.drawBitmap(this.assets.get(1), 32, 32);
			//this.drawBitmap(this.assets.get(2), 32, 32);
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
					FileManager.saveBitmap(this, FileManager.createFile("resources/print.png"), false);
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
