import java.util.ArrayList;

public class StickerManager {
	
	private StickerData[][][] staticNet;
//	private Sticker[][][] dynamicNet;
	private ArrayList<Sticker> dynamicNet;
	private Matrix[] turnCCMatrices;
	private final Matrix[] faceMatrices;
	private final int n;
	private final float t;
	
	public StickerManager(int n){
		
		this.n = n;
		t = (float) (n - 1) / 2;
		staticNet = new StickerData[6][n][n];
//		dynamicNet = new Sticker[6][n][n];
		dynamicNet = new ArrayList<Sticker>();
		
		for(int face = 0; face < 6; face++){
			
			for(int x = 0; x < n; x++){
				for(int y = 0; y < n; y++){
				
					int[] stickerCoordinates = new int[]{x, y};
					
					Matrix location = getLocation(face, stickerCoordinates);
					ArrayList<int[]> sectors = new ArrayList<int[]>();
					sectors.add(new int[]{0, (int) location.x()});
					sectors.add(new int[]{1, (int) location.y()});
					sectors.add(new int[]{2, (int) location.z()});
					
					staticNet[face][x][y] = new StickerData(face, stickerCoordinates, sectors);
					
				}
			}
			
		}
//		
//		Matrix translation1 = new Matrix(new float[][]{
//			
//			{1, 0, -t},
//			{0, 1, -t},
//			{0, 0, 1}
//			
//		});
//		
//		Matrix translation2 = new Matrix(new float[][]{
//			
//			{1, 0, t},
//			{0, 1, t},
//			{0, 0, 1}
//			
//		});
//		
//		turnCCMatrices = new Matrix[]{ 
//				
//				translation2.multiply(new Matrix(new float[][]{
//					
//					{0,  -1,  0},
//					{1,  0,  0},
//					{0,  0,  1}
//					
//				})).multiply(translation1),
//				
//				
//				translation2.multiply(new Matrix(new float[][]{
//					
//					{0,  1,  0},
//					{-1,  0,  0},
//					{0,  0,  1}
//					
//				})).multiply(translation1),
//				
//		};
//		
		turnCCMatrices = new Matrix[]{ 
				
				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90CC).multiply(Matrix.translationMatrix(0, -t, -t)),
				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90CC).multiply(Matrix.translationMatrix(-t, 0, -t)),
				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90CC).multiply(Matrix.translationMatrix(-t, -t, 0)),
				
				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90C).multiply(Matrix.translationMatrix(0, -t, -t)),
				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90C).multiply(Matrix.translationMatrix(-t, 0, -t)),
				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90C).multiply(Matrix.translationMatrix(-t, -t, 0)),
				
		};
		
		faceMatrices = new Matrix[]{
				
				new Vector(n - 1, t, t).toMatrix(),
				new Vector(t, n - 1, t).toMatrix(),
				new Vector(t, t, 0).toMatrix(),
				new Vector(0, t, t).toMatrix(),
				new Vector(t, 0, t).toMatrix(),
				new Vector(t, t, n - 1).toMatrix()
				
		};
		
