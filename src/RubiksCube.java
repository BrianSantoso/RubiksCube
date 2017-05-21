import java.util.ArrayList;
import java.util.LinkedList;

public class RubiksCube implements CubeComponent{

	private final int n;	// n x n x n dimensions
	private final float size; // kind of like a scale
	
	private Vector pos;
	private Vector[] axis;
	
	private Cublet[] pieces;
	private StickerManager stickerManager;
	//private int[][] stickerNet;
	
	
	
	private int color;
	private int[] colorScheme;
	
	private final Matrix[] turnCCMatrices;
	
	private LinkedList<Move> moves;
	
	private boolean turning;
	
	
	public RubiksCube(int n, float size){
		
		this.n = Math.max(n, 1);
		this.size = size;
		
		float z = new Vector(1, 1, 1).scale((float)(this.n - 1)/2).getMagnitude();
		//pos = new Vector(0, 0, -15 -(float)(n - 1)/2);
		
		pos = new Vector(0, 0, -12.7729f - z);
		axis = new Vector[]{ Vector.RIGHT, Vector.UP, Vector.BACK }; // might replace forward with backwards and vice versa
		
		color = 0x080808;
		colorScheme = new int[]{
				
				0xff6600, // 0: Orange. Right Face.
				0xffffff, // 1: White. Up Face.
				0x1e90ff, // 2: Blue. Front Face.
				0xfe0000, // 3: Red. Left Face.
				0xffff00, // 4: Yellow. Down Face.
				0x00ff00, // 5: Green. Back Face.
				
		};
		
//		color = 0xffffff;
//		colorScheme = new int[]{
//				
//				0, // 0: Orange. Right Face.
//				1, // 1: White. Up Face.
//				2, // 2: Blue. Front Face.
//				3, // 3: Red. Left Face.
//				4, // 4: Yellow. Down Face.
//				5, // 5: Green. Back Face.
//				
//		};
		
		stickerManager = new StickerManager(this.n);
		
		// 6n^2 - 12n + 8
		int len = this.n == 1 ? 1 : 6 * this.n * this.n - 12 * this.n + 8;
		pieces = new Cublet[len];
		constructPieces();
		
		//rotateCube(new EAngle(0.01f, 0.01f, 0));
		
		moves = new LinkedList<Move>();
		
		float t = (float) (n - 1) / 2;
		turnCCMatrices = new Matrix[]{ 
				
				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90CC).multiply(Matrix.translationMatrix(0, -t, -t)),
				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90CC).multiply(Matrix.translationMatrix(-t, 0, -t)),
				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90CC).multiply(Matrix.translationMatrix(-t, -t, 0)),
				
				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90C).multiply(Matrix.translationMatrix(0, -t, -t)),
				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90C).multiply(Matrix.translationMatrix(-t, 0, -t)),
				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90C).multiply(Matrix.translationMatrix(-t, -t, 0)),
				
		};
		
		
		rotateCube(new EAngle((float) Math.PI/4, (float) Math.PI/4, 0));
		
		//moveCube(new Vector(-3f, 0, 0));
		
//		Matrix translation1 = Matrix.translationMatrix(pos.scale(-1));
//		Matrix rotation = Matrix.xAxisRotationMatrix(-1f);
//		Matrix rotation2 = Matrix.yAxisRotationMatrix(-1f);
//		Matrix translation2 = Matrix.translationMatrix(pos);
//		
//		Matrix transformation = translation2.multiply(rotation).multiply(rotation2).multiply(translation1);
//		
//		applyTransformation(transformation);
		
//		Matrix translation01 = Matrix.translationMatrix(pos.scale(-1));
//		Matrix rotation0 = Matrix.xAxisRotationMatrix(1f);
//		Matrix rotation02 = Matrix.yAxisRotationMatrix(1f);
//		Matrix translation02 = Matrix.translationMatrix(pos);
//		
//		Matrix transformation0 = translation2.multiply(rotation02).multiply(rotation0).multiply(translation1);
//		
//		applyTransformation(transformation0);
		
		System.out.println(stickerManager.isSolved());
		
