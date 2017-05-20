import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{

	private int width; // width of canvas
	private int height; // height of canvas
	
	private int x;
	private int y;
	
	boolean down;
	boolean left, right;
	
	private int dragStartX;
	private int dragStartY;
	private int dragEndX;
	private int dragEndY;
	
	private int prevX;
	private int prevY;
	private Vector direction;
	
	private Vector dragDirection;
	
	public Mouse(int width, int height){
		
		this.width = width;
		this.height = height;
		
		down = false;
		left = false;
		right = false;
		
		this.x = 0;
		this.y = 0;
		this.dragStartX = 0;
		this.dragStartY = 0;
		this.dragEndX = 0;
		this.dragEndY = 0;
		this.prevX = 0;
		this.prevY = 0;
		
		this.dragDirection = Vector.ZERO;
		this.direction = Vector.ZERO;
	}
	
	public Vector prevToVector(){
		
		return new Vector(prevX, prevY, 0);
		
	}
	
	public Vector prevToVector(float z){
		
		return new Vector(prevX, prevY, z);
		
	}
	
	public Vector dragStartToVector(){
		
		return new Vector(dragStartX, dragStartY, 0);
		
	}
	
	public Vector dragStartToVector(float z){
		
		return new Vector(dragStartX, dragStartY, z);
		
	}
	
	public Vector toVector(){
		
		return new Vector(x, y, 0);
		
	}
	
	public Vector toVector(float z){
		
		return new Vector(x, y, z);
		
	}
	
	public Vector dragEndToVector(){
		
		return new Vector(dragEndX, dragEndY, 0);
		
	}
	
	public Vector dragEndToVector(float z){
		
		return new Vector(dragEndX, dragEndY, z);
		
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
		
		down = true;
		if(e.getButton() == 1) left = true;
		if(e.getButton() == 3) right = true;
		
		dragStartX = getX(e.getX());
		dragStartY = getY(e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		down = false;

		if(e.getButton() == 1) left = false;
		if(e.getButton() == 3) right = false;
		
		
		this.dragStartX = 0;
		this.dragStartY = 0;
		this.dragEndX = 0;
		this.dragEndY = 0;
		
		this.dragDirection = Vector.ZERO;
		
	}

	
	
	
	public void keyInputs() {
		// TODO Auto-generated method stub
		
		
	}

	
	public Ray getRay(){
		
		Vector screenPoint = toVector(-1);
		Vector point = new Vector(screenPoint.x() / width - 0.5f, screenPoint.y() / height - 0.5f, screenPoint.z());
		
		// Assume camera is oriented perfectly...
		// Should take into account camera's orientation.
		
		Vector origin = Game.renderer.getCamera().getPos();
		
		Vector direction = point.minus(origin).normalize();
		
		//System.out.println("AAA: " + point);
		
		return new Ray(origin, direction);
		
	}
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		prevX = x;
		prevY = y;
		
		x = getX(e.getX());
		y = getY(e.getY());
		
		dragEndX = getX(x);
		dragEndY = getY(y);
		
		dragDirection = dragEndToVector(-1).minus(dragStartToVector(0));
		
		
		
		direction = toVector().minus(prevToVector());
		
		//System.out.println(direction);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		prevX = x;
		prevY = y;
		
		x = getX(e.getX());
		y = getY(e.getY());
		
		direction = toVector(-1).minus(prevToVector());
		
		//System.out.println(direction);
		
	}
	
	public String toString(){
		
		return "(" + x + ", " + y + ")";
		
	}

	public Vector getDragDirection() {
		
		return dragDirection;
		
	}
	
	public Vector getDirection(){
		
		return direction;
		
	}
	
	public void setDirection(Vector direction){
		
		this.direction = direction;
		
	}

	public boolean down() {
		
		return down;
		
	}

	public boolean left() {
		
		return left;
	}

	public boolean right(){
		
		return right;
		
	}
	
}
