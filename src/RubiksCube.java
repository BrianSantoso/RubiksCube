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
	
	
	private int[][] colorSchemes;
	private int[] colorScheme;
	
	//private final Matrix[] turnCCMatrices;
	
	private LinkedList<Move> moves;
	
	private ArrayList<AnimationData> animationQueue;
	
	private Sticker selectedSticker;
	private StickerData selectedStickerData;
	private boolean dragging;
	private boolean lockDirection;
	private float minDragDistance1;
	private float minRadians;
	private int lockedRotationAxisIndex;
	private float accumulatedRadians;
	
	private int animationFrames;
	private float accumulationTime;
	
	private float rotationSensitivity;
	private float translationSensitivity;
	
	
	public RubiksCube(int n, float scalar){
		
		this.n = Math.max(n, 1);
		this.size = 4.2f / n * scalar;
		
		float z = new Vector(1, 1, 1).scale((float)(this.n - 1)/2).getMagnitude();
		//pos = new Vector(0, 0, -15 -(float)(n - 1)/2);
		
		pos = new Vector(0, 0, -12.7729f - z);
		axis = new Vector[]{ Vector.RIGHT, Vector.UP, Vector.BACK, Vector.LEFT, Vector.DOWN, Vector.FORWARD }; // might replace forward with backwards and vice versa
		
		
		colorSchemes = new int[][]{
			
			new int[]{
					
					0xffffff,
					0xffffff,
					0xffffff,
					0xffffff,
					0xffffff,
					0xffffff,
					0x000000
					
			},
			
			new int[]{
					
					0xff6600, // 0: Orange. Right Face.
					0xffffff, // 1: White. Up Face.
					0x1e90ff, // 2: Blue. Front Face.
					0xfe0000, // 3: Red. Left Face.
					0xffff00, // 4: Yellow. Down Face.
					0x00ff00, // 5: Green. Back Face.
					0x080808  // 6: Cube's background color
					
			}
			
		};
		
		colorScheme = colorSchemes[1];
		
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
		
		// Polynomial expressing number of pieces of an n x n x n cube:
		// 6n^2 - 12n + 8, x > 1
		int len = this.n == 1 ? 1 : 6 * this.n * this.n - 12 * this.n + 8;
		pieces = new Cublet[len];
		constructPieces();
		
		//rotateCube(new EAngle(0.01f, 0.01f, 0));
		
		moves = new LinkedList<Move>();
		
		float t = (float) (n - 1) / 2;
//		turnCCMatrices = new Matrix[]{ 
//				
//				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90CC).multiply(Matrix.translationMatrix(0, -t, -t)),
//				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90CC).multiply(Matrix.translationMatrix(-t, 0, -t)),
//				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90CC).multiply(Matrix.translationMatrix(-t, -t, 0)),
//				
//				Matrix.translationMatrix(0, t, t).multiply(Matrix.xAxisRotation90C).multiply(Matrix.translationMatrix(0, -t, -t)),
//				Matrix.translationMatrix(t, 0, t).multiply(Matrix.yAxisRotation90C).multiply(Matrix.translationMatrix(-t, 0, -t)),
//				Matrix.translationMatrix(t, t, 0).multiply(Matrix.zAxisRotation90C).multiply(Matrix.translationMatrix(-t, -t, 0)),
//				
//		};
		
		
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
		
		//System.out.println(stickerManager.isSolved());
		
//		makeMove(new int[]{0, 2}, true);
//		makeMove(new int[]{1, 2}, true);
//		makeMove(new int[]{0, 2}, false);
//		makeMove(new int[]{1, 2}, false);
	
		//System.out.println(stickerManager.isSolved());
		//highlight(new int[]{1, 2});
		
		//for(int rep = 0; rep < 100; rep++) destroy(0.2f);
		
		//makeMove(new int[]{1, 0}, true);
		//stickerManager.rotateFaceStickers(4, true);
		
		//makeMove(new int[]{0, 2}, true);
		//stickerManager.rotateFaceStickers(0, true);
		
		//stickerManager.rotateStickerData(new int[]{1, 2}, true);
		
		rotationSensitivity = 0.02f;
		translationSensitivity = 0.05f;
		selectedSticker = null;
		selectedStickerData = null;
		dragging = false;
		lockedRotationAxisIndex = -100;
		accumulatedRadians = 0;
		
		animationFrames = 10;
		minDragDistance1 = 10;
		minRadians = (float) Math.PI / 6;
		
		animationQueue = new ArrayList<AnimationData>();
		accumulationTime = 0;
		
		
		//makeMove(new int[]{0, 2}, true);
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
						
						Matrix location = new Vector(x, y, z).toMatrix();
						//System.out.println(getStickerCoordinates(x, y, z)[0] + " , " + getStickerCoordinates(x, y, z)[1]);
						
						for(int face = 0; face < 6; face++){
							
							
							
							Face f = new Face(Face.constructFaceVertices(pos, EAngle.AXIS_ANGLES[face], size/2, size, colorScheme[6]));
							facesToAdd.add(f);
							
							if(isOnFace(face, x, y, z)){
								
								//System.out.println(colorScheme[face] == 0xff6600);
								
								int[] stickerCoordinates = stickerManager.getStickerCoordinates(face, x, y, z);
								//if(rep == 2) System.out.println(stickerCoordinates[0] + " , " + stickerCoordinates[1]);
								//System.out.println(stickerCoordinates[0] + " , " + stickerCoordinates[1]);
								Sticker s = new Sticker(Face.constructFaceVertices(pos, EAngle.AXIS_ANGLES[face], size/2 + 0.07f, size * 0.8f, colorScheme[face]), stickerCoordinates, face, location);
								
								//System.out.println(s);
								//if(face == 0 && x == 2 && z == 2 && y == 0) System.out.println("hi");
								//System.out.println(face == 0);
								
								//System.out.println("face: " + face + " coords: " + stickerCoordinates[0] + " , " + stickerCoordinates[1]);
								//stickerManager.addSticker(s, face, stickerCoordinates);
								stickerManager.addSticker(s);

								
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
						
						pieces[index] = new Cublet(location, facesToAdd);
						
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

		System.out.println("MOVE MADE");
		
		//if(!animated) rotateFace(sector, axis[sector[0]], (float) Math.PI/2 * (cc ? -1 : 1));
		
	}
	
	
	public void rotateSectorData(int[] sector, boolean cc){
		
		Matrix transformation = stickerManager.getTurnCCMatrices()[sector[0] + (cc ? 0 : 3)];
		//System.out.println("Transformation \n" + transformation);
		
		for(Cublet c : pieces){
			
			if(c.isOnSector(sector)){
				
//				System.out.println("before");
//				System.out.println(c.getLocation());
				c.rotateData(transformation);
//				System.out.println(c.getLocation());
				
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
	
	@Override
	public void setColor(int[] colorScheme){
		
		this.colorScheme = colorScheme;
		
		for(Cublet c : pieces)
			c.setColor(colorScheme);
		
	}
	
	public void setColor(int colorSchemeIndex){
		
		setColor(colorScheme[colorSchemeIndex]);
		
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
	
	public boolean stickerSelected(){
		
		return selectedSticker != null;
		
	}
	
	public void resetSelectionData(){
		
		selectedSticker = null;
		selectedStickerData = null;
		dragging = false;
		lockedRotationAxisIndex = -100;
		accumulatedRadians = 0;
		
	}
	
	public void lockRotationAxisIndex(int index){
		
		lockedRotationAxisIndex = index;
		
	}
	
	public boolean rotationAxisLocked(){
		
		return lockedRotationAxisIndex >= 0;
		
	}
	
	public void selectSticker(Sticker selectedSticker){
		
		this.selectedSticker = selectedSticker;
		selectedStickerData = stickerManager.getStickerData(selectedSticker);
		
	}
	
	public void interpolateFace(int[] sector, Vector axis, float radians){
		
		float radiansPerAnimation = radians / animationFrames;
		
		for(int rep = 0; rep < animationFrames; rep++){
			
			animationQueue.add(0, new AnimationData(sector, axis, radiansPerAnimation));
			
		}
		
	}
	
	public boolean isAnimating(){
		
		return animationQueue.size() > 0;
		
	}
	
	public void keyInputs(Mouse mouse){
		
		Vector direction = mouse.getDirection();
		Vector normalizedDirection = direction.normalize();
		
		Vector dragDirection = mouse.getDragDirection();
		Vector normalizedDragDirection = dragDirection.normalize();
		
		if(!isAnimating()){
			
			// put this part under !isAnimating() because the interpolated animation rotates 
			// around a given Vector axis and the axis will change as the cube rotates
			// so instead i should be using the axis index ?
			if(mouse.left()){
				
				rotateCube(new EAngle(-direction.y() * rotationSensitivity, direction.x() * rotationSensitivity, 0));
				
			}
		
		
			
			if(mouse.right()){
				
				if(stickerSelected()){
					
					if(rotationAxisLocked()){
						
						int[] sector = selectedStickerData.getSectors().get(lockedRotationAxisIndex % 3);
						
						Vector rotationAxis = axis[lockedRotationAxisIndex];
						
						float cross = direction.cross(rotationAxis).z();
						float cc = cross > 0 ? 1 : -1;
	//					System.out.println("cross: " + cross);
						
						float radians = direction.getMagnitude() * rotationSensitivity * cc;
						accumulatedRadians += radians;
						
						System.out.println(accumulatedRadians);
						
						rotateFace(sector, rotationAxis, radians);
						
						if(Math.abs(accumulatedRadians) > minRadians){
							
							// counter clockwise is defined reversely for axis 3, 4, and 5, so flip the rotation direction if it is 3 4 or 5
							
							//makeMove(sector, (lockedRotationAxisIndex > 2) ^ (dragDirection.cross(rotationAxis).z() < 0));
							
							makeMove(sector, (lockedRotationAxisIndex > 2) ^ (cc < 0));
							interpolateFace(sector, rotationAxis, cc * ((float) (Math.PI / 2) - Math.abs(accumulatedRadians)));
							
							
							System.out.println("accumulatedRadians " + accumulatedRadians);
							resetSelectionData();
						}
						
						
					} else {
						
						if(dragDirection.getMagnitudeSquared() >= minDragDistance1 * minDragDistance1){
							
							int[] axisIndices = selectedStickerData.getAxis();
							
							System.out.println(axisIndices[0] + " , " + axisIndices[1]);
							
							// THESE DOT PRODUCTS ARE NORMALIZED FOR THE SAKE OF COMPARISON
							float dot1 = axis[axisIndices[0]].dot(normalizedDragDirection);
							float dot2 = axis[axisIndices[1]].dot(normalizedDragDirection);
							
							int whichAxisToLock = Math.abs(dot1) < Math.abs(dot2) ? axisIndices[0] : axisIndices[1];
							
							System.out.println("locked axis index: " + whichAxisToLock);
							//DONT DO THIS: lock rotation around general axis. use dot product to determine which direction		
							lockRotationAxisIndex(whichAxisToLock);
							
							
						}
						
					}
					
				} else {
					
					Ray ray = mouse.getRay();
					Sticker requestedStickerSelection = whichSticker(ray);
					
					if(requestedStickerSelection != null)
						selectSticker(requestedStickerSelection);
					
				}
				
			} else {
				
				if(Math.abs(accumulatedRadians) > 0 && selectedStickerData != null){
					
					int[] sector = selectedStickerData.getSectors().get(lockedRotationAxisIndex % 3);
					Vector rotationAxis = axis[lockedRotationAxisIndex];
					
					interpolateFace(sector, rotationAxis, -accumulatedRadians);
					
				}
				
				
				
				
				resetSelectionData();
				
			}
		}
		
	}
	
//	public void keyInputs(Mouse mouse){
//		
//		Vector direction = mouse.getDirection();
//		//System.out.println(direction);
//		
//		if(mouse.left())
//			rotateCube(new EAngle(-direction.y() * rotationSensitivity, direction.x() * rotationSensitivity, 0));
//			
//		
//		if(mouse.right()){		
//			
//			
//			if(dragging){
//				
//				if(selectedSticker == null){
//					
//				} else {
//					
//					Vector dragDirection = mouse.getDragDirection();
//					System.out.println(dragDirection);
//					
//					if(lockDirection){
//						
//						Vector normalizedDirection = dragDirection.normalize();
//						int whichAxisIndex = whichAxis(normalizedDirection, selectedStickerData.getAxis());
//						Vector rotationAxis = axis[whichAxisIndex];
//						
//						System.out.println("WHICH AXIS INDEX: " + whichAxisIndex);
//						int[] sector = selectedStickerData.getSectors().get(whichAxisIndex % 3);
//						
//						if(direction.getMagnitudeSquared() > 5 * 1){
//						
//							//System.out.println(dragDirection);
//							
//							float dot = normalizedDirection.normalize().dot(rotationAxis);
//							float cc = -dot / Math.abs(dot);
//							
////							makeMove(sector, cc < 0);
////							selectedSticker = null;
////							selectedStickerData = null;
////							dragging = false;
////							lockDirection = false;
//							rotateFace(sector, rotationAxis, (float) direction.getMagnitude() * rotationSensitivity * cc);
//							
//						}
//						
//					} else {
//				
//						
//						System.out.println("dragDirecton: " + dragDirection);
//						
//						if(dragDirection.getMagnitudeSquared() > minDragDistance1 * minDragDistance1){
//							
//							System.out.println("Greater than minDragDistance1");
//							lockDirection = true;
//							
//							
//							
//							//rotateFace();
//							
//						} else {
//							
//							lockDirection = false;
//							
//						}
//					}
//					
//				}
//				
//			} else {
//				
//			
//				if(selectedSticker == null){
//					
//					Ray ray = mouse.getRay();
//					selectedSticker = whichSticker(ray);
//					
//					if(selectedSticker != null)
//						selectedStickerData = stickerManager.getStickerData(selectedSticker);
//					
//					
//					//rotateFace(new EAngle(0, 0.01f, 0), new int[]{1, 0});
//					
//					
//				} else {
//					
//					int[] via = selectedSticker.getVertexIndexArray();
//					for(int x : via) Game.renderer.getVertex(x).setRGB(0xabcdef);	// remove later
//					
//					//StickerData sd = stickerManager.getStickerData(selectedSticker);
//					
//					//System.out.println("axis: " + sd.getAxis()[0] + " , " + sd.getAxis()[1]);
//					
////					ArrayList<int[]> sectors = sd.getSectors();
//	//				System.out.println("sector: ");
//	//				for(int[] sector : sectors){
//	//					
//	//					for(int x : sector) System.out.print(x + " ");
//	//					System.out.println();
//	//					
//	//				}
//					
//					
//					
//					
//					
//					
////					Vector dragDirection = mouse.getDragDirection();
////					
////					
////					System.out.println("dragDirection: " + dragDirection);
////					
////					
////					float minDistance = 30;
////					
////					if(dragDirection.getMagnitudeSquared() > minDistance * minDistance){
////					
////						System.out.println("YEEET");
////						Vector normalizedDirection = dragDirection.normalize();
////						System.out.println("normalized: " + normalizedDirection);
////						
////						int whichAxisIndex = whichAxis(normalizedDirection, sd.getAxis());
////						Vector rotationAxis = axis[whichAxisIndex];
////						
////						System.out.println("WHICH AXIS INDEX: " + whichAxisIndex);
////						int[] sector = sd.getSectors().get(whichAxisIndex);
////						
////						if(direction.getMagnitudeSquared() > 1 * 1){
////						
////							float dot = direction.normalize().dot(rotationAxis);
////							float cc = -dot / Math.abs(dot);
////							
////							makeMove(sector, cc > 0);
////							//rotateFace(sector, rotationAxis, (float) direction.getMagnitude() * rotationSensitivity * cc);
////							
////						}
////						
////					}
//					
//					
//					
//					
//					
//					
//					
//					
//					
//				}
//				
//				
//				
//				
//			}
//			
//			// say dragging even if not clicking a sticker
//			dragging = true;
//			
//			System.out.println(selectedSticker);
//			
//			//if(selectedSticker == null)
//			//	moveCube(direction.scale(translationSensitivity));
//			
//		} else {
//			
//			selectedSticker = null;
//			selectedStickerData = null;
//			dragging = false;
//			lockDirection = false;
//			
//		}
//		
//	}
	
	public int whichAxis(Vector normalizedMouseDirection, int[] axisIndices){
		
		
		
		Vector axis1 = axis[axisIndices[0]];
		Vector axis2 = axis[axisIndices[1]];
		
		Vector axis3 = axis[axisIndices[0] + 3];
		Vector axis4 = axis[axisIndices[1] + 3];
		
		float[] dots = new float[]{
				
				axis1.dot(normalizedMouseDirection),
				axis2.dot(normalizedMouseDirection),
				axis3.dot(normalizedMouseDirection),
				axis4.dot(normalizedMouseDirection)
				
		};
		
		int[] indices = new int[]{
				
				axisIndices[0],
				axisIndices[1],
				axisIndices[0] + 3,
				axisIndices[1] + 3
				
		};
		
		int index = 0;
		float closestToZero = Math.abs(dots[index]);
		
		for(int rep = 0; rep < dots.length; rep++){
			
			if(dots[rep] < closestToZero){
				
				index = rep;
				closestToZero = Math.abs(dots[rep]);
				
			}
			
		}
		
		return indices[index];
		
		//Vector projectedAxis1 = axis.project();
		//return Math.abs(normalizedMouseDirection.dot(axis1)) < Math.abs(normalizedMouseDirection.dot(axis2)) ? axisIndices[0] : axisIndices[1];
		
	}
	
	public void update(float step){
		
		if(animationQueue.size() > 0){
			
			AnimationData a = animationQueue.get(0);
			
			//should store axis index huh?
			rotateFace(a.getSector(), a.getAxis(), a.getRadians());
			
			animationQueue.remove(0);
			
		}
		
		//destroy(0.2f);
		//destroy(0.1f);
		//rotateFace(new EAngle(0, 0.01f, 0), new int[]{1, 0});
		
	}
	
	
	
}
