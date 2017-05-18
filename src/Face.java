
public class Face implements CubeComponent {
	
	private int[] vertexIndexArray;	
	
	public Face(){
		
		
		
	}
	
	@Override
	public void applyTransformation(Matrix transformation){
		
		for(int i = 0; i < vertexIndexArray.length; i++){
			
			Vertex v = Game.renderer.getVertex(vertexIndexArray[i]);
			Matrix newPos = transformation.multiply(v.getPos());
			v.setPos(newPos);
			
		}
		
	}
	
}
