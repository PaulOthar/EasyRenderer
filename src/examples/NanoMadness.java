package examples;

public class NanoMadness {
	public static void main(String[] args) {
		for(int i = 0;i<10000;i++) {
			long tim = System.nanoTime();
			int x = 35;
			int y = 78;
			for(int l = 0;l<(1000*1000);l++) {
				x *= y;
				y = y<<1;
			}
			System.out.println(System.nanoTime()-tim);
		}
	}
}
