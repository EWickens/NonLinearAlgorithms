package com.company;
// Name: Eoin Wickens
// StudentID: R00151204
// email: eoin.wickens@mycit.ie
import java.util.LinkedList;
import java.util.Iterator;

	/**
	* Graph implementation that uses Adjacency Lists to store edges. It
	* contains one linked-list for every vertex i of the graph. The list for i
	* contains one instance of VertexAdjList for every vertex that is adjacent to i.
	* For directed graphs, if there is an edge from vertex i to vertex j then there is
	* a corresponding element in the adjacency list of node i (only). For
	* undirected graphs, if there is an edge between vertex i and vertex j, then there is a
	* corresponding element in the adjacency lists of *both* vertex i and vertex j. The
	* edges are not sorted; they contain the adjacent nodes in *order* of
	* edge insertion. In other words, for a graph, the node at the head of
	* the list of some vertex i corresponds to the edge involving i that was
	* added to the graph least recently (and has not been removed, yet). 
	*/

	public class GraphAdjList  implements Graph {

	// ATTRIBUTES: 
	private int numVertices;
	private int numEdges;
	private boolean directed;
	private LinkedList<Edge> adjLists[];
	 //TO-DO

	 /*
	  * CONSTRUCTOR: Creates a directed/undirected graph with V vertices and no edges.
	  * It initializes the array of adjacency edges so that each list is empty.
	  */
	    
	 public GraphAdjList(int V, boolean directed) {
			this.directed = directed;
			this.numVertices = V;
		 	this.adjLists = new LinkedList[numVertices];

		 	for (int i = 0; i < this.numVertices; i++)
				 this.adjLists[i] = new LinkedList<Edge>();
	 }

	  // 1. IMPLEMENTATION METHOD numVerts: 
	  public int numVerts() {
     	return this.numVertices;
     }

	  // 2. IMPLEMENTATION METHOD numEdges:
	  //If the number of edges is an odd number, add 1 to the value and then return the value by half
	  //if the graph is set to be non-directional
	  public int numEdges() {
	 		if(this.directed) {
	 			return this.numEdges;
			} else {
	 			if (this.numEdges % 2 == 1) {
	 				return this.numEdges/2 + 1;
				}
	 			return this.numEdges/2;
			}
	  }

		//Checks to ensure that the vertices are within bounds
		public boolean oobCheck(int v1){
			if(v1 >= this.numVertices){
				System.out.println("Vertex is out of bounds: " + v1);
				return true;
			}
			return false;
		}

		public boolean oobCheck(int v1, int v2){
			if(oobCheck(v1) || oobCheck(v2)){
				return true;
			}
			return false;
		}
	    
	  //  3. IMPLEMENTATION METHOD addEdge:
	  public void addEdge(int v1, int v2, int w) {
		  if(oobCheck(v1, v2)){
			  return;
		  }

		  int existsindex = verifyEdgePresent(v1, v2);

		  if(existsindex  != -1){
			  adjLists[v1].get(existsindex).setWeight(w);
			  return;
		  }

		  adjLists[v1].add(new Edge(v2, w));
		  this.numEdges += 1;

		  if(!this.directed){
			  addEdge(v2, v1, w);
		  }

	 }
	 //Verfies the edge is present, returns -1 if not present
		// returns index if exists
	private int verifyEdgePresent(int v1, int v2){
		int index = -1;

		for(int i = 0; i < adjLists[v1].size(); i++){
			//if V2 is found within the adjlist of V1 return the vertex
			if(v2 == adjLists[v1].get(i).getVertex()){
				index = i;
				break;
			}
		}
		return index;
	}
	 // 4. IMPLEMENTATION METHOD removeEdge: 
	 public void removeEdge(int v1, int v2) {
		 if(oobCheck(v1, v2)){
			 return;
		 }
	 	int checkvarindex = verifyEdgePresent(v1, v2);

	 	if(checkvarindex > -1){
			adjLists[v1].remove(checkvarindex);
			this.numEdges--;
		}
		else{
			System.out.println(String.format("Edge V%d,V%d does not exist: ", v1, v2));
			return;
		}
		 if(!this.directed){
			 removeEdge(v2, v1);
		 }
	 }
	 
	 // 5. IMPLEMENTATION METHOD hasEdge:
		//Leverages the my verifyEdgePresent function to perform duties
	 public boolean hasEdge(int v1, int v2) {
		 int checkvarindex = verifyEdgePresent(v1, v2);

		 return checkvarindex > -1;
	 }

	// 6. IMPLEMENTATION METHOD getWeightEdge:
	 public int getWeightEdge(int v1, int v2) {
		 int checkvarindex = verifyEdgePresent(v1, v2);

		 if(checkvarindex > -1){
			 return this.adjLists[v1].get(checkvarindex).getWeight();
		 }
		 else{
			 System.out.println(String.format("Edge V%d,V%d does not exist: ", v1, v2));
			 return 0;
		 }

	 }

	// 7. IMPLEMENTATION METHOD getNeighbors:
	 public LinkedList getNeighbors(int v) {

		 if(oobCheck(v)){
			 return null;
		 }

		 LinkedList<Edge> lneighbours = new LinkedList<Edge>();

		 // iterates through the number of vertices and checks if an edge is present.
		 for(int i = 0; i < this.numVertices; i++){
			 Edge neighbour = null;

			 int checkindex;

			 checkindex = verifyEdgePresent(v, i);

			 if(checkindex != -1){
				 neighbour = this.adjLists[v].get(checkindex);
			 }

			 checkindex = verifyEdgePresent(i, v);

			 if(!this.directed && checkindex != -1){
				 neighbour = new Edge(i, adjLists[i].get(checkindex).getWeight());
			 }

			 //  If the edge is present it adds the edge to the lneightbours linkedlist
			 if(neighbour != null){
				 lneighbours.add(neighbour);
			 }
		 }

		 return lneighbours;
	 }

    // 8. IMPLEMENTATION METHOD getDegree:
	public int getDegree(int v) {
		if(oobCheck(v)){
			return 0;
		}

	 	int retval = 0;

		//iterates through all vertex linked lists looking for the presence of an edge to vertex V
		for(int k = 0; k < this.numVertices; k++){
			for (int i = 0; i < this.adjLists[k].size(); i++) {

				if (this.adjLists[k].get(i).getVertex() == v) {
					retval += 1;
				}

				//If the iterator k is the same as the input parameter V, count how many edges it has
				//providing that they do not include the number v as they would be accounted for above.
				if(k == v){
					if(this.adjLists[v].get(i) != null && this.adjLists[v].get(i).getVertex() != v) {
						retval++;
					}
				}
			}
		}

	 	return retval;
	}

	// 9. IMPLEMENTATION METHOD toString:
	 public String toString() {
	 	String resstring = "\n";

		 for(int i = 0; i < this.numVertices; i++){
			 LinkedList<Edge> vert = this.adjLists[i];
			 resstring += "Vertex: " + i + "\n";
			 for (Edge edge : vert) {
				 resstring += ("\t[" + "V:" + edge.getVertex() + ",W:" + edge.getWeight() + "],");
			 }
			 resstring += "\n";
		 }
		 return resstring;
	 }

}


