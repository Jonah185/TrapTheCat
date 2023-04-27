import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
public class CatGame{
	private int n;
	private int FREEDOM;
	private int s;
	boolean[] marked;
	EdgeWeightedGraph G;
	DijkstraUndirectedSP SP;
	public CatGame(int n){
		this.n = n;
		FREEDOM = n*n;
		marked = new boolean[n*n];
		System.out.println(n);
		s = getIndex(n/2,n/2);
		System.out.println(s);
		
		G = new EdgeWeightedGraph(FREEDOM + 1);
		for(int row = 1; row < n-1; row++){
			for(int col = 1; col < n-1; col++){
				int v = getIndex(row, col);
				G.addEdge(new CatEdge(v, v - 1));
				G.addEdge(new CatEdge(v, v + 1));
				G.addEdge(new CatEdge(v, v + n));
				G.addEdge(new CatEdge(v, v + n + 1));
				G.addEdge(new CatEdge(v, v - n + 1));
				G.addEdge(new CatEdge(v, v - n));
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
		SP = new DijkstraUndirectedSP(G, s);
	}
	
	public void markTile(int row, int col){
		int v = getIndex(row, col);
			for(Edge i : G.adj(v)){
			CatEdge c = (CatEdge) i;
			c.changeWeight();
		}
		SP = new DijkstraUndirectedSP(G, s);
		CatEdge e = (CatEdge) SP.pathTo(FREEDOM).iterator().next();
		s = e.other(s);
		marked[v] = true;
	}
	public void startMarkTile(int row, int col){
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
		return SP.distTo(FREEDOM) == Double.POSITIVE_INFINITY;
	}
}
