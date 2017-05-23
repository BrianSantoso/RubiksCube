import java.util.ArrayList;
import java.util.Arrays;

public class Raster {

	private final int width;
	private final int height;
	private final int halfWidth;
	private final int halfHeight;
	
	private int[] pixels;
	private float[] depthBuffer;
	
	public Raster(int width, int height){
		
		this.width = width;
		this.height = height;
		halfWidth = this.width / 2;
		halfHeight = this.height / 2;
		pixels = new int[width * height];
		depthBuffer = new float[width * height];
		
		clear(0xfafafa);
		
	}
	
	public void clear(int color){
		
		Arrays.fill(pixels, color);
		Arrays.fill(depthBuffer, -Float.MAX_VALUE);
		
	}
	
	public void tryToSetPixel(int x0, int y0, int color, float depth){
		
		int x = x0 + halfWidth;
		int y = halfHeight - y0 - 1; // height - (y0 + halfHeight) - 1
		
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		
		int index = y * width + x;
		
		// make it < to prevent flashing maybe
		
		//if(depthBuffer[index] == -5) System.out.println(depth < depthBuffer[index]);
		
		if(depth > depthBuffer[index]){
		//if(Math.abs(depth) < Math.abs(depthBuffer[index])){
		//if(Math.abs(depth) <= Math.abs(depthBuffer[index])){
			
			pixels[index] = color;
			depthBuffer[index] = depth;
			
		}
		
	}
	
	public int getPixel(int index){
		
		return pixels[index];
		
	}
	
	public int getPixel(int x, int y){
		
		return pixels[y * width + x];
		
	}
	
	public int[] getPixels(){
		
		return pixels;
		
	}
	
	public float getDepth(int x, int y){
		
		return depthBuffer[y * width + x];
		
	}
	
	public float[] getDepthBuffer(){
		
		return depthBuffer;
	
	}
	
	public int getWidth(){
		
		return width;
		
	}
	
	public int getHeight(){
		
		return height;
		
	}
	
	public static float[] barycentricWeights(Vector a, Vector b, Vector c, Vector point){
		
		float totalArea = areaOfTriangle(a, b, c);
		float areaOfTriangleA = areaOfTriangle(point, b, c); // thanks Joseph
		float areaOfTriangleB = areaOfTriangle(point, c, a);
		float areaOfTriangleC = totalArea - areaOfTriangleA - areaOfTriangleB;
		
		//System.out.println(areaOfTriangleA + areaOfTriangleB + areaOfTriangleC == totalArea);
		
		return new float[]{
				
				areaOfTriangleA / totalArea,
				areaOfTriangleB / totalArea,
				areaOfTriangleC / totalArea
				
		};
		
	}
	
	public static float[] barycentricWeights2(Vector a, Vector b, Vector c, Vector point){
		
		float totalArea = areaOfTriangle2(a, b, c);
		float areaOfTriangleA = areaOfTriangle2(point, b, c);
		float areaOfTriangleB = areaOfTriangle2(point, c, a);
		float areaOfTriangleC = totalArea - areaOfTriangleA - areaOfTriangleB;
		
		//System.out.println(areaOfTriangleA + areaOfTriangleB + areaOfTriangleC == totalArea);
		
		return new float[]{
				
				areaOfTriangleA / totalArea,
				areaOfTriangleB / totalArea,
				areaOfTriangleC / totalArea
				
		};
		
	}
	
	public static float barycentricInterpolation(Vector a, float aValue, Vector b, float bValue, Vector c, float cValue, Vector point){
		
		float[] weights = barycentricWeights(a, b, c, point);
		
		return aValue * weights[0] + bValue * weights[1] + cValue * weights[2];
		
	}
	
	public static float barycentricInterpolation2(Vector a, float aValue, Vector b, float bValue, Vector c, float cValue, Vector point){
		
		float[] weights = barycentricWeights2(a, b, c, point);
		
		return aValue * weights[0] + bValue * weights[1] + cValue * weights[2];
		
	}
	
	public static float areaOfTriangle(Vector a, Vector b, Vector c){
		
		Vector AB = b.minus(a);
		Vector AC = c.minus(a);
		
		return AB.cross(AC).getMagnitude() * 0.5f;
		
	}
	
	public static float areaOfTriangle2(Vector a, Vector b, Vector c){
		
		Vector AB = b.minus(a);
		Vector AC = c.minus(a);
		
		return AB.cross2(AC) * 0.5f;
		
	}
	
