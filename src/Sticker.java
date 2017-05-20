import java.util.ArrayList;

public class Sticker extends Face {
	
	//private int[] stickerNetLocation;
	private int face; // its a number
	private int[] stickerCoordinates;

	public Sticker(ArrayList<Vertex> vertices, int[] stickerCoordinates, int face){
		
		super(vertices);
		this.stickerCoordinates = stickerCoordinates;
		this.face = face;
		
	}
	
	public boolean intersectsFace(Ray ray){
		
		ArrayList<Vertex> v = Game.renderer.getVertices();
		
		return 
				
			ray.intersectsTriangle(
					
					v.get(getVertexIndexArray()[0]).getPos().toVector(), 
					v.get(getVertexIndexArray()[1]).getPos().toVector(),
					v.get(getVertexIndexArray()[2]).getPos().toVector()
					
			) ||
			
			ray.intersectsTriangle(
					
					v.get(getVertexIndexArray()[3]).getPos().toVector(),
					v.get(getVertexIndexArray()[4]).getPos().toVector(),
					v.get(getVertexIndexArray()[5]).getPos().toVector()
		
			);
		
	}
	
	public int[] getStickerCoordinates(){
		
		return stickerCoordinates;
		
	}
	
	public void setStickerCoordinates(int[] stickerCoordinates) {
		this.stickerCoordinates = stickerCoordinates;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	
}
