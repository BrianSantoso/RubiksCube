import java.util.ArrayList;

public class Renderer {

	private Camera camera;
	private Raster raster;
	private ArrayList<Vertex> vertices;
	private boolean wireFrame;
	private boolean zBuffering;
	private boolean interpolateColors;
	
	public Renderer(Camera camera, Raster raster){
		
		this.camera = camera;
		this.raster = raster;
		
		wireFrame = false;
		zBuffering = true;
		interpolateColors = false;
		
		vertices = new ArrayList<Vertex>();
		
		// Dummy Cube
		
//		test(-6);
//		test(-5);
//		test(-4);
		
		// Z Buffer Debugging
		
//		Vector pos = new Vector(1.5f, 0, -10f);
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos.minus(new Vector(0.5f, 0.5f, 0)), new EAngle((float) Math.PI/2, (float) Math.PI/2, 0), 0.5f, 1, 0xff0000));
		
		// Test Cube
		
//		Vector pos = new Vector(1.5f, 0, -10f);
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
//		
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, 0), 1f, 0.8f, 0xff0000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI/2, 0, 0), 1f, 0.8f, 0x000ff00));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) (3 * Math.PI/2), 0, 0), 1f, 0.8f, 0x0000ff));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI, 0, 0), 1f, 0.8f, 0xffff00));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) Math.PI/2), 1f, 0.8f, 0x00ffff));
//		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) (3 * Math.PI/2)), 1f, 0.8f, 0xff00ff));
//		
		// EAngle Constants Test
		
//		Vector pos = new Vector(-5, 0, -10);
//		float radius = 0.5f;
//		float size = 0.5f;
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.RIGHT, radius, size, 0xff0000));
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.UP, radius, size, 0x00ff00));
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.BACK, radius, size, 0x0000ff));
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.LEFT, radius, size, 0xffff00));
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.DOWN, radius, size, 0x00ffff));
//		addVertices(CubeGeometry.constructFaceVertices(pos, EAngle.FORWARD, radius, size, 0xff00ff));
		
		
		
		
//		vertices.add(new Vertex(new Vector(0, 0, -3), 0xff0000));
//		vertices.add(new Vertex(new Vector(1, 0, -3), 0x00ff00));
//		vertices.add(new Vertex(new Vector(1, 1, -3), 0x0000ff));
//		
//		vertices.add(new Vertex(new Vector(0, 1, -3), 0xff0000));
//		vertices.add(new Vertex(new Vector(0, 0, -3), 0x00ff00));
//		vertices.add(new Vertex(new Vector(1, 1, -3), 0x0000ff));
		
		//render();
		
