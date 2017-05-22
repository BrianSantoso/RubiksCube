import java.util.ArrayList;

public class Face implements CubeComponent {
	
	public static final Vector[] FACE = new Vector[]{
			
			new Vector(-0.5f, 0.0f, 0.5f),
			new Vector(0.5f, 0.0f, 0.5f),
			new Vector(0.5f, 0.0f, -0.5f),
			new Vector(-0.5f, 0.0f, -0.5f)
			
	};
	
	public static final int[] FACE_INDICES = new int[]{
			
			//bottom left
			3, 0, 1,
			//top right
			3, 1, 2
			
	};
	
	public static ArrayList<Vertex> constructFaceVertices(Vector pos, EAngle eAngle, float radius, float size, int color){
		

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		Matrix scale = Matrix.scalingMatrix(size);
		Matrix rotation = eAngle.rotationMatrix();
		Matrix translation = Matrix.translationMatrix(pos);
		Matrix translation2 = Matrix.translationMatrix(0, radius, 0);
		
		Matrix transformation = translation.multiply(rotation).multiply(translation2).multiply(scale);
		
		vertices.add(new Vertex(FACE[FACE_INDICES[0]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[1]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[2]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[3]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[4]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[5]], color));
		
		for(Vertex v : vertices){
			
			v.setPos(transformation.multiply(v.getPos()));
			
		}
		
		return vertices;
		
		
	}
	
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

	@Override
	public void setColor(int[] colorScheme) {
		
		for(int i = 0; i < vertexIndexArray.length; i++){
			
			Vertex v = Game.renderer.getVertex(vertexIndexArray[i]);
			v.setRGB(colorScheme[6]);
			
		}
		
	}
	
}
