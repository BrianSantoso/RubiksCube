
public class Intersection {

	public static final Intersection INVALID = new Intersection(false, Vector.INFINITY, Vector.ZERO, Float.POSITIVE_INFINITY);
	
	private boolean valid;
	private Vector pos;
	private Vector normal;
	private float distanceSquared;
	
	public Intersection(boolean valid, Vector pos, Vector normal, float distanceSquared){
		
		this.valid = valid;
		this.pos = pos;
		this.normal = normal;
		this.distanceSquared = distanceSquared;
		
	}

	public static Intersection getInvalid() {
		return INVALID;
	}

	public boolean isValid() {
		return valid;
	}

	public Vector getPos() {
		return pos;
	}

	public Vector getNormal() {
		return normal;
	}

	public float getDistanceSquared() {
		return distanceSquared;
	}
	
}
