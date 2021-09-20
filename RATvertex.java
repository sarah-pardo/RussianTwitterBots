
/**
 * class RATvertex contains a constructor to make vertices which will be used by the RATgraph
 * class
 *
 * @author (Sarah Pardo, Panya Tang)
 * @version (11/25/19)
 */
import javafoundations.*;
public class RATvertex implements Comparable<RATvertex>
{
    // instance variables - replace the example below with your own
    private boolean isUser;
    private String identification;
    private String screenName;
    private int tweetCount;
    private int storyCount;
    private int edgeCount;
    /**
     * Constructor for objects of class RATvertex
     */
    public RATvertex(String id, boolean userStory)
    {
        identification = id;
        isUser = userStory;
        edgeCount = 0;
    }
    
    /**
     * Constructor for objects of class RATvertex
     */
    public RATvertex(String id, boolean userStory, String userName, int tweets, int stories)
    {
        this.identification = id;
        this.isUser = userStory;
        this.screenName = userName;
        this.tweetCount = tweets;
        this.storyCount = stories;
        edgeCount = 0;
    }
    
    
    /**
     * Returns a nicely formatted version of a RATvertex in a string
     * @return String nicely formatted RATvertex representation
     */
    public String toString(){
        String s = "";
        if (isUser)
            s = "User " + identification;
        else
            s = "Story " + identification;
           return s;
    }
    
    /**
     * Returns a nicely formatted version of a user RATvertex in a string - is specialized for user vertices which have more information
     * @return String nicely formatted RATvertex representation
     */
    public String toStringUser(){
        String s = "";
        if (isUser){
            s = "User " + screenName + " (id:" + identification + ")" + "\n" ;
            s+= "tweet count: " + tweetCount + "\n" + "story count: " + storyCount;
        }
           return s;
    }
    
    /**
     * compares edge counts
     * @ return integer; positive indicates this > other, negative this < other, 0 equality
     */
    public int compareTo(RATvertex otherRAT){
        return this.edgeCount - otherRAT.edgeCount;
    }
    
    /**
     * getId gets RATvertex identification
     * @return identification
     */
    public String getId(){
        return identification;
    }
    
    /**
     * gets the edge count
     * @return edgeCount
     */
    public int getEdgeCount(){
        return this.edgeCount;
    }
    
    /**
     * getIsUser gets boolean associated with isUser
     * @return isUser
     */
    public boolean getIsUser(){
        return isUser;
    }
    
    /**
     * increases the edge count by 1
     * @param none
     * @return none
     */
    public void increaseEdgeCount(){
        this.edgeCount++;
    }
    
    public static void main(String[] args){
        RATvertex testUser = new RATvertex("101", true);
        RATvertex testStory = new RATvertex("34698", false);
        System.out.println("User vertex: " + testUser);
        System.out.println("Story vertex: " + testStory);
        System.out.println("Is testUser vertex a user? Expect true: " + testUser.getIsUser());
        System.out.println("Is testStory vertex a user? Expect false: " + testStory.getIsUser());
        
        RATvertex testUser2 = new RATvertex("99", true, "testTwo", 345, 7);
        System.out.println("Normal toString: \n" + testUser);
        System.out.println("Specialized toString: \n" + testUser2.toStringUser());
        
    }
}
