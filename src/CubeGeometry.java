import java.util.ArrayList;

public class CubeGeometry {

//	public static final Vector[] CUBE_MESH = new Vector[]{
//		
//		new Vector()
//			
//		new Vertex(new Vector(x, y + st, z)),
//		new Vertex(new Vector(x, y, z)),
//		new Vertex(new Vector(x + st, y, z)),
//		new Vertex(new Vector(x + st, y + st, z)),
//		new Vertex(new Vector(x, y, z - st)),
//		new Vertex(new Vector(x + st, y, z - st)),
//		new Vertex(new Vector(x + st, y + st, z - st)),
//		new Vertex(new Vector(x, y + st, z - st))
//			
//	}
	
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
		
		// 1. have standard face position with center at 0, 0, 0
		// 2. scale face
		// 3. rotation so that it is aligned with normal
		//		find pitch, yaw, and roll of normal vector?
		// 4. translate to position
		
//		Vector up = Vector.UP;
//		Vector right = up.cross(normal);
//		up = normal.cross(right);
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
//		float normalMagnitude = normal.getMagnitude();
//		float alpha = (float) Math.acos(normal.x() / normalMagnitude);	// angle with x axis
//		float beta = (float) Math.acos(normal.y() / normalMagnitude);		// angle with y axis
//		float gamma = (float) Math.acos(normal.z() / normalMagnitude);	// angle with z axis
		
		Matrix scale = Matrix.scalingMatrix(size);
		
//		Matrix xAxisRotation = Matrix.xAxisRotationMatrix(eAngle.pitch());
//		Matrix yAxisRotation = Matrix.yAxisRotationMatrix(eAngle.yaw());
//		Matrix zAxisRotation = Matrix.zAxisRotationMatrix(eAngle.roll());
//		Matrix rotation = xAxisRotation.multiply(yAxisRotation).multiply(zAxisRotation);
		Matrix rotation = eAngle.rotationMatrix();
		
		Matrix translation = Matrix.translationMatrix(pos);
		Matrix translation2 = Matrix.translationMatrix(0, radius, 0);
		//Matrix translation2 = Matrix.translationMatrix(0, size/2, 0);
		//Matrix translation3 = Matrix.translationMatrix(0, -size/2, 0);
		
		//scale translation rotate
		
		//Matrix transformation = rotation.multiply(translation).multiply(scale);
		
		Matrix transformation = translation.multiply(rotation).multiply(translation2).multiply(scale);
		//Matrix transformation = scale.multiply(rotation).multiply(translation);
		
		vertices.add(new Vertex(FACE[FACE_INDICES[0]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[1]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[2]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[3]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[4]], color));
		vertices.add(new Vertex(FACE[FACE_INDICES[5]], color));
		
		for(Vertex v : vertices){
			
			v.setPos(transformation.multiply(v.getPos()));
			
		}
		
//		Vertex test = new Vertex(new Vector(1, 0, 0), 0xff0000);
//		test.setPos(transformation.multiply(test.getPos()));
//		
//		vertices.add(test);
		
		return vertices;
		
		
	}
	
	
	// FRONT
//	//bottom left
//	cv.add( corners[0] );
//	cv.add( corners[1] );
//	cv.add( corners[2] );
//	
//	//top right
//	cv.add( corners[0] );
//	cv.add( corners[2] );
//	cv.add( corners[3] );
//	
//	// RIGHT
//	//top left
//	cv.add( corners[3] );
//	cv.add( corners[2] );
//	cv.add( corners[6] );
//	
//	//bottom right
//	cv.add( corners[6] );
//	cv.add( corners[2] );
//	cv.add( corners[5] );
//	
//	// LEFT
//	//bottom left
//	cv.add( corners[7] );
//	cv.add( corners[4] );
//	cv.add( corners[1] );
//	
//	// top right
//	cv.add( corners[0] );
//	cv.add( corners[7] );
//	cv.add( corners[1] );
//	
//	//BACK
//	// top right
//	cv.add( corners[6] );
//	cv.add( corners[4] );
//	cv.add( corners[7] );
//	
//	// bottom left
//	cv.add( corners[6] );
//	cv.add( corners[5] );
//	cv.add( corners[4] );
//	
//	
//	//TOP
//	//bottom left
//	cv.add( corners[7] );
//	cv.add( corners[0] );
//	cv.add( corners[3] );
//	
//	//top right
//	cv.add( corners[7] );
//	cv.add( corners[3] );
//	cv.add( corners[6] );
//	
//	//BOTTOM
//	//bottom left
//	cv.add( corners[1] );
//	cv.add( corners[4] );
//	cv.add( corners[5] );
//	
//	//top right
//	cv.add( corners[1] );
//	cv.add( corners[5] );
//	cv.add( corners[2] );
	
}
