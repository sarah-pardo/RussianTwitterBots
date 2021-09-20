
/**
 * AdjListsGraph implements the Graph interface and uses a vector of linked lists to store the arcs or edges between two nodes.
 *
 * @author (Sarah Pardo, Panya Tang)
 * @version (11/20/2019)
 */
import javafoundations.*;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import java.io.*;

public class AdjListsGraph<T> implements Graph<T>
{
    // instance variables - replace the example below with your own
    protected Vector<T> vertices;
    protected Vector<LinkedList<T>> arcs;

    /**
     * Constructor for objects of class AdjListsGraph
     */
    public AdjListsGraph()
    {
        // initialise instance variables
        vertices = new Vector<T>();
        arcs = new Vector<LinkedList<T>>();
    }

    /**
     * Returns a string representation of this graph
     */
    public String toString()
    {
        String s = ""; //Number of Vertices: " + getNumVertices() + "\n";

        for (int i = 0; i < vertices.size(); i++){
            s += "Vertex: " +vertices.get(i) +  "\t" + "Arcs: " +arcs.get(i)+"\n";
        }

        return s;
    }
    
   
    
    /** 
     * Returns a boolean indicating whether this graph is empty or not.
     * A graph is empty when it contains no vertice,a nd of course, no edges.
     *  
     *  @return true if this graph is empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return vertices.size() == 0;
    }

    /** 
     * Returns the number of vertices in this graph. 
     * 
     * @return the number of vertices in this graph
     */
    public int getNumVertices()
    {
        return vertices.size();
    }

    /** 
     * Returns the number of arcs in this graph.
     * An arc between Verteces A and B exists, if a direct connection
     * from A to B exists.
     * 
     * @return the number of arcs in this graph
     *  */
    public int getNumArcs()
    {
        int count =0 ;
        for (int i = 0; i < arcs.size(); i++)
        {
            count += arcs.get(i).size();
        }
        return count;
    }

    /** 
     * Inserts the edge between the two given vertices of this graph, if both vertices exist, 
     * else the graph is not changed
     * 
     * @param The edge to be added to this graph
     * */
    public void addEdge (T vertex1, T vertex2)
    {
        if (vertices.contains(vertex1) && vertices.contains(vertex2) && !isEdge(vertex1, vertex2) )
        {
            int index1 = vertices.indexOf(vertex1);
            arcs.get(index1).add(vertex2);
            int index2 = vertices.indexOf(vertex2);
            arcs.get(index2).add(vertex1);
        }

    }

    /** 
     * Adds the given vertex to this graph
     * If the given vertex already exists, the graph does not change
     * 
     * @param The vertex to be added to this graph
     * */
    public void addVertex (T vertex)
    {
        if (!vertices.contains(vertex))
        {
            vertices.add(vertex);
            arcs.add(new LinkedList<T>());
        }
        else{
            System.out.println("That vertex is already on the graph.");
        }
    }

    /** 
     * Returns true if an edge exists 
     * from the first vertex to the second, false otherwise
     * 
     * @return true if an edge exists between the first given vertex (vertex1),
     * and the second one (vertex2),false otherwise
     * 
     *  */
    public boolean isEdge (T vertex1, T vertex2){
        boolean result = false;
        if (vertices.contains(vertex1) && vertices.contains(vertex2) ){
            int index1 = vertices.indexOf(vertex1);
            int index2 = vertices.indexOf(vertex2);
            result = (arcs.get(index1).contains(vertex2) && arcs.get(index2).contains(vertex1));
        }
        return result;
    }

    /** 
     * Returns true if an arc (direct connection) exists 
     * from the first vertex to the second, false otherwise
     * 
     * @return true if an arc exists between the first given vertex (vertex1),
     * and the second one (vertex2),false otherwise
     * 
     *  */
    public boolean isArc (T vertex1, T vertex2){
        boolean result = false;
        if (vertices.contains(vertex1) && vertices.contains(vertex2) ){
            int index = vertices.indexOf(vertex1);
            result = arcs.get(index).contains(vertex2);
        }
        return result;
    }

    /**
     * Returns true if vertex exists within vertices vector already
     */
    public boolean isVertex(T v){
        return  (vertices.contains(v));
    }

    /** 
     * Inserts an arc between two given vertices of this graph.
     * if at least one of the vertices does not exist, the graph 
     * is not changed.
     * 
     * @param the origin of the arc to be added to this graph
     * @param the destination of the arc to be added to this graph
     * 
     *  */
    public void addArc (T vertex1, T vertex2){
        // get index of vertex 1, vertex 2
        if (vertices.contains(vertex1) && vertices.contains(vertex2) && !isArc(vertex1, vertex2) && !vertex1.equals(vertex2)){
            int index = vertices.indexOf(vertex1);
            arcs.get(index).add(vertex2);
        }

        else{
            System.out.println("That arc is already on the graph");
        }
    }