//		Vector a = vertices.get(0).getPos().toVector();
//		Vector b = vertices.get(1).getPos().toVector();
//		Vector c = vertices.get(2).getPos().toVector();
//		
//		float[] weights = Raster.barycentricWeights(a, b, c, new Vector(1.0f, 0f, 5));
//		
//		System.out.println(weights[0] + " , " + weights[1] + " , " + weights[2]);
		
	}
	
	public void render(){
		
		// <insert transformations here>
		
		
		ArrayList<Vector> projectedPoints = getProjectedPoints();
		
		//System.out.println(projectedPoints);
		
		for(int i = 0; i < projectedPoints.size(); i += 3){
			
			Vertex v0 = vertices.get(0 + i);
			Vertex v1 = vertices.get(1 + i);
			Vertex v2 = vertices.get(2 + i);
			
			Vector a = projectedPoints.get(0 + i);
			Vector b = projectedPoints.get(1 + i);
			Vector c = projectedPoints.get(2 + i);
			
			if(a.x() < -1 || a.x() > 1 || a.y() < -1 || a.y() > 1 || v0.getZ() > -1) continue;
			if(b.x() < -1 || b.x() > 1 || b.y() < -1 || b.y() > 1 || v1.getZ() > -1) continue;
			if(c.x() < -1 || c.x() > 1 || c.y() < -1 || c.y() > 1 || v2.getZ() > -1) continue;
			
			ArrayList<int[]> trianglePixels = Raster.getTrianglePixels(
					getScreenCoordinates(a),
					getScreenCoordinates(b),
					getScreenCoordinates(c),
					wireFrame
			);
			
			
			for(int[] p : trianglePixels){
				
				//System.out.println(p[0] + " , " + p[1]);
				
				//int color = (int) Raster.barycentricInterpolation(a, v0.getRGB(), b, v1.getRGB(), c, v2.getRGB(), new Vector(p[0], p[1], -1));
				//raster.tryToSetPixel(p[0], p[1], color, 0);
				
				//System.out.println(v0);
				
				float depth = 0;
				
				if(zBuffering){
					
					Vector point = new Vector(
						(float) p[0] / raster.getWidth(),
						(float) p[1] / raster.getHeight(),
						-1
					);
					
					depth = 1f / Raster.barycentricInterpolation2(
						a, 1f / v0.getZ(),
						b, 1f / v1.getZ(),
						c, 1f / v2.getZ(),
						point
					);
					
				}
				
				raster.tryToSetPixel(p[0], p[1], v0.getRGB(), depth);
				
			}
			
		}
		
	}
	

	public void addVertices(ArrayList<Vertex> newVertices){
		
		vertices.addAll(newVertices);
		
	}
	
	
	public int[] getScreenCoordinates(Vector a){
		
		return new int[]{
				
			(int) (a.x() * raster.getWidth()),
			(int) (a.y() * raster.getHeight())
				
		};
		
	}
	
	public ArrayList<Vector> getProjectedPoints(){
		
		ArrayList<Vector> projectedPoints = new ArrayList<Vector>();
		
		for(Vertex vertex : vertices){
			
			// just replace with division... later
			
			Matrix homogonized = camera.getProjectionMatrix().multiply(vertex.getPos());
			//float p = 1 / homogonized.m()[3][0];
			float p = 1 / homogonized.w();
			
			Matrix projected = new Matrix(new float[][]{
				
				{p, 0, 0, 0},
				{0, p, 0, 0},
				{0, 0, p, 0},
				{0, 0, 0, 1},
				
			}).multiply(homogonized);
			
			projectedPoints.add(projected.toVector());

			
		}
		
		return projectedPoints;
		
	}

	public Camera getCamera() {
		return camera;
	}

	public Raster getRaster() {
		return raster;
	}


	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public void addVertex(Vertex vertex){
		
		vertices.add(vertex);
		
	}
	
	public Vertex getVertex(int index){
		
		return vertices.get(index);
		
	}
	
	public void setVertex(int index, Vertex vertex){
		
		vertices.set(index, vertex);
		
	}
	
	public void toggleWireFrame() {
		
		wireFrame = !wireFrame;
		
	}
	
	public boolean isWireFrame() {
		return wireFrame;
	}

	public void setWireFrame(boolean wireFrame) {
		this.wireFrame = wireFrame;
	}

	public void toggleZBuffering(){
		
		zBuffering = !zBuffering;
		
	}
	
	public boolean iszBuffering() {
		return zBuffering;
	}

	public void setzBuffering(boolean zBuffering) {
		this.zBuffering = zBuffering;
	}

	public void test(float b){
		
		Vector pos = new Vector(1.5f, 0, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos1 = new Vector(0.5f, 0, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos1, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos2 = new Vector(-0.5f, 0, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos2, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos3 = new Vector(1.5f, -1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos3, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos4 = new Vector(0.5f, -1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos4, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos5 = new Vector(-0.5f, -1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos5, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos6 = new Vector(1.5f, 1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos6, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos7 = new Vector(0.5f, 1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos7, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
		Vector pos8 = new Vector(-0.5f, 1, -5f + b);
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) Math.PI/2, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) Math.PI, 0, 0), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, (float) Math.PI/2), 0.5f, 1, 0x000000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.5f, 1, 0x000000));
		
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, 0), 0.6f, 0.8f, 0xff0000));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) Math.PI/2, 0, 0), 0.6f, 0.8f, 0x000ff00));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) (3 * Math.PI/2), 0, 0), 0.6f, 0.8f, 0x0000ff));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle((float) Math.PI, 0, 0), 0.6f, 0.8f, 0xffff00));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, (float) Math.PI/2), 0.6f, 0.8f, 0x00ffff));
		addVertices(CubeGeometry.constructFaceVertices(pos8, new EAngle(0, 0, (float) (3 * Math.PI/2)), 0.6f, 0.8f, 0xff00ff));
		
	}

	public int getSize() {
		
		return vertices.size();
		
	}
	
}