//		makeMove(new int[]{0, 2}, true);
//		makeMove(new int[]{1, 2}, true);
//		makeMove(new int[]{0, 2}, false);
//		makeMove(new int[]{1, 2}, false);
	
		System.out.println(stickerManager.isSolved());
		//highlight(new int[]{1, 2});
		
	}
	
	public void highlight(int[] sector){
		
		for(Cublet c : pieces){
			
			if(c.isOnSector(sector)){
				
				System.out.println(c.getLocation());
				
				for(Face f : c.getFaces()){
					
					Game.renderer.getVertex(f.getVertexIndexArray()[0]).setRGB(0xabcdef);
					//Game.renderer.getVertex(f.getVertexIndexArray()[3]).setRGB(0xabcdef);
					
				}
				
			}
			
		}
		
	}
	
	public void constructPieces(){
		
		int index = 0;
		
		Vector pivot = pos.plus(new Vector( 
				
				-(float)(n - 1)/2,
				-(float)(n - 1)/2,
				(float)(n - 1)/2
				
		).scale(size));
		
		int temp = 0;
		
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				for(int z = 0; z < n; z++){
				
					if(isOnOutside(x, y, z)){
						
						Vector pos = pivot.plus(new Vector(x, y, -z).scale(size));
						ArrayList<Face> facesToAdd = new ArrayList<Face>();
						
						//System.out.println(getStickerCoordinates(x, y, z)[0] + " , " + getStickerCoordinates(x, y, z)[1]);
						
						for(int face = 0; face < 6; face++){
							
							Face f = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[face], size/2, size, color));
							facesToAdd.add(f);
							
							if(isOnFace(face, x, y, z)){
								
								int[] stickerCoordinates = stickerManager.getStickerCoordinates(face, x, y, z);
								//if(rep == 2) System.out.println(stickerCoordinates[0] + " , " + stickerCoordinates[1]);
								//System.out.println(stickerCoordinates[0] + " , " + stickerCoordinates[1]);
								Sticker s = new Sticker(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[face], size/2 + 0.03f, size * 0.8f, colorScheme[face]), stickerCoordinates, face);
								facesToAdd.add(s);
								
								
								
//								Face f0 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[0], size/2, size, color));
//								Face f1 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[1], size/2, size, color));
//								Face f2 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[2], size/2, size, color));
//								Face f3 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[3], size/2, size, color));
//								Face f4 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[4], size/2, size, color));
//								Face f5 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[5], size/2, size, color));
//								
//								//System.out.println("s " + CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[rep], size/2 + 0.01f, size * 0.8f, colorScheme[rep]));
//								
//								facesToAdd.add(f0);
//								facesToAdd.add(f1);
//								facesToAdd.add(f2);
//								facesToAdd.add(f3);
//								facesToAdd.add(f4);
//								facesToAdd.add(f5);
//								facesToAdd.add(s);
//								
//								temp += 7 * 6;
								//temp += 6;
							}
							
						}
						
//						Face f0 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[0], size/2, size, color));
//						Face f1 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[1], size/2, size, color));
//						Face f2 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[2], size/2, size, color));
//						Face f3 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[3], size/2, size, color));
//						Face f4 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[4], size/2, size, color));
//						Face f5 = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[5], size/2, size, color));
						
						//System.out.println("s " + CubeGeometry.constructFaceVertices(pos, EAngle.AXIS_ANGLES[rep], size/2 + 0.01f, size * 0.8f, colorScheme[rep]));
						
//						facesToAdd.add(f0);
//						facesToAdd.add(f1);
//						facesToAdd.add(f2);
//						facesToAdd.add(f3);
//						facesToAdd.add(f4);
//						facesToAdd.add(f5);
						
						//temp += 6 * 6;
						
						
						
//						if(isOnFace(0, x, y, z)){
//							
//							Face f = new Face(CubeGeometry.constructFaceVertices(pos, EAngle.RIGHT, size, size, colorScheme[0]));
//							//facesToAdd.add(f);
//							
//						}
						
						pieces[index] = new Cublet(new Vector(x, y, z), facesToAdd);
						
						//System.out.println(pieces[index].getFaces());
						
						index++;
						//System.out.println(index);
						
					}
					
				}
			}
		}
		
		System.out.println(temp);
		
	}
	
//	public int[] getStickerCoordinates(int face, int x, int y, int z){
//		
//		if(face == 0) /*return new int[]{z, n - y - 1};*/ return new int[]{y, n - z - 1};
//		if(face == 1) return new int[]{x, n - z - 1};
//		if(face == 2) return new int[]{x, y};
//		if(face == 3) return new int[]{n - y - 1, n - z - 1};
//		if(face == 4) return new int[]{x, n - z - 1};/*return new int[]{x, z};*/ /*return new int[]{99, 99};*/
//		//return new int[]{z, n - x - 1};
//		return new int[]{x, n - y - 1};
//		//return new int[]{n - y - 1, x};
//		
//	}
	
	public boolean isOnFace(int face, int x, int y, int z){
		
		if(face == 0) return x == n - 1;
		else if(face == 1) return y == n - 1;
		else if(face == 2) return z == 0;
		else if(face == 3) return x == 0;
		else if(face == 4) return y == 0;
		return z == n - 1;
		
	}
	
