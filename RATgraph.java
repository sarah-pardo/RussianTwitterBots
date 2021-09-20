
/**
 * class RATgraph takes in a csv file with the twitter user id's and associated stories and 
 * produces nodes which represent each user and each story. Edges are created between connected
 * users and stories, but tere is no intraconnection between groups of users nor groups of stories. 
 * Once all of the vectors and edges are created, a tgf file can be generated.
 * 
 * @author (Sarah Pardo, Panya Tang)
 * @version (11/25/19)
 */
import java.util.Scanner;

import javafoundations.*;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Hashtable;
import java.util.List;

public class RATgraph //extends Comparable<T>
{
    // instance variables - replace the example below with your own
    private AdjListsGraph<RATvertex> bipartite;
    private int totalUsers;
    private int totalStories;
    /**
     * Constructor for objects of class RATgraph
     */
    public RATgraph() 
    {
        bipartite = new AdjListsGraph<RATvertex>();
        totalUsers = 0;
        totalStories = 0;
        
    }

    /**
     * Returns a string representation of this graph
     */
    public String toString()
    {
        String s = "RATgraph summary: \n";
        s += "Number of total vertices: " + bipartite.getNumVertices() + "\n";
        s += "Total user vertices: " + totalUsers;
        s += " Total story vertices: " + totalStories + "\n";
        s+= bipartite.toString();
        return s;
    }
    
    /**
     * returns bipartite AdjListsGraph
     * @ return bipartite
     */
    public AdjListsGraph<RATvertex> getBipartite(){
        return this.bipartite;
    }
    
    /**
     * returns total user RATvertex count
     * @ return totalUsers
     */
    public Integer getTotalUsers(){
        return this.totalUsers;
    }
    
    /**
     * returns total story RATvertex count;
     * @ return totalStories
     */
    public Integer getTotalStories(){
        return this.totalStories;
    }
    
    /**
     * returns a matching vertex given the id
     * @ return RATvertex that matches correctly
     */
    public RATvertex findVertexFromId(String id){
        for (int i = 0; i < this.bipartite.vertices.size(); i++){
            if (this.bipartite.vertices.get(i).getId().equals(id))
                return this.bipartite.vertices.get(i);
        }
        return null;
    }

    /**
     * creates user and story RATvertices from a csv.tsv file, adds them to the bipartite graph, 
     * makes corresponding edges
     * @param String inputFile - the name of the csv.tsv file
     * @return none
     */
    public void readFile (String inputFile){
        //intermediate data structure holder contains information about what vertices have been seen
        //this allows us to circumvent the double for loop check, which is inefficient
        Hashtable<String, Integer> holder = new Hashtable<String, Integer>(); 
        try{
            Scanner wholeFile = new Scanner(new File(inputFile));
            wholeFile.nextLine(); //skip header
            int lineCount = 0;

            while (wholeFile.hasNextLine()){
                String newLine = wholeFile.nextLine();
                Scanner line = new Scanner(newLine);

                lineCount++;
                //System.out.println("NEW ACCOUNT # " + lineCount + ": "+newLine);

                try{ 
                    //line.useDelimiter("\t");
                    String userName = line.next();
                    String id = line.next();
                    //Integer idInt = Integer.valueOf(id); 
                    int tweetNum = line.nextInt();
                    int storyNum = line.nextInt();
                    RATvertex userID = new RATvertex (id, true, userName, tweetNum, storyNum);
                    //System.out.println(userID.toStringUser());
                    //store the new RATvertex id info inside the holder hashtable
                    this.bipartite.addVertex(userID);
                    holder.put(id, 0);
                    //add the entire vertex (all of the info) to the bipartite graph
                    
                    this.totalUsers++; //update user count


                    String remainderLine = line.next();
                    //System.out.println("Rest of the line: " + remainderLine);
                    String[] storyIds = remainderLine.split(",");
                    String current;
                    //int currentInt;
                    for (int storyIdx = 0; storyIdx < storyIds.length; storyIdx++){

                        current = storyIds[storyIdx];
                        //currentInt = Integer.parseInt(current);
                        //System.out.println(current );

                        //check if the current id is already within the hashtable holder

                        //if it is, just create an edge between the user ID and the existing story
                        if (!holder.containsKey(current)){

                            //System.out.println("CURRENT is NOT inside holder");
                            RATvertex newStory = new RATvertex(current, false);
                            
                            this.bipartite.addVertex(newStory);
                            holder.put(current, 0);
                            this.totalStories++; //update story count
                            this.bipartite.addEdge(userID, newStory);//need to find the correct story index to make an edge to 
                            userID.increaseEdgeCount();
                            newStory.increaseEdgeCount();
                        }
                        //if it isn't, make a new story RATvertex and then make an edge to the user ID
                        else{
                            // ie. need to find the vertex based on the the current identification
                            //use findVertexFromID method - only loop when encountering an existing id
                            //System.out.println("CURRENT is already inside holder");
                            this.bipartite.addEdge(userID, this.findVertexFromId(current));
                            userID.increaseEdgeCount();
                            this.findVertexFromId(current).increaseEdgeCount();
                        }

                    }
                    line.close();       
                }catch(InputMismatchException e){
                    System.out.println(e);

                }

            }  

            wholeFile.close();
        } catch (IOException x){
            System.out.println(x);
        }
        //System.out.println(holder.values());
    }
    
