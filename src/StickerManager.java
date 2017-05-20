
public class StickerManager {

	private StickerData[][][] net;
	private final int n;
	
	public StickerManager(int n){
		
		this.n = n;
		net = new StickerData[6][n][n];
		
		for(int face = 0; face < 6; face++){
			
			for(int x = 0; x < n; x++){
				for(int y = 0; y < n; y++){
				
					net[face][x][y] = new StickerData(face, new int[]{x, y});
					
				}
			}
			
		}
		
	}
	
	public StickerData[][][] getNet() {
		return net;
	}

	public void setNet(StickerData[][][] net) {
		this.net = net;
	}

	public int getN() {
		return n;
	}

	public int[] getStickerCoordinates(int face, int x, int y, int z){
		
		if(face == 0) return new int[]{y, n - z - 1};
		if(face == 1) return new int[]{x, n - z - 1};
		if(face == 2) return new int[]{x, y};
		if(face == 3) return new int[]{n - y - 1, n - z - 1};
		if(face == 4) return new int[]{x, n - z - 1};
		return new int[]{x, n - y - 1};
		
	}
	
	public StickerData getStickerData(Sticker sticker){
		
		int[] stickerCoordinates = sticker.getStickerCoordinates();
		return net[sticker.getFace()][stickerCoordinates[0]][stickerCoordinates[1]];
		
	}
	
}
