import java.util.Arrays;

public class Matrix {
	
	private final float[][] m;
	
	public float[][] m(){
		
		return m;
		
	}
	
	public static final Matrix IDENTITY = new Matrix(new float[][]{
		
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, 0, 1}
		
	});
	
	public static final Matrix xAxisRotation90CC = new Matrix(new float[][]{
		
		{1,  0,  0,  0},
		{0,  0, -1,  0},
		{0,  1,  0,  0},
		{0,  0,  0,  1}
		
	});
	
	public static final Matrix yAxisRotation90CC = new Matrix(new float[][]{
			
		{0,  0,  1,  0},
		{0,  1,  0,  0},
		{-1, 0,  0,  0},
		{0,  0,  0,  1}
		
	});
	
	public static final Matrix zAxisRotation90CC = new Matrix(new float[][]{
		
		{0, -1,  0,  0},
		{1,  0,  0,  0},
		{0,  0,  1,  0},
		{0,  0,  0,  1}
		
	});
	
	public static final Matrix xAxisRotation90C = new Matrix(new float[][]{
		
		{1,  0,  0,  0},
		{0,  0,  1,  0},
		{0, -1,  0,  0},
		{0,  0,  0,  1}
		
	});
	
	public static final Matrix yAxisRotation90C = new Matrix(new float[][]{
			
		{0,  0, -1,  0},
		{0,  1,  0,  0},
		{1,  0,  0,  0},
		{0,  0,  0,  1}
		
	});
	
	public static final Matrix zAxisRotation90C = new Matrix(new float[][]{
		
		{0,  1,  0,  0},
		{-1,  0,  0,  0},
		{0,  0,  1,  0},
		{0,  0,  0,  1}
		
	});
	
	public static Matrix xAxisRotationMatrix(float radians){
		
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		
		/*return new Matrix(new float[][]{
			
			{1,     0,     0},
			{0,   cos,  -sin},
			{0,   sin,   cos}
			
		});*/
		
		return new Matrix(new float[][]{
			
			{1,     0,     0,    0},
			{0,   cos,  -sin,    0},
			{0,   sin,   cos,    0},
			{0,     0,     0,    1}
			
		});
		
	}
	
	public static Matrix yAxisRotationMatrix(float radians){
		
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		/*
		return new Matrix(new float[][]{
			
			{cos,   0,   sin},
			{0,     1,   0  },
			{-sin,  0,   cos}
			
		});*/
		
		return new Matrix(new float[][]{
			
			{cos,    0,   sin,   0},
			{0,      1,   0,     0},
			{-sin,   0,   cos,   0},
			{0,      0,   0,     1}
			
		});
		
	}
	
	public static Matrix zAxisRotationMatrix(float radians){
		
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		
		
		/*return new Matrix(new float[][]{
			
			{cos,  -sin,   0},
			{sin,   cos,   0},
			{0,     0,     1}
			
		});*/
		
		return new Matrix(new float[][]{
			
			{cos,   -sin,   0,    0},
			{sin,    cos,   0,    0},
			{0,      0,     1,    0},
			{0,      0,     0,    1}
			
		});
		
	}
	
	public static Matrix scalingMatrix(float s){
		
		return new Matrix(new float[][]{
			
			{s, 0, 0, 0},
			{0, s, 0, 0},
			{0, 0, s, 0},
			{0, 0, 0, 1}
			
		});
		
	}
	
	public static Matrix translationMatrix(Vector translation){
		
		return translationMatrix(translation.x(), translation.y(), translation.z());
		
	}
	
	public static Matrix translationMatrix(float tx, float ty, float tz){
		
		return new Matrix(new float[][]{
			
			{1, 0, 0, tx},
			{0, 1, 0, ty},
			{0, 0, 1, tz},
			{0, 0, 0, 1}
			
		});
		
	}
	
	public Matrix(){
		
		this.m = new float[][]{
			
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
			
		};
		
	}
	
	public Matrix(float[][] m){
		
		this.m = m;
		
	}
	
	public int getCols(){
		
		return this.m[0].length;
		
	}
	
	public int getRows(){
		
		return this.m.length;
		
	}
	
	public Matrix multiply(Matrix mat){
		
		if(this.getCols() != mat.getRows())
			System.out.println("Incompatible dimensions Matrix * Matrix operation");
		
		Matrix result = new Matrix(new float[this.getRows()][mat.getCols()]);
		
		for(int ar = 0; ar < this.getRows(); ar++){
			
			for(int bc = 0; bc < mat.getCols(); bc++){
				
				float sum = 0;
				
				for(int i = 0; i < this.getCols(); i++){
					
					sum += this.m[ar][i] * mat.m[i][bc];
					
				}
				
				result.m[ar][bc] = sum;
				
			}
			
		}
		
		return result;
		
	}
	
	public Matrix multiply(Vector v){
		
	}
	
	public Matrix transpose(){
		
		Matrix result = new Matrix(new float[this.getCols()][this.getRows()]);
		
		for(int r = 0; r < this.getRows(); r++){
			
			for(int c = 0; c < this.getCols(); c++){
				
				result.m[c][r] = this.m[r][c];
				
			}
			
		}
		
		return result;
		
	}
	
	// I have no clue how this determinant method works but here is the link 
	// http://sandsduchon.org/duchon/math/determinantJava.html
	public float determinant () {
		
	    int n = getRows() - 1;
	    if (n < 0) return 0;
	    
	    float M [][][] = new float [n + 1][][];
	    
	    M[n] = this.m;  // init first, largest, M to a
	    
	    // create working arrays
	    for (int i = 0; i < n; i++)
	    	M[i] = new float [i + 1][i + 1];
	    
	    return determinant (M, n);
     } // end method getDecDet double [][] parameter
     
     public float determinant (float [][][] M, int m) {
     
    	 if (m == 0) return M[0][0][0];
    	 int e = 1;
       
    	 // init subarray to upper left mxm submatrix
    	 for (int i = 0; i < m; i++)
    		 for (int j = 0; j < m; j++)
    			 M[m-1][i][j] = M[m][i][j];
    	 
    	 float sum = M[m][m][m] * determinant (M, m-1);
       
    	 // walk through rest of rows of M
    	 for (int i = m-1; i >= 0; i--) {
    		for (int j = 0; j < m; j++)
    			 	M[m-1][i][j] = M[m][i+1][j];
         
    		e = -e;
    		sum += e * M[m][i][m] * determinant (M, m-1);
    	 } // end for each row of matrix
       
    	 return sum;
     }
     
     
    public Vector toVector(){
    	
    	return new Vector(
    			
    			this.m[0][0],
    			this.m[1][0],
    			this.m[2][0]
    	
    	);
    	
    }
	
    public float x(){
    	
    	return this.m[0][0];
    	
    }
    
    public float y(){
    	
    	return this.m[1][0];
    	
    }
    
    public float z(){
    	
    	return this.m[2][0];
    	
    }
    
    public float w(){
    	
    	return this.m[3][0];
    	
    }
    
	public boolean equals(Object obj){
		
		Matrix mat = (Matrix) obj;
		
		return Arrays.equals(this.m, mat.m);
		
	}
	
	public String toString(){
		
		int cols = this.getCols();
		int rows = this.getRows();
		
		
		String str = "";
		
		for(int r = 0; r < rows; r++){
			
			for(int c = 0; c < cols; c++){
				
				str += this.m[r][c] + "   ";
				
			}
			
			str += "\n";
			
		}
		
		return str;
		
	}

	
}
