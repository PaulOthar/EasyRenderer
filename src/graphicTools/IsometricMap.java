package graphicTools;

import java.util.ArrayList;

public class IsometricMap {
	private static final char NEXT_LINE = '\n';
	private static final char NO_TILE = ' ';
	private static final char TILE = '#';
	
	public static final int FLAG_VALID = 0b1;
	public static final int FLAG_LW = 0b10;
	public static final int FLAG_RW = 0b100;
	
	public static int[] buildMap(String map) {
		ArrayList<ArrayList<Boolean>> builtMap = new ArrayList<>();
		String[] splitted = map.split(NEXT_LINE+"");
		
		char[] chars = null;
		for(int i = 0;i<splitted.length;i++) {
			chars = splitted[i].toCharArray();
			builtMap.add(new ArrayList<>());
			for(int l = 0;l<chars.length;l++) {
				builtMap.get(i).add(chars[l] == TILE);
			}
		}
		
		int width = 0;
		int height = builtMap.size();
		
		for(int i = 0;i<builtMap.size();i++) {
			if(builtMap.get(i).size() > width) {
				width = builtMap.get(i).size();
			}
		}
		
		for(int i = 0;i<builtMap.size();i++) {
			if(builtMap.get(i).size() < width) {
				int size = builtMap.get(i).size();
				for(int l = 0;l<width-size;l++) {
					builtMap.get(i).add(false);
				}
			}
		}
		
		int[] preoutput = new int[width*height];
		int actualSize = 0;
		
		for(int y = 0;y<height;y++) {
			int Y = y*width;
			for(int x = 0;x<width;x++) {
				if(!builtMap.get(y).get(x)) {
					continue;
				}
				preoutput[actualSize] = (x<<16)|(y<<8);
				if(y == 0 || (y > 0 && !builtMap.get(y-1).get(x))) {
					preoutput[actualSize] |= FLAG_RW;
				}
				if(x == 0 || (x > 0 && !builtMap.get(y).get(x-1))) {
					preoutput[actualSize] |= FLAG_LW;
				}
				actualSize++;
			}
		}
		
		int[] output = new int[actualSize];
		for(int i = 0;i<output.length;i++) {
			output[i] = preoutput[i];
		}
		
		return output;
	}
	
	public static void main(String[] args) {
		int[] data = buildMap("#####\n# # #\n#####\n\n# # #\n#####");
		for(int i = 0;i<data.length;i++) {
			System.out.println(String.format("X:%10d\tZ:%10d\tFlags:%8X", (data[i]>>16)&0xff,(data[i]>>8)&0xff,data[i]&0xff));
		}
	}
}
