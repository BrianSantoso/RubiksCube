import java.util.ArrayList;

public class Face implements CubeComponent {
	
	private int[] vertexIndexArray;	
	
	public Face(ArrayList<Vertex> vertices){
		
		vertexIndexArray = new int[vertices.size()];
		
		for(int i = 0; i < vertices.size(); i++){
			
			Game.renderer.addVertex(vertices.get(i));
			vertexIndexArray[i] = Game.renderer.getSize() - 1;
			
		}
		
		
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