//		turnCCMatrices = new Matrix[]{
//				
//			new Matrix(new float[][]{
//								
//				{0,  -1,  0},
//				{1,  0,  0},
//				{0,  0,  1}
//				
//			}),
//			
//			new Matrix(new float[][]{
//				
//				{0,  1,  0},
//				{-1,  0,  0},
//				{0,  0,  1}
//				
//			})
//				
//		};
		
	}
	
	public StickerData[][][] getStaticNet() {
		return staticNet;
	}

	public void setStaticNet(StickerData[][][] staticNet) {
		this.staticNet = staticNet;
	}

	public int getN() {
		return n;
	}

	public Matrix[] getTurnCCMatrices(){
		
		return turnCCMatrices;
		
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
	
	public int getFaceFromFaceMatrix(Matrix faceMatrix){
		
		for(int rep = 0; rep < 6; rep++){
			
			if(faceMatrix.toVector().equals(faceMatrices[rep].toVector())){
				
				return rep;
				
			}
			
		}
		
		return -1234;
		
	}
	
	public StickerData getStickerData(Sticker sticker){
		
		// maybe get rid
		int[] stickerCoordinates = sticker.getStickerCoordinates();
		return staticNet[sticker.getFace()][stickerCoordinates[0]][stickerCoordinates[1]];
		
		//should serasch thru dnyamic net for sticker
		
		
		
	}
	
	public void addSticker(Sticker sticker){
		
		dynamicNet.add(sticker);
		
//		dynamicNet[face][stickerCoordinates[0]][stickerCoordinates[1]] = sticker;
		
		
		
		
		//System.out.println(dynamicNet[face][stickerCoordinates[0]][stickerCoordinates[1]]);
//		if(face == 1 && stickerCoordinates[0] == 1 && stickerCoordinates[1] == 1){
//			
//			sticker.setFace(2);
//			sticker.setStickerCoordinates(new int[]{1, 1});
//			
			//rotateFaceStickers(4, true);
			
//		}
		
		//if(face == 4 && stickerCoordinates[0] == 2 && stickerCoordinates[1] == 0)
		//	System.out.println("test");
		
	}
	
	
	
	public void rotateStickerData(int[] sector, boolean cc){
		
		ArrayList<Sticker> sectorSelection = new ArrayList<Sticker>();
		
		for(Sticker s : dynamicNet){
			
			if(s.getLocation().m()[sector[0]][0] == sector[1])
				sectorSelection.add(s);
			
		}
		
		Matrix transformation = turnCCMatrices[sector[0] + (cc ? 0 : 3)];
		
		for(Sticker sticker : sectorSelection){
			
			Matrix location = sticker.getLocation();
			Matrix faceMatrix = faceMatrices[sticker.getFace()];
			
			Matrix newLocation = transformation.multiply(location);
			int newFace = getFaceFromFaceMatrix(transformation.multiply(faceMatrix));
			
			sticker.setFace(newFace);
			sticker.setLocation(newLocation);
			sticker.setStickerCoordinates(getStickerCoordinates(newFace, (int) newLocation.x(), (int) newLocation.y(), (int) newLocation.z()));
			
		}
		
		
		//System.out.println("stuff: " + sectorSelection);
		//System.out.println(sectorSelection.size());
		
		
	}
	
	public Matrix stickerCoordinatesToMatrix(int[] stickerCoordinates){
		
		return new Matrix(new float[][]{
			
			{stickerCoordinates[0]},
			{stickerCoordinates[1]},
			{1}
			
		});
		
	}
	
	public int[] matrixToStickerCoordinates(Matrix matrix){
		
		return new int[]{
			(int) matrix.x(),
			(int) matrix.y()
		};
		
	}
	
	public void rotateFaceStickers(int f, boolean cc){
		
		Sticker[][] face = dynamicNet[f];
		//System.out.println(dynamicNet[f][2][0]);
		
		Matrix transformation = turnCCMatrices[cc ? 0 : 1];
		
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				
				face[x][y].setFace(f);
				//System.out.println("f " + f);
				
				int[] coords = face[x][y].getStickerCoordinates();
				Matrix coordsMatrix = stickerCoordinatesToMatrix(coords);
				Matrix newCoordsMatrix = transformation.multiply(coordsMatrix);
				int[] newCoords = matrixToStickerCoordinates(newCoordsMatrix);
				
				System.out.println(newCoordsMatrix);
				
				face[x][y].setStickerCoordinates(newCoords);
				
				//face[x][y].setStickerCoordinates(matrixToStickerCoordinates(transformation.multiply(stickerCoordinatesToMatrix(face[x][y].getStickerCoordinates()))));
				
			}
		}
			
	}
	
//	public boolean isSolved(){
//		
//		for(int f = 0; f < 6; f++){
//			
//			if(!faceSolved(f))
//				return false;
//			
//		}
//		
//		return true;
//		
//	}
//	
//	public boolean faceSolved(int f){
//		
//		StickerData[][] face = net[f];
//		
//		int faceSticker = face[0][0].getFaceSticker();
//		
//		for(int x = 0; x < n; x++){
//			for(int y = 0; y < n; y++){
//				
//				if(face[x][y].getFaceSticker() != faceSticker)
//					return false;
//				
//			}
//		}
//		
//		return true;
//		
//	}
	
	
}
