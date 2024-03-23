package graphics;

public class ANSIRenderer {
	//private static final String DUOPIXEL_FULL = "█";
	private static final String DUOPIXEL_TOP = "▀";
	//private static final String DUOPIXEL_BOTTOM = "▄";
	//private static final String DUOPIXEL_EMPTY = " ";
	//private static final String RESET_BACKGROUND = "\033[49m";
	private static final String DUOPIXEL_FOREGROUND_FORMAT = "\033[38;2;%d;%d;%dm";
	private static final String DUOPIXEL_BACKGROUND_FORMAT = "\033[48;2;%d;%d;%dm";
	private static final String NEW_LINE = "\033[0m \n";
	
	private static String getDuopixelChannelChanger(String format,int color) {
		return String.format(format, (color>>16)&0xff,(color>>8)&0xff,color&0xff);
	}
	
	public static String renderBitmap(int width,int height,int[] pixel) {
		String output = new String();
		
		int fixedHeight = height;
		if((height&0b1)>0) {//odd
			fixedHeight--;
		}
		
		int x = 0;
		int y = 0;
		
		int curr_up = 0;
		int curr_down = 0;
		
		int prev_up = 0;
		int prev_down = 0;
		
		String symb = DUOPIXEL_TOP;
		
		for(y = 0;y<(fixedHeight>>1);y++) {
			if(y > 0) {
				output += NEW_LINE;
			}
			x = 0;
			prev_up = pixel[x + (y<<1)*width];
			prev_down = pixel[x + ((y<<1)+1)*width];
			output += getDuopixelChannelChanger(DUOPIXEL_FOREGROUND_FORMAT,prev_up);
			output += getDuopixelChannelChanger(DUOPIXEL_BACKGROUND_FORMAT,prev_down);
			output += symb;
			for(x = 1;x<width;x++) {
				curr_up = pixel[x + (y<<1)*width];
				curr_down = pixel[x + ((y<<1)+1)*width];
				
				if(curr_up != prev_up) {
					output += getDuopixelChannelChanger(DUOPIXEL_FOREGROUND_FORMAT,curr_up);
				}
				if(curr_down != prev_down){
					output += getDuopixelChannelChanger(DUOPIXEL_BACKGROUND_FORMAT,curr_down);
				}
				prev_up = curr_up;
				prev_down = curr_down;
				output += symb;
			}
		}
		if(fixedHeight != height) {
			if(y > 0) {
				output += NEW_LINE;
			}
			x = 0;
			prev_up = pixel[x + (y<<1)*width];
			output += getDuopixelChannelChanger(DUOPIXEL_FOREGROUND_FORMAT,prev_up);
			output += symb;
			for(x = 1;x<width;x++) {
				curr_up = pixel[x + (y<<1)*width];
				if(curr_up != prev_up) {
					output += getDuopixelChannelChanger(DUOPIXEL_FOREGROUND_FORMAT,curr_up);
				}
				prev_up = curr_up;
				output += symb;
			}
		}
		output += NEW_LINE;
		
		return output;
	}
}
