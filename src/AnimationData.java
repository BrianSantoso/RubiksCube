
public class AnimationData {

	private int[] sector;
	private int axisIndex;
	private float radians;
	
	public AnimationData(int[] sector, int axisIndex, float radians){
		
		this.sector = sector;
		this.axisIndex = axisIndex;
		this.radians = radians;
		
	}

	public int[] getSector() {
		return sector;
	}

	public void setSector(int[] sector) {
		this.sector = sector;
	}

	public int getAxisIndex() {
		return axisIndex;
	}

	public void setAxisIndex(int axisIndex) {
		this.axisIndex = axisIndex;
	}

	public float getRadians() {
		return radians;
	}

	public void setRadians(float radians) {
		this.radians = radians;
	}
	
	
	
}
