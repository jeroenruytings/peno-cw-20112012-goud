package mainController;

import mainController.MainController.Orientation;

/*************************************************************************
 *  Compilation:  javac EuclideanGraph.java
 *  Execution:    java EuclideanGraph
 *  Dependencies: In.java IntIterator.java
 *  
 *  Undirected graph of points in the plane, where the edge weights
 *  are the Euclidean distances.
 *
 *************************************************************************/


public class EuclideanGraph {

    private int V;            // number of vertices
    private int E;            // number of edges
    private Node[]  adj;      // adjacency lists
    private Square[] square;
    private int rows;
    private int columns;
    
    // node helper class for adjacency list
    private static class Node {
        int v;
        Node next;
        Node(int v, Node next) { this.v = v; this.next = next; }
    }

    // iterator for adjacency list
    public class AdjListIterator{
        private Node x;
        AdjListIterator(Node x)  { this.x = x; }
        public boolean hasNext() { return x != null; }
        public int next() { 
            int v = x.v;
            x = x.next;
            return v;
        }
    }


   /*******************************************************************
    *  Read in a graph from a file, bare bones error checking.
    *  V E
    *  node: id x y
    *  edge: from to
    *******************************************************************/
    public EuclideanGraph(Square[][] vakjes) {
        rows = vakjes.length;
        columns = vakjes[0].length;
        square = new Square[rows * columns];
        adj = new Node[rows * columns];
        
        for (int i = 0; i < rows; i++){
        	for (int j = 0; j < columns; j++){
        		square[i*columns + j] = vakjes[i][j];
        		V++;
        		for(Orientation orientation:Orientation.values()){
        			if (i + orientation.getXPlus() < 0 || i + orientation.getXPlus() >= rows || 
        					j + orientation.getYPlus() < 0 || j + orientation.getYPlus() >= columns){
        				continue;
        			}
        			Square anderVakje = vakjes[i + orientation.getXPlus()][j + orientation.getYPlus()];
        			if (Maze.wallBetween(vakjes[i][j],anderVakje) == 2){
        				int v = i*columns + j;
        				int w = anderVakje.getXCoordinate() * columns + anderVakje.getYCoordinate();
        				if (adj[v] != null){
        					Node current = adj[v];
        					while(current.next != null)
        						current = current.next;
        					current.next = new Node(w,adj[v]);
        				}
        				else
        					adj[v] = new Node(w,adj[v]);
        				if (adj[w] != null){
        					Node current = adj[w];
        					while(current.next != null)
        						current = current.next;
        					current.next = new Node(v,adj[w]);
        				}
        				else
        					adj[w] = new Node(v,adj[w]);
        				E++;
        			}
        		}
        	}
        }
        
        
    }


    // accessor methods
    public int V() { return V; }
    public int E() { return E; }

    // return iterator for list of neighbors of v
    public AdjListIterator neighbors(int v) {
        return new AdjListIterator(adj[v]);
    }

    /**
     * 
     * @param v
     * @param w
     * @pre	Squares v and w are adjacent
     * @return
     */
	public double distance(Square v, Square w) {
		if(Maze.wallBetween(v, w) == 0)
			return Double.POSITIVE_INFINITY;
		if(Maze.wallBetween(v, w) == 1)
			return 3;
		return 1;
		
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Square getSquare(int i) {
		return square[i];
	}

}