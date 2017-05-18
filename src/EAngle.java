
public class EAngle {

	public static EAngle UP = new EAngle(0, 0, 0);
	public static EAngle RIGHT = new EAngle(0, 0, (float) (3 * Math.PI/2));
	public static EAngle BACK = new EAngle((float) Math.PI/2, 0, 0);
	public static EAngle FORWARD = new EAngle((float) (3 * Math.PI/2), 0, 0);
	public static EAngle DOWN = new EAngle((float) Math.PI, 0, 0);
	public static EAngle LEFT = new EAngle(0, 0, (float) Math.PI/2);
	
	public static EAngle[] AXIS_ANGLES = new EAngle[]{
			
			RIGHT,	// 0
			UP,		// 1
			BACK,	// 2
			LEFT,	// 3
			DOWN,	// 4
			FORWARD	// 5
			
	};
	
	private float pitch;
	private float yaw;
	private float roll;
	
	public EAngle(float pitch, float yaw, float roll){
		
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		
	}
	
	public float pitch(){ return pitch; }
	public float yaw(){ return yaw; }
	public float roll(){ return roll; }
	
	public Matrix rotationMatrix(){
		
		Matrix xAxisRotationMatrix = Matrix.xAxisRotationMatrix(pitch());
		Matrix yAxisRotationMatrix = Matrix.yAxisRotationMatrix(yaw());
		Matrix zAxisRotationMatrix = Matrix.zAxisRotationMatrix(roll());
		
		return xAxisRotationMatrix.multiply(yAxisRotationMatrix).multiply(zAxisRotationMatrix);
		
	}
	
	public EAngle normalize(){
		
		
	
		
	}
	
	@Override
	public String toString(){
		
		return "(" + pitch + ", " + yaw + ", " + roll + ")";
		
	}
	
	
}