//	public ArrayList<Integer> getAxisIndices(int x, int y, int z){
//		
////		int[] axisIndices = new int[3];
////
////		for(int rep = 0, index = 0; rep < 6 && index < 3; rep++){
////			
////			if(isOnFace(rep, x, y, z)){
////				axisIndices[index] = rep;
////				index++;
////			}
////			
////		}
////		
////		return axisIndices;
//		
//		ArrayList<Integer> axisIndices = new ArrayList<Integer>();
//		
//		for(int rep = 0; rep < 6; rep++)	
//			if(isOnFace(rep, x, y ,z))
//				axisIndices.add(rep);
//		
//		return axisIndices;
//		
//	}
	
	public boolean isOnOutside(int x, int y, int z){
		
		return x == 0 || x == n - 1 ||
				y == 0 || y == n - 1 ||
				z == 0 || z == n - 1;
		
	}
	
	public void makeMove(int[] sector, boolean cc){
		
		rotateSectorData(sector, cc);
		
		stickerManager.rotateStickerData(sector, cc);
		
		rotateFace(sector, axis[sector[0]], (float) Math.PI/2 * (cc ? -1 : 1));
		
	}
	
	public void rotateSectorData(int[] sector, boolean cc){
		
		Matrix transformation = turnCCMatrices[sector[0] + (cc ? 0 : 3)];
		//System.out.println("Transformation \n" + transformation);
		
		for(Cublet c : pieces){
			
			if(c.isOnSector(sector)){
				
				System.out.println("before");
				System.out.println(c.getLocation());
				c.rotateData(transformation);
				System.out.println(c.getLocation());
				
			}
			
		}
		
		//turns the data, not the model
		
	}
	
	public void moveCube(Vector translation){
		
		//this.pos = this.pos.plus(translation);
		applyTransformation(Matrix.translationMatrix(translation));
		
	}
	
	public void moveCubeTo(Vector position){
		
		this.pos = position;
		
	}
	
	public void rotateCube(EAngle eAngle){
		
		
		
		
		Matrix translation1 = Matrix.translationMatrix(pos.scale(-1));
		Matrix rotation = eAngle.rotationMatrix();
		Matrix translation2 = Matrix.translationMatrix(pos);
		
		Matrix transformation = translation2.multiply(rotation).multiply(translation1);
		
		for(int i = 0; i < axis.length; i++)
			axis[i] = rotation.multiply(axis[i].toMatrix()).toVector();
		
		applyTransformation(transformation);
		
	}
	
	public void rotateFace(int[] sector, Vector axis, float radians){
		
//		Matrix translation1 = Matrix.translationMatrix(pos.scale(-1));
//		Matrix rotation = eAngle.rotationMatrix();
//		Matrix translation2 = Matrix.translationMatrix(pos);
//		
//		Matrix transformation = translation2.multiply(rotation).multiply(translation1);
		
//		applyTransformation(transformation, sector);
		
		for(Cublet c : pieces){
			
			if((int) c.getLocation().m()[sector[0]][0] == sector[1]){
				
				//System.out.println(axis);
				c.rotate(pos, axis, radians);
				
			}
		}
		
	}
	
	@Override
	public void applyTransformation(Matrix transformation) {
		
		this.pos = transformation.multiply(pos.toMatrix()).toVector();
		
//		for(int i = 0; i < axis.length; i++)
//			axis[i] = transformation.multiply(axis[i].toMatrix());
		
		for(Cublet c : pieces)
			c.applyTransformation(transformation);
		
	}
	
