import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;

/**
 * The result of running single-source shortest paths.
 *
 * @param E the type of the labels on the vertices
 * @version 0.1 2017-04-12
 */

public class DijkstraSingleSourceShortestPaths<E> implements SingleSourceShortestPaths<E>
{

    private BinaryHeapPriorityQueue pq; 
    private HashMap<E,E> predMap;
    private HashMap<E,Double> distMap;
    private double t;
    private E source;

    public DijkstraSingleSourceShortestPaths(Map<E, Map<E, Double>> edges, E s)
    {
        pq = new BinaryHeapPriorityQueue();
        predMap = new HashMap<E,E>();
        distMap = new HashMap<E,Double>();
        t = 0;
        source = s;
        //add source to queueu
        pq.add(source,0);
        //while queue is not empty
        while (pq.size()!=0)
        {
            double pri = pq.peekMin(); 
            //take item with lowest priority off queue
            E u = (E)pq.extractMin();
            t = pri;
            //put item into distance map with the current time
            distMap.put(u,t);
            //iterate through every vertext, double pair in the map for vertex u
            for (Map.Entry<E,Double> vert:edges.get(u).entrySet())
            {
                //get the vertex from the vertex, double pair
                E v = (E)vert.getKey();
                double p = (edges.get(u)).get(v)+t;
                //if item has not already been taken off the queue
                if (!distMap.containsKey(v))
                {
                    //if item is not currently in the queue
                    if (!pq.contains(v))
                    {
                        //add item to the queue
                        pq.add(v,p);
                        //record the predecesor 
                        predMap.put(v,u); 
                    }
                    //if item is currently in the queue
                    else
                    {
                        pq.decreasePriority(v,p);
                        //if new distance is less than distance based on old predecesor
                        if (p<distMap.get(predMap.get(v))+edges.get(predMap.get(v)).get(v))
                        {
                            //update predecesor map
                            predMap.put(v,u);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the list of vertices on a shortest path from the source
     * to the given destination.  If there is no path then the return
     * value should be null.
     *
     * @param dest the label of a vertex; not null
     * @return a list containing the vertices on a shortest path from
     * the source to the given destination, or null of there is no path
     */
    public List<E> getPath(E dest)
    {
        if (!predMap.containsKey(dest))
        {
            return null;
        }
        else
        {
            List<E> path = new ArrayList<E>();
            E curr = dest;
            while (curr!=source)
                {
                //add current place to path, starting at destination
                path.add(curr);
                //iterate backwards through the list
                curr = (E)predMap.get(curr);
                }
            path.add((E)source);
            //reverse direction of the path (linear time for p)
            Collections.reverse(path);
            return path;
        }

    }

    /**
     * Returns the total cost of a shortest path from the source
     * to the given destination.
     *
     * @param dest the label of a vertex to which there is a path from
     * the source; not null
     * @return the total cost of a shortest path fro the source to the
     * given destination
     */
    public double getDistance(E dest)
    {
        return distMap.get(dest);
    }
}