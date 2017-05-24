
public class Move {

	private int[] sector;
	private boolean cc;
	private boolean animated;
	
	public Move(int[] sector, boolean cc, boolean animated){
		
		this.sector = sector;
		this.cc = cc;
		this.animated = animated;
		
	}

	public int[] getSector() {
		return sector;
	}

	public void setSector(int[] sector) {
		this.sector = sector;
	}

	public boolean isCc() {
		return cc;
	}

	public void setCc(boolean cc) {
		this.cc = cc;
	}
	
	public Move getInverse(){
		
		return new Move(sector, !cc, animated);
		
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}
	
}
