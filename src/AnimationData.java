
public class AnimationData {

	private int[] sector;
	private Vector axis;
	private float radians;
	
	public AnimationData(int[] sector, Vector axis, float radians){
		
		this.sector = sector;
		this.axis = axis;
		this.radians = radians;
		
	}

	public int[] getSector() {
		return sector;
	}

	public void setSector(int[] sector) {
		this.sector = sector;
	}

	public Vector getAxis() {
		return axis;
	}

	public void setAxis(Vector axis) {
		this.axis = axis;
	}

	public float getRadians() {
		return radians;
	}

	public void setRadians(float radians) {
		this.radians = radians;
	}
	
	
	
}
