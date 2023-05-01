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
  A CatGame is contructed as an n by n grid of vertices that constitute the board and an additional vertex that represents a successful escape.
  The rows are offset to make a hexogonal like structure and the edges between them are constructed as follows:
  All vertices not on the edge are connected to the nodes on their left and right as well as in the row above and below them.
  Vertices not on the edge are also connected to two additional vertices whose integer representaion differs if they are in an odd or even row.
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
			else{
				G.addEdge(new CatEdge(v, v + n + 1));
				G.addEdge(new CatEdge(v, v - n + 1));
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
	private void startMarkTile(int row, int col){
		marked[getIndex(row, col)] = true;
		int v = getIndex(row, col);
			for(Edge i : G.adj(v)){
			CatEdge c = (CatEdge) i;
			c.changeWeight();
		}
	}
	public boolean marked(int row, int col){
		return marked[getIndex(row, col)];
	}
	private int getIndex(int row, int col){
		return n * row + col;
	}
	public int[] getCatTile(){
		return new int[]{s/n,s%n};
		
	}	
	public boolean catHasEscaped(){
		return s == FREEDOM;
	}
	public boolean catIsTrapped(){
		return CatIsTrapped;
	}
}
