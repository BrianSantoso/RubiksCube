
public class Cublet implements CubeComponent {

	private Face[] faces;
	
	public Cublet(){
		
		
		
	}
	
	@Override
	public void applyTransformation(Matrix transformation){
		
		for(Face f : faces)
			f.applyTransformation(transformation);
		
	}
	
}
