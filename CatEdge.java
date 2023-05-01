import edu.princeton.cs.algs4.Edge;
/*
CatEdge extends Sedgewick's Edge.java class and is identical except for it's weight is always one and it has an additional method, the changeWeight() method.
The changeWeight() method takes no parameters and simply changes the CatEdge object's weight to be infinity
*/
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