//	public void applyTransformation(Matrix transformation, int[] sector) {
//		
//		this.pos = transformation.multiply(pos.toMatrix()).toVector();
//		
//		for(int i = 0; i < axis.length; i++)
//			axis[i] = transformation.multiply(axis[i].toMatrix()).toVector();
//		
//		for(Cublet c : pieces){
//		
//			if((int) c.getLocation().m()[sector[0]][0] == sector[1])
//				c.applyTransformation(transformation);
//		}
//	}
	
	public Sticker whichSticker(Ray ray){
		
		
		for(Cublet c : pieces){
			
			for(Face f : c.getFaces()){
				
				if(f instanceof Sticker){
					
					Sticker s = (Sticker) f;
					
					
					
					if(s.intersectsFace(ray)){
						
						return s;
						
					}
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public void destroy(float radians){
		
		rotateFace(new int[]{(int) (Math.random() * 3), (int) (Math.random() * n)}, axis[(int) (Math.random() * 3)], radians);
		
	}
	
	public void keyInputs(Mouse mouse){
		
		//applyTransformation(Matrix.translationMatrix(-0.000001f, 0, 0));
		
		Vector direction = mouse.getDirection();
		//System.out.println(direction);
		
		float rotationSensitivity = 0.02f;		
		float translationSensitivity = 0.05f;
		
		if(mouse.left()){
			
			rotateCube(new EAngle(-direction.y() * rotationSensitivity, direction.x() * rotationSensitivity, 0));
			
			
			//destroy(0.5f);
			
		}
		
		if(mouse.right()){
			
			//System.out.println(Game.renderer.getSize());
			
			Ray ray = mouse.getRay();
			
			Sticker sticker = whichSticker(ray);
			
			//rotateFace(new int[]{1, 0}, axis[1], 0.1f);
			//rotateFace(new int[]{2, 2}, axis[2], 0.1f);
			
			if(sticker == null){
				
				//moveCube(direction.scale(translationSensitivity));
				
				//rotateFace(new EAngle(0, 0.01f, 0), new int[]{1, 0});
				
				
			} else {
				int[] via = sticker.getVertexIndexArray();
				//Game.renderer.getVertex(via[0]).setRGB(0xabcdef);
				//System.out.println(ray);
				
				
				//System.out.println(ray);
				
				//System.out.println(Game.renderer.getVertex(via[0]).getRGB() + " coords:  " + sticker.getStickerCoordinates()[0] + " , " + sticker.getStickerCoordinates()[1]);
			
				//for(int x : via) System.out.print(x + " "); System.out.println();
				//for(int x : via) Game.renderer.getVertex(x).setRGB(0xabcdef);
//			
//				int[] c = sticker.getStickerCoordinates();
//				StickerData sd = stickerManager.getNet()[c[0]][c[1]];
				
				StickerData sd = stickerManager.getStickerData(sticker);
				System.out.println("axis: " + sd.getAxis()[0] + " , " + sd.getAxis()[1]);
				
				ArrayList<int[]> sectors = sd.getSectors();
				System.out.println("sector: ");
				for(int[] sector : sectors){
					
					for(int x : sector) System.out.print(x + " ");
					System.out.println();
					
				}
				
				Vector dragDirection = mouse.getDragDirection();
				
				
				System.out.println("dragDirection: " + dragDirection);
				
				
				float minDistance = 30;
				
				if(dragDirection.getMagnitudeSquared() > minDistance * minDistance){
				
					System.out.println("YEEET");
					Vector normalizedDirection = dragDirection.normalize();
					System.out.println("normalized: " + normalizedDirection);
					
					int whichAxisIndex = whichAxis(normalizedDirection, sd.getAxis());
					Vector rotationAxis = axis[whichAxisIndex];
					
					System.out.println("WHICH AXIS INDEX: " + whichAxisIndex);
					int[] sector = sd.getSectors().get(whichAxisIndex);
					
					if(direction.getMagnitudeSquared() > 1 * 1){
					
						float dot = direction.normalize().dot(rotationAxis);
						float cc = -dot / Math.abs(dot);
						
						makeMove(sector, cc > 0);
						//rotateFace(sector, rotationAxis, (float) direction.getMagnitude() * rotationSensitivity * cc);
						
					}
					
				}
				
				
				
				// sector, axis, radians
				//rotateFace(sd.getSectors().get(0), axis[sd.getAxis()[0]], 0.1f);
				
				
			}
			
			
			//System.out.println(sticker);
			
			//System.out.println(mouse.getRay());
			
		}
		
	}
	
	public int whichAxis(Vector normalizedMouseDirection, int[] axisIndices){
		
		
		
		Vector axis1 = axis[axisIndices[0]];
		Vector axis2 = axis[axisIndices[1]];
		
		//Vector projectedAxis1 = axis.project();
		return Math.abs(normalizedMouseDirection.dot(axis1)) < Math.abs(normalizedMouseDirection.dot(axis2)) ? axisIndices[0] : axisIndices[1];
		
	}
	
	public void update(float step){
		
		//destroy(0.1f);
		//rotateFace(new EAngle(0, 0.01f, 0), new int[]{1, 0});
		
	}
	
}
