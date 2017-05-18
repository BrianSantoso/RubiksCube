import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{

	private int width; // width of canvas
	private int height; // height of canvas
	
	private int x;
	private int y;
	
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private Vector direction;
	
	public Mouse(int width, int height){
		
		this.width = width;
		this.height = height;
		
		this.x = 0;
		this.y = 0;
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;
		
		this.direction = Vector.ZERO;
		
	}
	
	public Vector startToVector(){
		
		return new Vector(startX, startY, 0);
		
	}
	
	public Vector startToVector(float z){
		
		return new Vector(startX, startY, z);
		
	}
	
	public Vector toVector(){
		
		return new Vector(x, y, 0);
		
	}
	
	public Vector toVector(float z){
		
		return new Vector(x, y, z);
		
	}
	
	public Vector endToVector(){
		
		return new Vector(endX, endY, 0);
		
	}
	
	public Vector endToVector(float z){
		
		return new Vector(endX, endY, z);
		
	}
	
	public int getX(int x){
		
		return x;
		
	}
	
	public int getY(int y){
		
		return this.height - y - 1;
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		startX = getX(e.getX());
		startY = getY(e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;
		
		this.direction = Vector.ZERO;
		
	}

	
	
	
	public void keyInputs() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		endX = getX(e.getX());
		endY = getY(e.getY());
		
		direction = endToVector(-1).minus(startToVector(0));
		
		//System.out.println(direction);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		x = getX(e.getX());
		y = getY(e.getY());
	}
	
	public String toString(){
		
		return "(" + x + ", " + y + ")";
		
	}

}
