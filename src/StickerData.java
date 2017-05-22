import java.util.ArrayList;

public class StickerData {
	
	// The only thing that should change in a StickerData is the color faceSticker

	private final int[] axis;
	private final ArrayList<int[]> sectors; // a sector will have 2 parts: [x y or z, value]
	private final int[] stickerCoordinates;
	private final int face;
	
	//private int faceSticker; // AKA color
	
	public StickerData(int face, int[] stickerCoordinates, ArrayList<int[]> sectors){
		
		axis = new int[]{ (face + 1) % 3, (face + 2) % 3 };	// The 2 parallel axis to this Sticker
		
		this.sectors = sectors;
		this.stickerCoordinates = stickerCoordinates;
		this.face = face;
		
		
	}

//	public int getFaceSticker() {
//		return faceSticker;
//	}
//
//	public void setFaceSticker(int faceSticker) {
//		this.faceSticker = faceSticker;
//	}

	public int[] getAxis() {
		return axis;
	}

	public ArrayList<int[]> getSectors() {
		return sectors;
	}

	public int[] getStickerCoordinates() {
		return stickerCoordinates;
	}

	public int getFace() {
		return face;
	}
	
	
	
}
