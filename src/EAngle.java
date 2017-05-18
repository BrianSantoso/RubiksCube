
public class EAngle {

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
