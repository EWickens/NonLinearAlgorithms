package com.company;
// Name: Eoin Wickens
// StudentID: R00151204
// email: eoin.wickens@mycit.ie
import java.util.LinkedList;

/*
 *  Implementation of the interface Graph with adjacency matrix.
*/

 
public class GraphAdjMatrix implements Graph{

	// ATTRIBUTES:
    private int[][] adjMatrix;
    private int numVertices;
    private int numEdges;
    private boolean directed;
    
    // CONSTRUCTOR: Creates a directed/undirected graph with V vertices and no edges
    public GraphAdjMatrix(int V, boolean directed) {
        this.numVertices = V;
        this.directed = directed;
        adjMatrix = new int[numVertices][numVertices];
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


   //  3. IMPLEMENTATION METHOD addEdge:
    //TODO Add in weight update for edge
    public void addEdge(int v1, int v2, int w) {
        if(oobCheck(v1, v2)){
            return;
        }

        if(this.adjMatrix[v1][v2] == 0){ //case for edge already existing and weight not being 0
            this.numEdges += 1;
            this.adjMatrix[v1][v2] = w;
        }
        else{
            this.adjMatrix[v1][v2] = w;
        }

        //Add the edge in the other direction
        if(!this.directed){
            if(this.adjMatrix[v2][v1] == 0){ //case for edge already existing and weight not being 0
                this.numEdges += 1;
                this.adjMatrix[v2][v1] = w;
            }
            else{
                this.adjMatrix[v1][v2] = w;
            }

        }

    }
    //Checks to ensure that the vertice is within bounds
    public boolean oobCheck(int v1){
        if(v1 >= this.numVertices){
            System.out.println("Vertex is out of bounds: " + v1);
            return true;
        }
        return false;
    }
    //Checks to ensure that the vertices are within bounds using overloading
    public boolean oobCheck(int v1, int v2){
        if(oobCheck(v1) || oobCheck(v2)){
            return true;
        }
        return false;
    }


   // 4. IMPLEMENTATION METHOD removeEdge:
   public void removeEdge (int v1, int v2)
   {
       if(oobCheck(v1, v2)){
           return;
       }

       this.adjMatrix[v1][v2] = 0;
       this.numEdges -= 1;

       if(!this.directed){
           this.adjMatrix[v2][v1] = 0;
           this.numEdges -= 1;
       }
   }

    // 5. IMPLEMENTATION METHOD hasEdge:
    public boolean hasEdge(int v1, int v2) {
        if(oobCheck(v1, v2)){
            return false;
        }
        int exists = 0;
        if(this.adjMatrix[v1][v2] != 0){
            return true;
        }
        else if(!this.directed && this.adjMatrix[v2][v1] != 0){
            return true;
        }

        return false;
    }
    
    // 6. IMPLEMENTATION METHOD getWeightEdge:

    //TODO DO I NEED TO PUT IN CHECK FOR DIRECTED HERE?
	public int getWeightEdge(int v1, int v2) {
        if(oobCheck(v1, v2)){
            return 0;
        }
		return this.adjMatrix[v1][v2];
	}


	// 7. IMPLEMENTATION METHOD getNeighbors:
	public LinkedList getNeighbors(int v)
	{
        if(oobCheck(v)){
            return null;
        }
        LinkedList<Edge> retlist = new LinkedList<Edge>();

        for(int i = 0; i < this.numVertices; i++){
            if(this.adjMatrix[v][i] != 0){
                retlist.add(new Edge(i, this.adjMatrix[v][i]));
            }
        }

        return retlist;
	}
   	
	// 8. IMPLEMENTATION METHOD getDegree:
    // Degree is how many times a vertext is used within edges
	public int getDegree(int v) 
	{
        if(oobCheck(v)){
            return 0;
        }

	    int retval = 0;
        for(int i = 0; i < this.numVertices; i++){
            if(this.adjMatrix[v][i] != 0){
                retval += 1;
            }
            if(this.adjMatrix[i][v] != 0){
                retval += 1;
            }
        }
        return retval;
	}
	

	// 9. IMPLEMENTATION METHOD toString:
   	public String toString()
    {
        String resstring = "";

        for(int i = 0; i < this.numVertices; i++){
            resstring += i + ": ";
            for(int j = 0; j < this.numVertices; j++)
                resstring += "[" + this.adjMatrix[i][j] + "] ";
            resstring += "\n";
        }
        return resstring;
    }    
}