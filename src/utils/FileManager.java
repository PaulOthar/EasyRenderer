package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileManager {
	public static File createFile(String path) throws IOException {
		if(path.isBlank()) {
			return null;
		}
		File output = new File(path);
		
		output.createNewFile();
		
		return output;
	}
	
	public static File createDirectory(String path) {
		if(path.isBlank()) {
			return null;
		}
		File output = new File(path);
		
		output.mkdir();
		
		return output;
	}
	
	public static File selectFile(String path) {
		if(path.isBlank()) {
			return null;
		}
		
		File output = new File(path);
		
		if(output.exists() && !output.isDirectory()) {
			return output;
		}
		return null;
	}
	
	public static File selectDirectory(String path) {
		if(path.isBlank()) {
			return null;
		}
		
		File output = new File(path);
		
		if(output.exists() && output.isDirectory()) {
			return output;
		}
		return null;
	}
	
	public static String[] readFileLines(File file) throws IOException {
		if(file == null || file.isDirectory()) {
			return null;
		}
		
		String[] output = null;
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		Object[] lines = br.lines().toArray();
		
		output = new String[lines.length];
		
		for(int i = 0;i<output.length;i++) {
			output[i] = (String) lines[i];
		}
		
		br.close();
		fr.close();
		
		return output;
	}
	
	public static void overwriteFile(File file,String content) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(content);
		
		bw.close();
		fw.close();
	}
	
	public static BufferedImage readImage(File file) throws IOException{
		BufferedImage loaded = ImageIO.read(file);
		BufferedImage formatted = new BufferedImage(loaded.getWidth(),loaded.getHeight(),BufferedImage.TYPE_INT_ARGB);
		formatted.getGraphics().drawImage(loaded, 0, 0,null);
		return formatted;
	}
	
	public static Bitmap readBitmap(File file) throws IOException {
		BufferedImage loaded = ImageIO.read(file);
		BufferedImage formatted = new BufferedImage(loaded.getWidth(),loaded.getHeight(),BufferedImage.TYPE_INT_ARGB);
		formatted.getGraphics().drawImage(loaded, 0, 0,null);
		
		return new Bitmap(formatted);
	}
	
	public static boolean saveBitmap(Bitmap bitmap,File file,boolean alpha) throws IOException {
		int flag = BufferedImage.TYPE_INT_RGB;
		if(alpha) {
			flag = BufferedImage.TYPE_INT_ARGB;
		}
		BufferedImage img = new BufferedImage(bitmap.getWidth(), bitmap.getHeight(), flag);
		int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		int[] bmPix = bitmap.getPixels();
		
		for(int i = 0;i<bmPix.length;i++) {
			pixels[i] = bmPix[i];
		}
		
		int lif = file.getName().lastIndexOf('.');
		
		String ext = file.getName().substring(lif+1);
		return ImageIO.write(img,ext,file);
	}
}
