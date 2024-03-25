package graphicTools;

import utils.Bitmap;

/*
Draft = 4 steps of a single pixel;
	i0 = pixel 0
	i1 = pixel 0
	i2 = pixel 0
	i3 = pixel 0
	i4 = pixel 1
	i5 = pixel 1...
Bytes = XX_XX_YY_YY;
*/

public class IsometricDrafter {
	
	@Deprecated
	public static int[] draftFloor(int proportion) {
		int[] output = new int[(proportion*proportion)*4];
		
		int sh = proportion;//small height
		int sw = proportion;//small width
		
		int bh = (sh<<1);//big height
		int bw = (sw<<1)-1;//big width
		
		int xx = 0;
		int yy = 0;
		
		int ox = 0;
		int oy = 0;
		
		int outstep = 0;
		
		for(int y = 0;y<proportion;y++) {
			yy = y<<1;
			for(int x = 0;x<proportion;x++) {
				xx = x<<1;
				
				ox = (xx-yy)+bw;
				oy = ((xx+yy+1)>>1)+bh;
				
				output[outstep++] = ((ox+0)<<16)|(oy+0);
				output[outstep++] = ((ox+1)<<16)|(oy+0);
				output[outstep++] = ((ox+1)<<16)|(oy+1);
				output[outstep++] = ((ox+0)<<16)|(oy+1);
			}
		}
		
		return output;
	}
	
	@Deprecated
	public static Bitmap strokeDraft(Bitmap source,int[] draft,int draftProportions) {
		int w = source.getWidth();
		int multiWidth = w*4;
		int[] src = source.getPixels();
		
		Bitmap output = new Bitmap(multiWidth,multiWidth);
		int[] pixels = output.getPixels();
		
		int x = 0;
		int y = 0;
		
		int step = 0;
		int sX = 0;
		int sY = 0;
		for(int i = 0;i<draft.length;i++) {
			x = draft[i]>>16;
			y = draft[i]&0xffff;
			
			step = i/4;
			sY = step/draftProportions;
			sX = step-(sY*draftProportions);
			
			pixels[x+y*multiWidth] = src[sX + sY*w];
		}
		
		return output;
	}
	
	@Deprecated
	public static void drawDraft(Bitmap source,Bitmap target,int[] draft,int offsetX,int offsetY) {
		int[] src = source.getPixels();
		int[] trgt = target.getPixels();
		int targetWidth = target.getWidth();
		int targetHeight = target.getHeight();
		
		int x = 0;
		int y = 0;
		int indexTarget = 0;
		for(int i = 0;i<draft.length;i++) {
			x = draft[i]>>16;
			y = draft[i]&0xffff;
			
			x += offsetX;
			y += offsetY;
			
			if(x < 0 || x > targetWidth-1 || y < 0 || y > targetHeight-1) {
				continue;
			}
			
			indexTarget = x+y*targetWidth;
			
			trgt[indexTarget] = (src[i>>2]);
		}
	}
	
	@Deprecated
	public static void drawTileDraft(Bitmap source,Bitmap target,int[] draft,int x,int y,int z,int offsetX,int offsetY) {
		int proportion = source.getWidth();
		int X = (x-z)*(proportion<<1);
		int Y = (((x+y+z+1)*proportion));
		
		X += (target.getWidth()>>1);
		Y += (target.getHeight()>>1);
		
		X += offsetX;
		Y += offsetY;
		
		proportion = proportion<<2;
		
		if(X > target.getWidth() || Y > target.getHeight() || X < -proportion || Y < -proportion) {
			return;
		}
		
		drawDraft(source, target, draft, X, Y);
	}
	
	public static void drawTile(Bitmap tile,Bitmap target,int x,int y,int z,int offsetX,int offsetY) {
		int X = (x-z)*(tile.getWidth()>>1);
		int Y = ((x+y+z+1)*tile.getHeight())>>2;
		
		X += (target.getWidth()>>1);
		Y += (target.getHeight()>>1);
		
		X += offsetX;
		Y += offsetY;
		
		if(X > target.getWidth() || Y > target.getHeight() || X < -tile.getWidth() || Y < -tile.getHeight()) {
			return;
		}
		
		target.drawBitmap(tile, X, Y);
	}
	
	public static void drawTileMap(int[] sketch,Bitmap floor,Bitmap leftWall,Bitmap rightWall,Bitmap target,int offsetX,int offsetY) {
		int x = 0;
		int z = 0;
		for(int i = 0;i<sketch.length;i++) {
			x = sketch[i]>>16;
			z = (sketch[i]>>8)&0xff;
			drawTile(floor, target, x, 0, z, offsetX, offsetY);
			if((sketch[i]&IsometricMap.FLAG_LW) != 0) {
				drawTile(leftWall, target, x, 0, z, offsetX, offsetY);
			}
			if((sketch[i]&IsometricMap.FLAG_RW) != 0) {
				drawTile(rightWall, target, x, 0, z, offsetX, offsetY);
			}
		}
	}
}
