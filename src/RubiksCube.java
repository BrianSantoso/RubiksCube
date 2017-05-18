
public class RubiksCube {

	private final int n;
	
	private Vector pos;
	private Vector[] axis;
	
	private Cublet[] pieces;
	private int[][] stickerNet;
	private final int[] colorScheme;
	
	private boolean turning;
	
	
	public RubiksCube(int n){
		
		this.n = n;
		
		pos = Vector.ZERO;
		axis = new Vector[]{ Vector.RIGHT, Vector.UP, Vector.BACK }; // might replace forward with backwards and vice versa
		
		// 6n^2 - 12n + 8
		pieces = new Cublet[6 * n * n - 12 * n + 8];
		
		//stickerNet = new int[dimensions[1] * 2 + dimensions[0] * 2][dimensions[2] + dimensions[1] * 2];
		colorScheme = new int[]{
				
				0xff4500, // 0: Orange. Right Face.
				0xffffff, // 1: White. Up Face.
				0x0000ff, // 2: Blue. Front Face.
				0xff0000, // 3: Red. Left Face.
				0xffff00, // 4: Yellow. Down Face.
				0x00ff00, // 5: Green. Back Face.
				
		};
		
		
	}
	
	public void constructPieces(){
		
		
		
	}
	
	public void turnFace(int face, boolean clockwise){
		
		
		
	}
	
	public void rotateCube(EAngle eAngle){
		
		Matrix translation1 = Matrix.translationMatrix(pos.scale(-1));
		Matrix rotation = eAngle.rotationMatrix();
		Matrix translation2 = Matrix.translationMatrix(pos);
		
		Matrix transformation = translation2.multiply(rotation).multiply(translation1);
		
		for(Cublet c : pieces)
			c.applyTransformation(transformation);
		
	}
	
}
