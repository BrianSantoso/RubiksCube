import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private final int width;		// width of the screen (not raster) in pixels
	private final int height;		// height of the screen (not raster) in pixels
	private final int scale;
	
	private Thread thread;
	private JFrame frame;
	private boolean running;
	
	private BufferedImage image;
	private int[] pixels; //node1
	
	public static Renderer renderer;
	private RubiksCube rubiksCube;
	
	//private Keyboard keyboard;
	
	private long now, last;
	private float dt, accumulation, fps;
	private final float step;
	
	public Game(int width, int height, int scale, float step, String title){
		
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		this.step = step;
		now = System.currentTimeMillis();
		last = 0;
		dt = 0;
		fps = 0;
		accumulation = 0;
		running = false;
		
		
		int rasterWidth = width / scale;	// round down if not fit
		int rasterHeight = height / scale;
		Raster raster = new Raster(rasterWidth, rasterHeight);
		Camera camera = new Camera();
		
		renderer = new Renderer(camera, raster);
		
		image = new BufferedImage(rasterWidth, rasterHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		setPreferredSize(new Dimension(width, height));
		
		
		
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		//keyboard = new Keyboard();
		//addKeyListener(keyboard);
		
		
	}
	
	public synchronized void start(){
		
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
		
	}
	
	public synchronized void stop(){
		
		running = false;
		try{
			thread.join();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	public void keyInputs(){
		
		//keyboard.keyInputs();
		
	}
	
	public void update(){
		
		//renderer.update();
		
		Matrix rotation = Matrix.yAxisRotationMatrix(1.5f * step);
		Matrix rotation2 = Matrix.xAxisRotationMatrix(1.5f * step);
		Matrix rotation3 = Matrix.zAxisRotationMatrix(1.5f * step);
		Matrix translation1 = Matrix.translationMatrix(0.5f, 0, -10.5f);
		Matrix translation2 = Matrix.translationMatrix(-0.5f, 0, 10.5f);
		Matrix transformation = translation1.multiply(rotation3).multiply(rotation2).multiply(rotation).multiply(translation2);
		
		for(Vertex v : renderer.getVertices()){
			
			Matrix newPos = transformation.multiply(v.getPos());
			v.setPos(newPos);
			
		}
		
	}
	
	public void draw(){
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(1);	//use 3 for triple buffering
			return;
		}
		
		
		
		renderer.render();
		
		for(int rep = 0; rep < pixels.length; rep++)
			pixels[rep] = renderer.getRaster().getPixel(rep);
		
		Graphics g = bs.getDrawGraphics(); //create link between Graphics and BufferStrategy
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawImage(image, 0, getWidth(), getWidth(), -getHeight(), null);
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//g.dispose(); //remove Graphics from each frame
		//bs.show(); //make next available buffer visible
		
		g.setColor(Color.RED);
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
		g.setFont(newFont);
		g.drawString((int) fps + " fps", 15, 40);
		
		//System.out.println(fps + "");
		
		renderer.getRaster().clear(0xfafafa);
		
	}

	public void run(){
		
		while(running){
			
			now = System.currentTimeMillis();
			dt = Math.min((now - last)/1000f, 1);
			accumulation += dt;
			
			fps = 1 / dt;
			
			keyInputs();
			
			while(accumulation >= step){
				
				
				update();
				accumulation -= step;
				
			}
			
			draw();
			
			last = now;
			
			
		}
		
	}
	
	public Renderer getRenderer(){
		
		return renderer;
		
	}
	
	public static void main(String[] args){
		
		/*
		
		JFrame frame = new JFrame("Wireframe");
		Panel panel = new Panel(800, 800);
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		*/		
		
		
		
		Game game = new Game(800, 800, 1, 1f/60, "Pixels on a Screen");
		game.start();
		
		
		//System.out.println(1.5f / 2);
		
		//float[] test = Raster.barycentricWeights(new Vector(1, 5, 2), new Vector(0, 0, 0), new Vector(5, 1, 3), new Vector(2, 2, 0));
		//System.out.println(test[0]);
		
		
		
		//System.out.println(v.toEAngle());
//		ArrayList<Vertex> testFace = CubeGeometry.constructFaceVertices(new Vector(0.5f, 0, -3.5f), new EAngle(0, 0, 0), 1, 0xff0000);
//		System.out.println(testFace);
//		game.getRenderer().addVertices(testFace);
		
//		System.out.println(v.rotateAround(Vector.BACK.scale(1), (float) Math.PI/2));
		
//		System.out.println(CubeGeometry.constructFaceVertices(Vector.ZERO, Vector.LEFT, 1, 0xff0000));
		
	}
	
}
