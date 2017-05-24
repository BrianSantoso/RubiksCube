
public class AnimationData {

	private int[] sector;
	private int axisIndex;
	private float radians;
	private Move move;
	
	public AnimationData(int[] sector, int axisIndex, float radians){
		
		this.sector = sector;
		this.axisIndex = axisIndex;
		this.radians = radians;
		this.move = null;
	}

	public AnimationData(int[] sector, int axisIndex, float radians, Move move){
		
		this.sector = sector;
		this.axisIndex = axisIndex;
		this.radians = radians;
		this.move = move;
		
	}
	
	public boolean hasMove(){
		
		return move != null;
		
	}
	
	public Move getMove(){
		
		return move;
		
	}
	
	public void setMove(Move move){
		
		this.move = move;
		
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