    /**
     * returns nodes in breadth first search order
     */
    public LinkedList<T> BFS(T v){
        //array to keep track of which are visited
        boolean[] visited = new boolean[vertices.size()]; //make visited array same size as vertices
        for (int i = 0; i<visited.length; i++){
            visited[i] = false; //initialize all to false
        }
        //queue to store intermediary nodes while processing
        ArrayQueue<T> queue = new ArrayQueue<T>();
        //linked list of visited in order
        LinkedList<T> result =  new LinkedList<T>();

        //enque the starting vertex
        queue.enqueue(v);
        //mark it as visited
        visited[vertices.indexOf(v)] = true;
        //add to final linked list of ordered visited vertices
        result.add(v);
        //System.out.println("Vector of vertices: " + vertices);
        while (!queue.isEmpty()){
            //System.out.println("Queue at iteration of while " + queue);
            T current = queue.dequeue();
            LinkedList<T> adj = getSuccessors(current);
            //System.out.println(adj);
            for (int i = 0; i < adj.size(); i ++){
                // find the index in vertices vector of the specific node and check if it's been visited
                //System.out.println("From adjacency linked list: " + adj.get(i));
                //System.out.println("Index in vertices vector: " + vertices.indexOf(adj.get(i)));
                if(!visited[vertices.indexOf(adj.get(i))]){
                    //enqueue the node
                    queue.enqueue(adj.get(i));
                    //System.out.println("Queue at iteration of for " + queue);
                    //mark it as visited
                    visited[vertices.indexOf(adj.get(i))] = true;
                    //System.out.println(visited);
                    //add it to our result
                    result.add(adj.get(i));
                }
            }
        }

        return result;

    }

    /**
     * returns nodes in depth first search order
     */
    public LinkedList<T> DFS(T v){
        int vertexID = vertices.indexOf(v);
        LinkedList<T> result =  new LinkedList<T>(); //make a new emtpy linked list to store traversal result
        if (!vertices.contains(v)) return result; //early return if the vertex is invalid
        
        
        //array to keep track of which are visited
        boolean[] visited = new boolean[vertices.size()]; //make visited array same size as vertices
        for (int i = 0; i<visited.length; i++){
            visited[i] = false; //initialize all to false
        }
        boolean found;
        //stack to hold intermediary node (indices) while processing
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        //linked list to be returned at the end of method call of visited nodes in DFS order 
        
        // if it is valid, push the starting vertex onto the stack
        stack.push(vertexID);
        //mark it as visited (index with visited array should be the same index as in vertices vector)
        visited[vertexID] = true;
        //add vertex to final linked list of ordered visited vertices
        result.add(v);
        
        while (!stack.isEmpty()){
            
            int currentVertexIndex = stack.peek();
            found = false;
            //go through all vertices that could be in the currentVertex's arcs list
            
            LinkedList<T> adjacentNodes = getSuccessors(vertices.get(currentVertexIndex));
            
            for (int i = 0; i < adjacentNodes.size() && !found; i ++){
                // find the index in vertices vector of the specific node and check if it's been visited
                int adjacentNodeIndex = vertices.indexOf(adjacentNodes.get(i)); //index of node inside vertices vector
                //System.out.println("Current vertexIDx: " + x + "(inside for loop)" + y++);
                if (!visited[adjacentNodeIndex]){
                    stack.push(adjacentNodeIndex);
                    result.add(adjacentNodes.get(i));
                    visited[adjacentNodeIndex] = true;
                    found = true;
                }
            }
            if (!found && !stack.isEmpty())
                stack.pop();
        }
        return result;
    }

    /** 
     * Return all the vertices, in this graph, adjacent to the given vertex.
     * 
     * @param A vertex in th egraph whose successors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from the given vertex to x (vertex -> x).
     *
     * */ 
    public LinkedList<T> getSuccessors(T vertex) {

        //Get the index of vertex in vertices
        int index = vertices.indexOf(vertex);

        //use that index to get its corresponding linked list
        return arcs.get(index);
    }

    /** 
     * Return all the vertices x, in this graph, that precede a given
     * vertex.
     * 
     * @param A vertex in the graph whose predecessors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from x to the given vertex (x -> vertex).
     * 
     * */
    public LinkedList<T> getPredecessors(T vertex){
        //look through each linked list inside the arcs vector. If the vertex is within the linked list
        //contained by the arcs index,
        // we w               ant to add the corresponding index in the vertex vector to our result linkedList 
        LinkedList<T> predecessors = new LinkedList<T>();
        for (int i = 0; i <arcs.size(); i++){
            if (arcs.get(i).contains(vertex)){
                predecessors.add(vertices.get(i));
            }
        }

        return predecessors;
    }

    /**
     * Removes given edge from this graph
     * 
     */
    public void removeEdge(T vertex1, T vertex2){
        // get index of vertex 1, vertex 2
        if (isEdge(vertex1, vertex2)){
            int index1 = vertices.indexOf(vertex1);
            arcs.get(index1).remove(vertex2);
            int index2 = vertices.indexOf(vertex2);
            arcs.get(index2).remove(vertex1);
        }

        else{
            System.out.println("There is no edge to be removed");
        }
    }

