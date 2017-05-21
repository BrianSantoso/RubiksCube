
public class Move {

	private int[] sector;
	private boolean cc;
	
	public Move(int[] sector, boolean cc){
		
		this.sector = sector;
		this.cc = cc;
		
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
		
		return new Move(sector, !cc);
		
	}
	
}
