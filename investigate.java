
/**
 * investigate is a Driver class for RATgraph - used to test a file in a format such as "All_Russian-Accounts-in-TT-stories.csv.tsv"
 *
 * @author (Sarah Pardo, Panya Tang)
 * @version (12/6/19)
 */
import java.io.*;
import java.util.List;
public class investigate
{

    /**
     * testing for RATgraph using the "All_Russian-Accounts-in-TT-stories.csv.tsv"
     * if user inputs another file with the same format it will read that file
     */
    public static void main (String[] args)
    {
        if (args.length == 1){
            String userFile = args[0];
            System.out.print("Making a RATgraph from user input file " + userFile + "\n");
            
            RATgraph userGraph = new RATgraph ();
            userGraph.makeFile("userRATGraph.tgf");
            userGraph.readFile(userFile);
            try{
                userGraph.getBipartite().saveToTGF("userRATGraph.tgf");
            } catch(IOException x){
                System.out.println(x);
            }
            System.out.println(userGraph);
            
             System.out.println("10 most active users: ");
            List<RATvertex> actUsers = userGraph.mostActiveUsers();
            for (int t = 0; t < actUsers.size(); t++){
                System.out.println(actUsers.get(t).toStringUser());
            }
            System.out.println();

            System.out.println("10 most popular stories: ");
            List<RATvertex> popStories = userGraph.mostPopularStories();
            for (int x = 0; x < popStories.size(); x++){
                System.out.println(popStories.get(x));
            }
            System.out.println();
            System.out.println(userGraph.furthestApart());
        }else{
            RATgraph russians = new RATgraph ();

            russians.makeFile("RATGraph.tgf");

            russians.readFile("All_Russian-Accounts-in-TT-stories.csv.tsv");
            try{
                russians.getBipartite().saveToTGF("RATGraph.tgf");
            } catch(IOException x){
                System.out.println(x);
            }
            System.out.println(russians);
            System.out.println();
            System.out.println("10 most active users: ");
            List<RATvertex> actUsers = russians.mostActiveUsers();
            for (int t = 0; t < actUsers.size(); t++){
                System.out.println(actUsers.get(t).toStringUser());
            }
            System.out.println();
            System.out.println("10 most popular stories: ");
            List<RATvertex> popStories = russians.mostPopularStories();
            for (int x = 0; x < popStories.size(); x++){
                System.out.println(popStories.get(x) + ", number of edges: " + popStories.get(x).getEdgeCount());
            }
            System.out.println();
            System.out.println(russians.furthestApart());
            
            
        }
    }
}
