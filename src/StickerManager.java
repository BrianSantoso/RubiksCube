import java.util.ArrayList;

public class StickerManager {

	private StickerData[][][] net;
	private final int n;
	
	public StickerManager(int n){
		
		this.n = n;
		net = new StickerData[6][n][n];
		
		for(int face = 0; face < 6; face++){
			
			for(int x = 0; x < n; x++){
				for(int y = 0; y < n; y++){
				
					int[] stickerCoordinates = new int[]{x, y};
					
					Matrix location = getLocation(face, stickerCoordinates);
					ArrayList<int[]> sectors = new ArrayList<int[]>();
					sectors.add(new int[]{0, (int) location.x()});
					sectors.add(new int[]{1, (int) location.y()});
					sectors.add(new int[]{2, (int) location.z()});
					
					net[face][x][y] = new StickerData(face, stickerCoordinates, sectors);
					
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
	
	public Matrix getLocation(int face, int[] stickerCoordinates){
		
		int x = stickerCoordinates[0];
		int y = stickerCoordinates[1];
		
		if(face == 0) return new Vector(n - 1, x, n - y - 1).toMatrix();
		if(face == 1) return new Vector(x, n - 1, n - y - 1).toMatrix();
		if(face == 2) return new Vector(x, y, 0).toMatrix();
		if(face == 3) return new Vector(0, n - x - 1, n - y - 1).toMatrix();
		if(face == 4) return new Vector(x, 0, n - y - 1).toMatrix();
		return new Vector(x, n - y - 1, n - 1).toMatrix();
		
	}
	
	public StickerData getStickerData(Sticker sticker){
		
		int[] stickerCoordinates = sticker.getStickerCoordinates();
		return net[sticker.getFace()][stickerCoordinates[0]][stickerCoordinates[1]];
		
	}
	
	public void rotateStickerData(int[] sector, boolean cc){
		
		
		
	}
	
	public boolean isSolved(){
		
		for(int f = 0; f < 6; f++){
			
			if(!faceSolved(f))
				return false;
			
		}
		
		return true;
		
	}
	
	public boolean faceSolved(int f){
		
		StickerData[][] face = net[f];
		
		int faceSticker = face[0][0].getFaceSticker();
		
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				
				if(face[x][y].getFaceSticker() != faceSticker)
					return false;
				
			}
		}
		
		return true;
		
	}
	
	
}
