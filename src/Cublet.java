import java.util.ArrayList;

public class Cublet implements CubeComponent {

	private ArrayList<Face> faces;
	
	public Cublet(ArrayList<Face> faces){
		
		this.faces = faces;
		
	}
	
	@Override
	public void applyTransformation(Matrix transformation){
		
		for(Face f : faces)
			f.applyTransformation(transformation);
		
	}
	
}
