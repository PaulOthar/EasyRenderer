package graphicTools;

public class EasyColor {
	private static final float STUPID_ONE_HALF = 1f/2;
	private static final float STUPID_ONE_THIRD = 1f/3;
	private static final float STUPID_TWO_THIRDS = 2f/3;
	
	/**
	 * Translate 3 Integers RGB into a singe integer RGB.<br>
	 * The 3 intake integers will be read with bitwise operations, to not overflow the 255 limit<br>
	 * Output:0x00RRGGBB
	 * @param r (0-255)
	 * @param g (0-255)
	 * @param b (0-255)
	 * @return integer rgb
	 */
	public static int rgb(int r,int g,int b) {
		return ((r&0xff)<<16)|((g&0xff)<<8)|(b&0xff);
	}
	
	/**
	 * Takes in a single byte of a integer, and turns into a full integer RGB<br>
	 * Byte Structure : 0bIIRRGGBB<br>
	 * I = 2 bit Intensity<br>
	 * R = 2 bit Red<br>
	 * G = 2 bit Green<br>
	 * B = 2 bit Blue<br>
	 * @param color rgbyte
	 * @return integer rgb
	 */
	public static int rgbyte(int color) {
		int red = (color & 0b0011_0000)<<12;
		int green = (color & 0b0000_1100)<<6;
		int blue = color & 0b0000_0011;
		
		int intensity = (color & 0b1100_0000)>>6;
		int product = 21*(intensity+1);
		
		red *= product;
		green *= product;
		blue *= product;
		
		return red|green|blue;
	}
	
	/**
	 * Generates a color based on a normalized value.<br>
	 * Flow: R->G->B
	 * @param normalizedScale (0-1)
	 * @return integer rgb
	 */
	public static int rgbScale(float normalizedScale) {
		int v1 = 0;
		int v2 = 0;
		
		if(normalizedScale >= STUPID_ONE_HALF) {//Green to blue
			normalizedScale -= STUPID_ONE_HALF;
			normalizedScale *= 2;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(0, v2, v1);
		}
		else {//Red to green
			normalizedScale *= 2;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(v2, v1, 0);
		}
	}
	
	/**
	 * Generates a color based on a normalized value.<br>
	 * Flow: R->G->B->R
	 * @param normalizedScale (0-1)
	 * @return integer rgb
	 */
	public static int cyclicRGBScale(float normalizedScale) {
		int v1 = 0;
		int v2 = 0;
		
		if(normalizedScale >= STUPID_TWO_THIRDS) {//Blue to red
			normalizedScale -= STUPID_TWO_THIRDS;
			normalizedScale *= 3;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(v1, 0, v2);
		}
		else if(normalizedScale >= STUPID_ONE_THIRD) {//Green to blue
			normalizedScale -= STUPID_ONE_THIRD;
			normalizedScale *= 3;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(0, v2, v1);
		}
		else {//Red to green
			normalizedScale *= 3;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(v2, v1, 0);
		}
	}
	
	/**
	 * Grabs the linear interpolation between two colors, based on a normalized value.
	 * @param scale (0-1)
	 * @param color1 
	 * @param color2
	 * @return integer rgb
	 */
	public static int gradient(float scale,int color1,int color2) {
		int r1 = (color1&0xff0000)>>16;
		int g1 = (color1&0xff00)>>8;
		int b1 = color1&0xff;
		
		int r2 = (color2&0xff0000)>>16;
		int g2 = (color2&0xff00)>>8;
		int b2 = color2&0xff;
		
		int R = (int) (r1 + (r2-r1)*scale);
		int G = (int) (g1 + (g2-g1)*scale);
		int B = (int) (b1 + (b2-b1)*scale);
		
		return (R<<16)|(G<<8)|B;
	}
	
	/**
	 * Grabs the linear interpolation between multiple colors, based on a normalized value.
	 * @param scale (0-1)
	 * @param colors 
	 * @return integer rgb
	 */
	public static int multiGradient(float scale,int[] colors) {
		float colorpiece = 1f/(colors.length-1);
		
		float border = (scale/colorpiece);
		float remainder = border%1;
		
		int color1 = (int)(border-remainder);
		int color2 = (int)(border+(1-remainder));
		
		return EasyColor.gradient(remainder,colors[color1],colors[color2]);
	}
}
