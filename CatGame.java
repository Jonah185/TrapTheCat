import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import java.util.Random;
public class CatGame{
  //The length of the board
	private int n;
  //The integer representation of the vertex the cat is trying to reach
	private int FREEDOM;
  //The integer representation of the vertex the cat is currently on
	private int s;
  //The array that stores what tiles have been marked
	boolean[] marked;
	EdgeWeightedGraph G;
	DijkstraUndirectedSP SP;
  /*
  * A CatGame is contructed as an n by n grid of vertices that constitute the board and an additional vertex that represents a successful escape.
  * The rows are offset to make a hexogonal like structure and the edges between them are constructed as follows:
  * All vertices not on the exterior are connected to the nodes on their left and right as well as in the row above and below them.
  * Vertices not on the exterior are also connected to two additional vertices whose integer representaion differs if they are in an odd or even row.
  * On only even rows we connect each vertex with the vertex above and to the left as well as below and to the left. This assures that all
  * of the necessary connections are created and are created exactly once.

  * Vertices on the edge are then connected to the "FREEDOM" vertex and only the "FREEDOM" vertex.
  * All connections from the inside out have already been made and the exterior vertices do not need to be connected to each other because
  * the shortest path is always directly to the "FREEDOM" vertex

  * Lastly, a random number of  random vertices from [0-n) are marked and a Shortest path with the starting vertex (n/2, n/2) as the source is created
  */
	public CatGame(int n){
		this.n = n;
		FREEDOM = n*n;
		marked = new boolean[n*n];
		s = getIndex(n/2,n/2);
		G = new EdgeWeightedGraph(FREEDOM + 1);
		for(int row = 1; row < n-1; row++){
			for(int col = 1; col < n-1; col++){
            int v = getIndex(row, col);
            G.addEdge(new CatEdge(v, v - 1));
            G.addEdge(new CatEdge(v, v + 1));
            G.addEdge(new CatEdge(v, v + n));
            G.addEdge(new CatEdge(v, v - n));
			if(row % 2 == 0){			
				G.addEdge(new CatEdge(v, v + n - 1));
				G.addEdge(new CatEdge(v, v - n - 1));
			}
			marked[v] = false;
			}
		}
		for(int i = 0; i < n; i++){
			int v = getIndex(0,i);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(n-1, i);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(i, 0);
			G.addEdge(new CatEdge(v, FREEDOM));
			v = getIndex(i, n-1);
			G.addEdge(new CatEdge(v, FREEDOM));
			marked[v] = false;
		}
		Random rand = new Random();
		int num = rand.nextInt(n);
		for(int i = 0; i < n; i ++){
			startMarkTile(rand.nextInt(n), rand.nextInt(n));
		}
		SP = new DijkstraUndirectedSP(G, s);
	}

	/*
	 * Takes two ints for row and column as parameters, marks the corresponding vertex and moves the cat along the newly created shortest path. 
	 * between it's current location and the "FREEDOM" vertex. Marking a vertex has two steps:
	 * 
	 * First, change the weight of all of the edges adjacent to the vertex to infinity to effectively remove it from the shortest path tree.
	 * Second, update the marked array to account for the newly marked edge.
	 * 
	 * Then, the cat is moved by constructing the new shortest path tree and setting the cat's position to be the other vertex of the first edge of the shortest
	 * path between s and the "FREEDOM" vertex.
	 */
	public void markTile(int row, int col){
		int v = getIndex(row, col);
			for(Edge i : G.adj(v)){
			CatEdge c = (CatEdge) i;
			c.changeWeight();
		}
		SP = new DijkstraUndirectedSP(G, s);
    	if(SP.distTo(FREEDOM) == Double.POSITIVE_INFINITY){
     		CatIsTrapped = true;
    	}
		else{
    		CatEdge e = (CatEdge) SP.pathTo(FREEDOM).iterator().next();
			s = e.other(s);
			marked[v] = true;
    	}
	}

	/*
	 * Does the same thing as markTile except it doesnt move the cat.
	 */
	private void startMarkTile(int row, int col){
		int v = getIndex(row, col);
		for(Edge i : G.adj(v)){
			CatEdge c = (CatEdge) i;
			c.changeWeight();
		}
		marked[v] = true;
	}

	/*
	 * Takes two ints as parameteres for the row and column and returns the corresponing index of the marked array.
	 */
	public boolean marked(int row, int col){
		return marked[getIndex(row, col)];
	}
	/*
	 * Takes two ints as parameters for the row and column and returns the index representation of the vertex.
	 */
	private int getIndex(int row, int col){
		return n * row + col;
	}
	/*
	 * Takes no parameters and returns an int array containing the row in index 0 and the column in index 1.
	 */
	public int[] getCatTile(){
		return new int[]{s/n,s%n};
		
	}	
	/*
	 * Returns true if the cat's current position is the "FREEDOM" vertex and false otherwise.
	 */
	public boolean catHasEscaped(){
		return s == FREEDOM;
	}
}
