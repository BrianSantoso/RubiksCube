
public class Vector {
	
	public static final Vector ZERO = new Vector(0, 0, 0);
	public static final Vector INFINITY = new Vector(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	public static final Vector UP = new Vector(0, 1, 0);
	public static final Vector DOWN = new Vector(0, -1, 0);
	public static final Vector RIGHT = new Vector(1, 0, 0);
	public static final Vector LEFT = new Vector(-1, 0, 0);
	public static final Vector FORWARD = new Vector(0, 0, 1);
	public static final Vector BACK = new Vector(0, 0, -1);
	public static float EPSILON = 1e-6f;
	

	/**
	 * 
	 * Instance Fields
	 * 
	 * Public because they are supposed to be immutable
	 * 
	 */
	private final float x;		//x component of the Vector
	private final float y;		//y component of the Vector
	private final float z;		//z component of the Vector
	
	
	/**
	 * Constructs a Vector with x, y, and z components
	 * 
	 * @param x		x component of the Vector
	 * @param y		y component of the Vector
	 * @param z		z component of the Vector
	 */
	public Vector(float x, float y, float z){
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Matrix mat){
		
		this.x = mat.m()[0][0];
		this.y = mat.m()[1][0];
		this.z = mat.m()[2][0];
		
	}
	
	
	/**
	 * 
	 * Instance Methods
	 * 
	 */
	
	/**
	 * Returns the sum of 2 Vectors
	 * 
	 * 
	 * @param a		A Vector being added
	 * @return		The sum of 2 Vectors
	 */
	public Vector plus(Vector a){
		
		return new Vector(this.x + a.x, this.y + a.y, this.z + a.z);
		
	}
	
	/**
	 * Returns the difference of 2 Vectors
	 * 
	 * @param a		A Vector being subtracted
	 * @return		The difference of 2 Vectors
	 */
	public Vector minus(Vector a){
		
		return new Vector(this.x - a.x, this.y - a.y, this.z - a.z);
		
	}
	
	/**
	 * Returns a new Vector whose components are the product of each component
	 * multiplied by a scalar
	 * 
	 * @param scalar	Amount Vector is scaled by
	 * @return			new Vector whose components are the product of each component
	 * 					multiplied by a scalar
	 */
	public Vector scale(float scalar){
		
		return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
		
	}
	
	/**
	 * Returns the dot product (scalar product) of 2 Vectors
	 * 
	 * @param a		Vector being a dotted with
	 * @return		The dot product (scalar product) of 2 Vectors
	 */
	public float dot(Vector a){
		
		return this.x * a.x + this.y * a.y + this.z * a.z;
		
	}
	
	/**
	 * Returns the cross product (vector product) of 2 Vectors
	 * 
	 * @param a		Vector being crossed with
	 * @return		The cross product of 2 Vectors
	 */
	public Vector cross(Vector a){
		
		return new Vector(
				
				this.y * a.z - this.z * a.y,
				this.z * a.x - this.x * a.z,
				this.x * a.y - this.y * a.x
				
		);
		
	}
	
	public float cross2(Vector a){
		
		return this.x * a.y - this.y * a.x;
		
	}
	
	/**
	 * Normalizes a Vector
	 * 
	 * @return		Unit Vector (Vector with length 1)
	 */
	public Vector normalize(){
		
		float magnitude = this.getMagnitude();
		return this.scale(1/magnitude);	//should use fast inverse square root instead
		
		
		/*
		float invMag = MathUtils.invSqrt(this.getMagnitudeSquared());
		return this.scale(invSqrt);
		*/
	}
	
	/**
	 * Finds the squared distance between 2 (point) Vectors 
	 * 
	 * @param a		Vector being compared
	 * @return		The squared distance between 2 (point) Vectors
	 */
	public float getDistanceSquared(Vector a){
		
		return (this.x - a.x) * (this.x - a.x) + (this.y - a.y) * (this.y - a.y) + (this.z - a.z) * (this.z - a.z);
		
	}
	
	/**
	 * Finds the distance between 2 (point) vectors
	 * 
	 * @param a		Vector being compared
	 * @return		The distance between 2 (point) Vectors
	 */
	public float getDistance(Vector a){
		
		return (float) Math.sqrt( this.getDistanceSquared(a) );
	}
	
	/**
	 * Gets the squared magnitude (length) of a Vector
	 * 
	 * @return		The squared magnitude of a Vector
	 */
	public float getMagnitudeSquared(){
		
		return this.dot(this);
	}
	
	/**
	 * Gets the magnitude (length) of a Vector
	 * 
	 * @return		The magnitude of a Vector
	 */
	public float getMagnitude(){
		
		return (float) Math.sqrt( this.getMagnitudeSquared() );
	}
	
	/**
	 * Reflects Vector across a surface normal
	 * 
	 * @param normal	Unit Vector (Vector with length 1) indicating a direction
	 * 					which is being reflected about
	 * @return			Reflected Vector accross a normal
	 */
	public Vector reflect(Vector normal){
		
		return this.minus( normal.scale(2 * this.dot(normal)) );
		
	}
	
	/**
	 * Projects Vector onto another
	 * 
	 * @param a		Vector being projected onto
	 * @return		Vector projection of Vector onto a
	 */
	public Vector project(Vector a){
		
		return a.scale(this.dot(a) / a.getMagnitudeSquared());
	}
	
	public Matrix toMatrix(){
		
		return new Matrix(new float[][]{
			
			{this.x},
			{this.y},
			{this.z},
			{1}
			
		});
		
	}
	
	public Vector rotateAround(Vector axis, float radians){
		
		// Rodrigues Rotation Formula 
		
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		
		return scale(cos).plus(axis.cross(this).scale(sin)).plus(axis.scale(axis.dot(this) * (1 - cos)));
		
	}
	
	public EAngle toEAngle(){
		
		// MUST BE A UNIT VECTOR
		
		float pitch = (float) Math.asin(y);
		float yaw = (float) Math.asin(z / Math.cos(pitch));
		
		return new EAngle(pitch, yaw, 0);
		
	}
	
	public float x(){
		
		return x;
		
	}
	
	public float y(){
		
		return y;
		
	}
	
	public float z(){
		
		return z;
		
	}
	
	/**
	 * Checks if 2 vectors are equal, with a small margin of error
	 * 
	 * @param a		Vector being compared
	 * @return		true, if Vectors are equal with a small margin of error
	 * 				false, if Vectors are not equal with a small margin of error
	 */
	public boolean equals(Object obj){
		
		Vector a = (Vector) obj;
		
		return
				
			Math.abs( this.x - a.x ) < EPSILON &&
			Math.abs( this.y - a.y ) < EPSILON &&
			Math.abs( this.z - a.z ) < EPSILON;
		
	}
	
	/**
	 * Converts Vector to String containing its x, y, and z components in the format
	 * "<x, y, z>"
	 * 
	 * @return		String containing Vector's x, y, and z components in the format
	 * 				"<x, y, z>"
	 */
	@Override
	public String toString(){
		
		return "<" + x + ", " + y + ", " + z + ">";
		
	}
	
}

