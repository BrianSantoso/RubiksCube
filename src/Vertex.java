public class Vertex {
	
	private Matrix pos;
	private int rgb;
	
	public Vertex(Vector pos, int rgb){
		
		this.pos = pos.toMatrix();
		this.rgb = rgb;
		
	}
	
//	public Vertex(Vector pos){
//	
//		this.pos = pos.toMatrix();
//		this.rgb = M.rgb2int(
//				
//				(int)(Math.random() * 255),
//				(int)(Math.random() * 255),
//				(int)(Math.random() * 255)
//		
//		);
//	
//	}
	
	public Vertex(Matrix pos, int rgb){
		
		this.pos = pos;
		this.rgb = rgb;
		
	}
	
	
	public Matrix getPos() {
		return pos;
	}


	public void setPos(Vector pos) {
		this.pos = pos.toMatrix();
	}
	
	public void setPos(Matrix mat){
		this.pos = mat;
	}

	public int getRGB() {
		return rgb;
	}

	public void setArgb(int rgb) {
		this.rgb = rgb;
	}

	public float getX(){
		
		return this.pos.m()[0][0];
		
	}
	
	public float getY(){
		
		return this.pos.m()[1][0];
		
	}
	
	public float getZ(){
		
		return this.pos.m()[2][0];
		
	}
	
	
	public float getW(){
		
		return this.pos.m()[3][0];
		
	}
	
	public String toString(){
		
		return "\n" + this.getPos() + "\n";
		
	}

	public void setRGB(int rgb) {
		
		this.rgb = rgb;
		
	}
	
	

	
	
}