    /**
     * Removes given arc from this graph
     * 
     */
    public void removeArc(T vertex1, T vertex2){
        // get index of vertex 1, vertex 2
        if (isArc(vertex1, vertex2) && !isEdge(vertex1, vertex2)){
            int index = vertices.indexOf(vertex1);
            arcs.get(index).remove(vertex2);
        }

        else if (isEdge(vertex1, vertex2)){
            System.out.println("This connection is an edge, not an arc, please use removeEdge");
        }
        else{
            System.out.println("No arc exists between these vertices");
        }
    }

    /**
     * Removes given vertex from this graph
     * 
     */
    public void removeVertex(T vertex){
        vertices.remove(vertex);
        arcs.remove(vertex);
        for (int i = 0; i <arcs.size(); i++){
            if (arcs.get(i).contains(vertex)) arcs.get(i).remove(vertex);
        }
    }

    /** 
     * Writes this graph into a file in the TGF format.
     * 
     * @param the name of the file where this graph will be written 
     * in the TGF format.
     * */
    public void saveToTGF(String tgf_file_name) throws IOException
    {
        PrintWriter writer = new PrintWriter (new File(tgf_file_name));
        //first use for loop to add the index and name of each vertex in vertices
        // add #
        // use for loop to add the index name and the index of the vertex the arc points to in each linkedlist
        for (int i = 0; i < vertices.size(); i++)
        {
            writer.println(Integer.toString(i+ 1) + " " + vertices.get(i));
        }
        writer.println("#");
        for (int i = 0; i < arcs.size(); i++)
        {
            //System.out.println(i);
            for (int j = 0; j <arcs.get(i).size(); j++)
            {
                writer.println(Integer.toString(i + 1) + " " + (vertices.indexOf(arcs.get(i).get(j)) + 1));
                //System.out.println(arcs.get(i));
            }
        }
        writer.close();
    }

    /** 
     * Returns true if the graph is undirected, that is, for every
     * pair of nodes i,j for which there is an arc, the opposite arc
     * is also present in the graph, false otherwise.  
     * 
     * @return true if the graph is undirected, false otherwise
     * */
    public boolean isUndirected(){
        boolean result = true; // should be true ?? or sttart iwht false??
        for (int i = 0; i < vertices.size(); i++){
            LinkedList<T> pre = getPredecessors(vertices.get(i));
            LinkedList<T> suc = getSuccessors(vertices.get(i));
            if (pre.size() == suc.size()){
                for (int j = 0; j < pre.size(); j++){
                    if (!suc.contains(pre.get(j))) return false; 
                }
            } else return false;
        }
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println("***Testing Empty Graph***\n");
        AdjListsGraph<String> e = new AdjListsGraph<String>(); //1 node
        AdjListsGraph<String> g = new AdjListsGraph<String>(); // 4 nodes
        AdjListsGraph<String> f = new AdjListsGraph<String>(); // 7 nodes
        AdjListsGraph<String> h = new AdjListsGraph<String>(); //8 nodes
        System.out.println("------------Creating 1 node graph------------");
        e.addVertex("A");
        System.out.println(e);
        try{
            e.saveToTGF("1NodeGraph.tgf");
        } catch(IOException x){
            System.out.println(x);
        }

        System.out.println("------------Creating 4 node directed graph with 1 cycle------------");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("E");
        g.addArc("B", "C");
        g.addArc("C", "D");
        g.addArc("D", "E");
        g.addArc("E", "B");
        System.out.println(g);
        try{
            g.saveToTGF("4NodeCycle.tgf");
        } catch(IOException x){
            System.out.println(x);
        }

        System.out.println("------------Creating 7 node tree------------");
        f.addVertex("z");
        f.addVertex("y");
        f.addVertex("x");
        f.addVertex("w");
        f.addVertex("v");
        f.addVertex("u");
        f.addVertex("t");
        f.addEdge("z", "y");
        f.addEdge("z", "x");
        f.addEdge("y", "w");
        f.addEdge("y", "v");
        f.addEdge("x", "u");
        f.addEdge("x", "t");
        System.out.println(f);
        try{
            f.saveToTGF("7NodeTree.tgf");
        } catch(IOException x){
            System.out.println(x);
        }

        System.out.println("------------Creating 8 node disconnected, undirected graph with 2 components------------");
        h.addVertex("H");
        h.addVertex("I");
        h.addVertex("J");
        h.addVertex("K");
        h.addVertex("L");
        h.addVertex("O");
        h.addVertex("M");
        h.addVertex("N");
        h.addEdge("J", "H");
        h.addEdge("I", "H");
        h.addEdge("I", "J");
        h.addEdge("I", "M");
        h.addEdge("K", "L");
        h.addEdge("L", "O");
        h.addEdge("O", "K");
        h.addEdge("K", "N");
        System.out.println(g);
        try{
            h.saveToTGF("8NodeDisconnected.tgf");
        } catch(IOException x){
            System.out.println(x);
        }
        System.out.println(f.getSuccessors("z"));
        System.out.println(f.DFS("z"));
    }
}
