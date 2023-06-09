package graphicTools;

public class EasyColor {
	public static int rgb(int r,int g,int b) {
		// 0x00_rr_gg_bb
		int output = 0;
		
		//Eliminate extra bytes
		r = r&0xff;
		g = g&0xff;
		b = b&0xff;
		
		output += r*0x10000;
		output += g*0x100;
		output += b;
		
		return output;
	}
	
	public static int rgbyte(int color) {
		int intensity = color & 0b1100_0000;
		int red = color & 0b0011_0000;
		int green = color & 0b0000_1100;
		int blue = color & 0b0000_0011;
		
		intensity /= 0b0100_0000;
		
		red *= 0x1000;
		green *= 0x40;
		
		red = (red*21) + (red*intensity*21);
		green = (green*21)+(green*intensity*21);
		blue = (blue*21)+(blue*intensity*21);
		
		return red+green+blue;
	}
	
	public static int rgbytePallete(int pallete,int indexes) {
		int firstGrabber = (int) Math.pow(2, (indexes & 0b11)*8);
		int secondGrabber = (int) Math.pow(2, ((indexes & 0b1100)/4)*8);
		
		int firstHalf = (pallete & (firstGrabber * 0b1111))/firstGrabber;
		int secondHalf = (pallete & (secondGrabber * 0b1111_0000))/secondGrabber;
		
		return rgbyte(firstHalf+secondHalf);
	}
	
	public static int rgbScale(float normalizedScale) {
		int v1 = 0;
		int v2 = 0;
		
		if(normalizedScale >= 1f/2) {//Green to blue
			normalizedScale -= 1f/2;
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
	
	public static int cyclicRGBScale(float normalizedScale) {
		int v1 = 0;
		int v2 = 0;
		
		float br = 2f/3;
		float gb = 1f/3;
		
		if(normalizedScale >= br) {//Blue to red
			normalizedScale -= br;
			normalizedScale *= 3;
			
			v1 = (int) (255*normalizedScale);
			v2 = 255-v1;
			
			return EasyColor.rgb(v1, 0, v2);
		}
		else if(normalizedScale >= gb) {//Green to blue
			normalizedScale -= gb;
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
	
	public static int gradient(float scale,int color1,int color2) {
		int r1 = (color1&0xff0000)/0x10000;
		int g1 = (color1&0xff00)/0x100;
		int b1 = color1&0xff;
		
		int r2 = (color2&0xff0000)/0x10000;
		int g2 = (color2&0xff00)/0x100;
		int b2 = color2&0xff;
		
		int R = (int) (r1 + (r2-r1)*scale);
		int G = (int) (g1 + (g2-g1)*scale);
		int B = (int) (b1 + (b2-b1)*scale);
		
		return (R*0x10000)+(G*0x100)+B;
	}
	
	public static int multiGradient(float scale,int[] colors) {
		float colorpiece = 1f/(colors.length-1);
		
		float border = (scale/colorpiece);
		float remainder = border%1;
		
		int color1 = (int)(border-remainder);
		int color2 = (int)(border+(1-remainder));
		
		return EasyColor.gradient(remainder,colors[color1],colors[color2]);
	}
}
