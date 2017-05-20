
public class Plane {

	private Vector pos;
	private Vector normal;
	
	public Plane(Vector pos, Vector normal){
		
		this.pos = pos;
		this.normal = normal;
		
	}

	public Vector getPos() {
		return pos;
	}

	public Vector getNormal() {
		return normal;
	}

}
