
public class Ray {

	private Vector pos;
	private Vector direction;
	
	public Ray(Vector pos, Vector direction){
		
		this.pos = pos;
		this.direction = direction;
		
	}

	public Vector getPos() {
		return pos;
	}

	public Vector getDirection() {
		return direction;
	}
 
	public boolean intersectsTriangle(Vector a, Vector b, Vector c){
		
		Vector ab = b.minus(a);
		Vector ac = c.minus(a);
		
		Vector normal = ab.cross(ac).normalize();
		
		Plane plane = new Plane(a, normal);
				
		Intersection intersection = xPlane(plane);
		
		if(!intersection.isValid()) return false;
		
		Vector point = intersection.getPos();
		
		return Raster.isInsideTriangle(a, b, c, point);
		
	}
	
	public Intersection xPlane(Plane plane){
		
		float denom = this.direction.dot(plane.getNormal());
		
		// If the denominator is 0, return an invalid intersection
		if(denom == 0) return Intersection.INVALID;
		
		float t = plane.getPos().minus(this.pos).dot(plane.getNormal()) / denom;
		
		// If parametric parameter is negative, return an invalid intersection
		// 		Ray only goes in forward direction, so domain is restricted [0, inf)
		if(t < 0) return Intersection.INVALID;
		
		Vector pos = this.pos.plus(this.direction.scale(t));

		return new Intersection(true, pos, plane.getNormal(), this.pos.getDistanceSquared(pos));
		
	}
	
	public String toString(){
		
		return this.pos + ", " + this.direction;
		
	}
	
}