	public static boolean isInsideTriangle(Vector a, Vector b, Vector c, Vector point){
		
		Vector side1 = b.minus(a);
		Vector side2 = c.minus(b);
		Vector side3 = a.minus(c);
		
//		Vector AP = point.minus(a);
		
//		if(AP.cross(side1).z() > 0)
//			return false;
//		
//		
//		Vector BP = point.minus(b);
//		if(BP.cross(side2).z() > 0)
//			return false;
//		
//		Vector CP = point.minus(c);
//		if(CP.cross(side3).z() > 0)
//			return false;
		
		Vector AP = point.minus(a);
		if(AP.cross2(side1) > 0)
			return false;
		
		
		Vector BP = point.minus(b);
		if(BP.cross2(side2) > 0)
			return false;
		
		Vector CP = point.minus(c);
		if(CP.cross2(side3) > 0)
			return false;
		
		return true;
		
	}
	/*
	public static ArrayList<int[]> getPointsInTriangle(Vector a, Vector b, Vector c){
		
		ArrayList<int[]> pointsInTriangle = new ArrayList<int[]>();
		
		Vector side1 = b.minus(a);
		Vector side2 = c.minus(b);
		Vector side3 = a.minus(c);
		
		// oops these arent screen coordinates... cant cast to int
		
		int x2 = (int) Math.min(a.x(), Math.min(b.x(), c.x()));
		int y2 = (int) Math.min(a.y(), Math.min(b.y(), c.y()));
		
		int x3 = (int) Math.max(a.x(), Math.max(b.x(), c.x()));
		int y3 = (int) Math.max(a.y(), Math.max(b.y(), c.y()));
		
		int x0 = Math.min(x2, x3);
		int y0 = Math.min(y2, y3);
		int x1 = Math.max(x2, x3);
		int y1 = Math.max(y2, y2);
		
		System.out.println(x0 + " , " + x1);
		
		for(int x = x0; x < x1; x++){
			for(int y = y0; y < y1; y++){
				
				Vector point = new Vector(x, y, -1);
				
				Vector AP = point.minus(a);
				
				if(AP.cross(side1).z() > 0)
					continue;
				
				
				Vector BP = point.minus(b);
				if(BP.cross(side2).z() > 0)
					continue;
				
				Vector CP = point.minus(c);
				if(CP.cross(side3).z() > 0)
					continue;
				
				pointsInTriangle.add(new int[]{x, y});
				
			}
		}
		
		return pointsInTriangle;
		
	}
	*/
	public static ArrayList<int[]> getPointsInTriangle(int[] a0, int[] b0, int[] c0){
		
		ArrayList<int[]> pointsInTriangle = new ArrayList<int[]>();
		
		Vector a = new Vector(a0[0], a0[1], 0);
		Vector b = new Vector(b0[0], b0[1], 0);
		Vector c = new Vector(c0[0], c0[1], 0);
		
		Vector side1 = b.minus(a);
		Vector side2 = c.minus(b);
		Vector side3 = a.minus(c);
		
		int x0 = (int) Math.min(a.x(), Math.min(b.x(), c.x()));
		int y0 = (int) Math.min(a.y(), Math.min(b.y(), c.y()));
		
		int x1 = (int) Math.max(a.x(), Math.max(b.x(), c.x()));
		int y1 = (int) Math.max(a.y(), Math.max(b.y(), c.y()));
		
		for(int x = x0; x < x1; x++){
			for(int y = y0; y < y1; y++){
			
				Vector point = new Vector(x, y, 0);
				
//				Vector AP = point.minus(a);
//				
//				if(AP.cross(side1).z() > 0)
//					continue;
//				
//				
//				Vector BP = point.minus(b);
//				if(BP.cross(side2).z() > 0)
//					continue;
//				
//				Vector CP = point.minus(c);
//				if(CP.cross(side3).z() > 0)
//					continue;
				Vector AP = point.minus(a);
				if(AP.cross2(side1) > 0)
					continue;
				
				
				Vector BP = point.minus(b);
				if(BP.cross2(side2) > 0)
					continue;
				
				Vector CP = point.minus(c);
				if(CP.cross2(side3) > 0)
					continue;
				
				pointsInTriangle.add(new int[]{x, y});
				
			}
		}
		
		return pointsInTriangle;
		
	}
	
	public static ArrayList<int[]> getTrianglePixels(int[] a, int[] b, int[] c, boolean wireFrame){
		
		return wireFrame ? getPointsOnTriangle(a, b, c) : getPointsInTriangle(a, b, c);
		
	}
	
	public static ArrayList<int[]> bresenham(int[] p0, int[] p1){
		
		//http://www.sanfoundry.com/java-program-bresenham-line-algorithm/
		
		int x0 = p0[0];
		int y0 = p0[1];
		int x1 = p1[0];
		int y1 = p1[1];
		
		ArrayList<int[]> line = new ArrayList<int[]>();
		
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
 
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
 
        int err = dx-dy;
        int e2;
 
        while (true) 
        {
            line.add(new int[]{x0, y0});
 
            if (x0 == x1 && y0 == y1) 
                break;
 
            //e2 = 2 * err;
            e2 = err << 1;
            if (e2 > -dy) 
            {
                err -= dy;
                x0 += sx;
            }
 
            if (e2 < dx) 
            {
                err += dx;
                y0 += sy;
            }
        }                                
        return line;
		
	}
	
	public static ArrayList<int[]> getPointsOnTriangle(int[] a, int[] b, int[] c){
		
		ArrayList<int[]> tri = new ArrayList<int[]>();
		
		tri.addAll(bresenham(a, b));
		tri.addAll(bresenham(a, c));
		tri.addAll(bresenham(b, c));
		
		return tri;
		
	}
	
	public static int getRed(int rgb){
		
		return rgb & 0xff0000 >> 16;
		
	}
	
	public static int getGreen(int rgb){
		
		return rgb & 0xff00 >> 8;
		
	}
	
	public static int getBlue(int rgb){
		
		return rgb & 0xff;
		
	}
	
	public static int rgb2int(int r, int g, int b){
		
		return (r << 16) | (g << 8) | b;
		
	}
	
}