    /**
     * returns the top 10 most active users; i.e. the actors with the greatest story count
     * @return List<RATvertex> top 10 active users
     */
    public List<RATvertex> mostActiveUsers(){
         //make a vector to store the active users; first 10 will be returned in a list at the end
        Vector<RATvertex> activeUsers = new Vector<RATvertex>();
        
        
        HeapSort<RATvertex> heapUsers = new HeapSort<RATvertex>();
        //make a vector to pass through the sortInDescending method
        Vector<RATvertex> usersToSort = new Vector<RATvertex>() ;
        //add only the users to usersToSort
        for (int i = 0; i< this.bipartite.vertices.size(); i++ ){
            if (this.bipartite.vertices.get(i).getIsUser())
                usersToSort.add(this.bipartite.vertices.get(i));
        }
        
        activeUsers = heapUsers.sortInDescending(usersToSort);
        //get the edge count, get the corresponding id value using the edge count as the key
        //get the vertex using the id, add it to the final vector
        
        List<RATvertex> topTen; 
        if (activeUsers.size() >=10)
            topTen = activeUsers.subList(0,10);
        else 
            topTen = activeUsers.subList(0,activeUsers.size());
        return topTen;
    }
    
    /**
     * returns a list of the 10 most popular stories in descending order. Most popular is defined as having the 
     * highest number of arcs, thereby being a story participated in by many users.
     * @return List<RATvertex> top 10 most popular stories
     */
    public List<RATvertex> mostPopularStories(){
         //make a vector to store the popular stories; first 10 will be returned in a list at the end
        Vector<RATvertex> popularStories = new Vector<RATvertex>();
        
        
        HeapSort<RATvertex> heapUsers = new HeapSort<RATvertex>();
        //make a vector to pass through the sortInDescending method
        Vector<RATvertex> storiesToSort = new Vector<RATvertex>() ;
        //add only the users to usersToSort
        for (int i = 0; i< this.bipartite.vertices.size(); i++ ){
            if (!this.bipartite.vertices.get(i).getIsUser())
                storiesToSort.add(this.bipartite.vertices.get(i));
        }
        
        popularStories = heapUsers.sortInDescending(storiesToSort);
        //get the edge count, get the corresponding id value using the edge count as the key
        //get the vertex using the id, add it to the final vector
        
        List<RATvertex> topTen; 
        if (popularStories.size() >=10)
            topTen = popularStories.subList(0,10);
        else 
            topTen = popularStories.subList(0,popularStories.size());
        return topTen;
    }
    
    /**
     * returns the IDs of the two nodes which are furthest apart and the number of steps between them
     * @return String summary of nodes and distance
     */
    public String furthestApart(){
        int furthest = 0;
        LinkedList<RATvertex> steps = null;
        RATvertex start = null;
        RATvertex end = null;

        for (int i = 0; i< this.bipartite.vertices.size(); i++){
            steps = this.bipartite.BFS(this.bipartite.vertices.get(i));
            if (steps.size() > furthest){
                furthest = steps.size();
                start = steps.getFirst();
                end = steps.getLast();
            }
        }

        String result = "The 2 nodes that are furthest apart are " + start.toStringUser() + " and " + end.toStringUser() + " and they are ";
        result += steps.size() + " steps apart.";
        return result;
    }
    
    /**
     * makes a new file to save tgf info to
     * @param outFileName the name of the new file
     * @return none
     */
    public void makeFile(String outFileName){
        try{
            PrintWriter writer = new PrintWriter (new File(outFileName));       
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    /**
     * testing for the RATgraph class using a trimmed version of the 
     * "All_Russian-Accounts-in-TT-stories.csv.tsv" file
     * 
     */
    public static void main (String [] args){
        RATgraph test = new RATgraph ();
        
        test.makeFile("test.tgf");

        test.readFile("TEST.csv.tsv");
        try{
            test.bipartite.saveToTGF("test.tgf");
        } catch(IOException x){
            System.out.println(x);
        }
        
        
        System.out.println(test);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("Most active users:");
        List<RATvertex> actUsers = test.mostActiveUsers();
        for (int t = 0; t < actUsers.size(); t++){
            System.out.println(actUsers.get(t).toStringUser());
        }
        
        System.out.println("Most popular stories: \n" + test.mostPopularStories());
        System.out.println(test.furthestApart());
     
        
    }
}
