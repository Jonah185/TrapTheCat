import edu.princeton.cs.algs4.Edge;
public class CatEdge extends Edge{
	private double weight = 1;
	public CatEdge(int to, int from){
		super(to, from , 1);
	}
	public void changeWeight(){
		weight = Double.POSITIVE_INFINITY;
	}
	public double weight(){
		return weight;
	}
}