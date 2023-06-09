package graphicTools;

import graphics.EasyRender;

public class EasyDrafter {
	public static int[] buildFillPattern(int maxWidth,int maxHeight){
		int length = maxWidth*maxHeight;
		int[] output = new int[length];
		
		int i = 0;
		for(i = 0;i<length;i++) {
			output[i] = i;
		}
		
		return output;
	}
	
	public static int[] buildLinePattern(int maxWidth,int maxHeight,int x1,int y1,int x2,int y2){
		float X = x2-x1;
		float Y = y2-y1;
		
		float length = (float) Math.sqrt((X*X)+(Y*Y));
		float lengthPiece = 1/length;
		
		
		int Length = (int) ((1/lengthPiece)+(1-(1/lengthPiece)%1));
		
		int[] output = new int[Length];
		
		int xPix = 0;
		int yPix = 0;
		
		int i = 0;
		for(i = 0;i<Length;i++) {
			xPix = (int)Math.ceil(i * X) + x1;
			yPix = (int)Math.ceil(i * Y) + y1;
			
			if(xPix < 0 || xPix >= maxWidth) {
				continue;
			}
			if(yPix < 0 || yPix >= maxHeight) {
				continue;
			}
			
			output[i] = xPix+yPix*maxWidth;
		}
		
		return output;
	}
	
	public static int[] buildUnsortedTrianglePattern(int maxWidth,int maxHeight,int x1, int y1, int x2, int y2, int x3, int y3) {
		int 
		sx1 = x1,
		sy1 = y1,
		sx2 = x2,
		sy2 = y2,
		sx3 = x3,
		sy3 = y3,
		temp = 0;;
		
		if(sy2 < sy1) {
			temp = sx2;
			sx2 = sx1;
			sx1 = temp;
			
			temp = sy2;
			sy2 = sy1;
			sy1 = temp;
		}
		if(sy3<sy1) {
			temp = sx3;
			sx3 = sx1;
			sx1 = temp;
			
			temp = sy3;
			sy3 = sy1;
			sy1 = temp;
		}
		if(sy3<sy2) {
			temp = sx3;
			sx3 = sx2;
			sx2 = temp;
			
			temp = sy3;
			sy3 = sy2;
			sy2 = temp;
		}
		
		return EasyDrafter.buildTrianglePattern(maxWidth,maxHeight,sx1,sy1,sx2,sy2,sx3,sy3);
	}
	
	public static int[] buildTrianglePattern(int maxWidth,int maxHeight,int x1, int y1, int x2, int y2, int x3, int y3) {
		int dx12 = x2-x1;
		int dy12 = y2-y1;
		
		int dx13 = x3-x1;
		int dy13 = y3-y1;
		
		int dx23 = x3-x2;
		int dy23 = y3-y2;
		
		float xscale12 = 0;
		float xscale13 = 0;
		float xscale23 = 0;
		
		if(dy12 != 0) {
			xscale12 = ((float)dx12)/dy12;
		}
		
		if(dy13 != 0) {
			xscale13 = ((float)dx13)/dy13;
		}
		if(dy23 != 0) {
			xscale23 = ((float)dx23)/dy23;
		}
		
		float ax = 0;
		float bx = 0;
		float temp = 0;
		
		//Observation: it is just a temporary solution to my lack of triangular area knowledge
		int[] tempOutput = new int[maxWidth*maxHeight];
		int tempIndex = 0;
		
		if(dy12 != 0) {
			for(int y = y1;y<y2;y++) {
				if(y>maxHeight) {
					continue;
				}
				
				ax = xscale12*(y-y1);
				bx = xscale13*(y-y1);
				
				if(ax > bx) {
					temp = ax;
					ax = bx;
					bx = temp;
				}
				
				ax += x1;
				bx += x1;
				
				for(float x = ax;x<bx;x++) {
					if(x>maxWidth) {
						continue;
					}
					tempOutput[tempIndex++] = (int) ((x)+y*maxWidth);
				}
			}
		}
		if(dy23 != 0) {
			for(int y = y2;y<y3;y++) {
				if(y>maxHeight) {
					continue;
				}
				ax = xscale23*(y-y3);
				bx = xscale13*(y-y3);
				
				if(ax > bx) {
					temp = ax;
					ax = bx;
					bx = temp;
				}
				
				ax += x3;
				bx += x3;
				
				for(float x = ax;x<bx;x++) {
					if(x>maxWidth) {
						continue;
					}
					tempOutput[tempIndex++] = (int) ((x)+y*maxWidth);
				}
			}
		}
		
		//Observation: it is just a temporary solution to my lack of triangular area knowledge
		int[] output = new int[tempIndex];
		
		int i = 0;
		for(i = 0;i<tempIndex;i++) {
			output[i] = tempOutput[i];
		}
		
		return output;
	}
	
	public static int[] buildInfiniteTrianglePattern(int maxWidth,int maxHeight,float XperY1,float XperY2,int size,int x, int y) {
		int currentX = 0;
		int currentY = 0;
		float ax = 0;
		float bx = 0;
		float temp;
		
		int increment = 1;
		if(size < 0) {
			increment = -1;
		}
		
		//Observation: it is just a temporary solution to my lack of triangular area knowledge
		int[] tempOutput = new int[maxWidth*maxHeight];
		int tempIndex = 0;
		
		for(int i = 0;i!=size;i+=increment) {
			currentY = y+i;
			if(currentY > maxHeight) {
				continue;
			}
			
			ax = XperY1*i;
			bx = XperY2*i;
			
			ax += x;
			bx += x;
			
			if(ax>bx) {
				temp = ax;
				ax = bx;
				bx = temp;
			}
			
			for(currentX = (int) ax;currentX<bx;currentX++) {
				if(currentX > maxWidth) {
					continue;
				}
				tempOutput[tempIndex++] = currentX + currentY*maxWidth;
			}
		}
		//Observation: it is just a temporary solution to my lack of triangular area knowledge
		int[] output = new int[tempIndex];
				
		int i = 0;
		for(i = 0;i<tempIndex;i++) {
			output[i] = tempOutput[i];
		}
				
		return output;
	}
	
	public static void draw(EasyRender render,int[] pattern) {
		int i = 0;
		float scaleStep = 1f/pattern.length;
		for(i = 0;i<pattern.length;i++) {
			render.pixels[pattern[i]] = EasyColor.rgbScale(i*scaleStep);
		}
	}
	
	public static void draw(EasyRender render,int[] pattern,int color) {
		int i = 0;
		for(i = 0;i<pattern.length;i++) {
			render.pixels[pattern[i]] = color;
		}
	}
	
	
}
