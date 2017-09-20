import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class RunShortestPaths
{
    public static void main(String[] args)
    {
	String source = args[0];
	String destination = args[1];

	// create a map from vertices to maps from vertices to weights
	Map<String, Map<String, Double>> edges = new HashMap<String, Map<String, Double>>();
	
	// read input
	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	    {
		String from = in.next();
		String to = in.next();
		double w = in.nextDouble();

		if (w < 0)
		    {
			throw new RuntimeException("negative weight: " + from + ", " + to + " " + w);
		    }
		
		if (!edges.containsKey(from))
		    {
			edges.put(from, new HashMap<String,Double>());
		    }
		edges.get(from).put(to, w);

		if (!edges.containsKey(to))
		    {
			edges.put(to, new HashMap<String,Double>());
		    }
		edges.get(to).put(from, w);
	    }

	SingleSourceShortestPaths<String> p = new DijkstraSingleSourceShortestPaths<String>(edges, source);
	List<String> path = p.getPath(destination);
	if (path != null)
	    {
		System.out.println(p.getDistance(destination) + " " + path);
	    }
	else
	    {
		System.out.println(path);
	    }
    }
}


		