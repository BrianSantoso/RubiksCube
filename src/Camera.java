
public class Camera {

	public static final Matrix simplePerspectiveProjectionMatrix = new Matrix(new float[][]{
		
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, -1, 0},
		
	});
	
	public static final Matrix simpleOrthographicProjectionMatrix = new Matrix(new float[][]{
		
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, 0, 1},
		
	});
	
	private Vector pos;
	
	public Camera(){
		
		pos = new Vector(0, 0, 0);
		
		
	}
	
	public Matrix getCameraTransformMatrix(){
		
		Matrix translation = Matrix.translationMatrix(-pos.x(), -pos.y(), -pos.z());
		
		return translation;
		
	}
	
	public Matrix getProjectionMatrix(){
		
		return simplePerspectiveProjectionMatrix;
		//return simpleOrthographicProjectionMatrix;
		
		/*return new Matrix(new float[][]{
				
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, -1, 0}
				
		});*/
		
			
		
		
	}
	
}
