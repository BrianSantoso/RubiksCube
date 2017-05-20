import java.util.ArrayList;

public class Face implements CubeComponent {
	
	private int[] vertexIndexArray;	
	
	public Face(ArrayList<Vertex> vertices){
		
		vertexIndexArray = new int[vertices.size()];
		
		for(int i = 0; i < vertices.size(); i++){
			
			vertexIndexArray[i] = Game.renderer.getVertices().size();
			//System.out.println(vertexIndexArray[i]);
			Game.renderer.addVertex(vertices.get(i));
			//vertexIndexArray[i] = Game.renderer.getSize() - 1;
			
		}
		
		
	}
	
	public int[] getVertexIndexArray(){
		
		return vertexIndexArray;
		
	}
	
	@Override
	public void applyTransformation(Matrix transformation){
		
		for(int i = 0; i < vertexIndexArray.length; i++){
			
			Vertex v = Game.renderer.getVertex(vertexIndexArray[i]);
			Matrix newPos = transformation.multiply(v.getPos());
			v.setPos(newPos);
			
		}
		
	}
	
	public void rotate(Vector pivot, Vector axis, float radians){
		
		for(int i = 0; i < vertexIndexArray.length; i++){
			
			Vertex v = Game.renderer.getVertex(vertexIndexArray[i]);
			Vector p0 = v.getPos().toVector();
			
			Vector translated1 = p0.minus(pivot);
			Vector rotated = translated1.rotateAround(axis, radians);
			Vector translated2 = rotated.plus(pivot);
			
			v.setPos(translated2);
			
			
			
			//v.setPos(newPos);
			
		}
		
	}
	
}
