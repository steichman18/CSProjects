import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.HashMap;

import java.io.*;

/**
 * A hex-grid world of Tasmanian Devils.
 *
 * @author Jim Glenn
 * @version 0.1 2015-09-28
 */

public class World
{
    /**
     * The total number of devils in this world.
     */
    private int totDevils;

    /**
     * The width of this world.
     */
    private int width;

    /**
     * The height of this world, in rows.
     */
    private int height;

    /**
     * The devils in this world.
     */
    private List<Agent> agents;

    /**
     * Creates of the given width with 0 devils.
     */
    public World(int w)
    {
        this(w, 0, 0, false);
    }
    
    /**
     * Creates an approximately square world of the given width containing the
     * given number of devils.
     * @param w a positive integer
     * @param pop a nonnegative integer
     * @param infected a nonnegative integer and <= pop
     */
    public World(int w, int pop, int infected, boolean developed)
    {
	// validate values of parameters (iow check preconditions)
	if (w <= 0)
	    {
		throw new IllegalWidthException("width must be positive");
	    }

	if (pop < 0)
	    {
		throw new IllegalPopulationException("population must be nonnegative");
	    }

	if (infected < 0 || infected > pop)
	    {
		throw new IllegalInfectedCountException("infected count must be nonnegative and no greater than population");
	    }

	width = w;
	height = (int)(w * 2 / Math.sqrt(3));
	totDevils = pop;

	agents = new ArrayList<Agent>();

	// create terrain
	for (int r = 0; r < height; r++)
	    {
		for (int c = 0; c < width; c++)
		    {
			if (developed && r == height / 2)
			    {
				agents.add(new Road(this, r, c));
			    }
			else
			    {
				agents.add(new Forest(this, r, c));
			    }
		    }
	    }

	// create devils and add them to the list
        for (int i = 0; i < pop - infected; i++)
        {
            TasmanianDevil d = new TasmanianDevil(this, false);
            agents.add(d);
        }
        
        for (int i = 0; i < infected; i++)
        {
            TasmanianDevil d = new TasmanianDevil(this, true);
            agents.add(d);
        }
    }

    /**
     * Determines if the given location is in this world.
     *
     * @param loc a location
     * @return true if and only if that location is in this world
     */
    public boolean contains(Hex loc)
    {
	return loc.getRow() >= 0 && loc.getRow() < height && loc.getColumn() >= 0 && loc.getColumn() < width;
    }

    /**
     * Returns the number of infected devils in this world.
     *
     * @return the number of infected devils in this world
     */
    public int countInfected()
    {
        int count = 0;
        
        for (Agent a : agents)
        {
            if (a instanceof TasmanianDevil && ((TasmanianDevil)a).isInfected())
            {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Simulates the actions of the agents in this world until all devils
     * are infected.
     *
     * @retutn the number of time steps until all devils were infected
     */
     public int simulate()
     {
        int time = 0;

        while (countInfected() < totDevils)
        {
            update();
            // print the list of devils
            //System.out.println(devils);
            time++;
        }
        
        return time;
    }

    /**
     * Updates all the agents in this world.
     */
    public void update()
    {
        // move each devil in the list
        for (Agent a : agents)
	    {
		a.update();
	    }	    

	actOn(new AgentMap());
	
	// check which agents survive to next round
	List<Agent> surviving = new ArrayList<Agent>();

	for (Agent a : agents)
	    {
		if (a.isAlive())
		    {
			surviving.add(a);
		    }
		
		Agent spawn = a.spawn();
		if (spawn != null)
		    {
			surviving.add(spawn);
		    }
	    }
	
	agents = surviving;
    }
     
    /**
     * Has all the agents in this world act on the given patient.
     */
    public void actOn(Patient p)
    {
	// have each agent act
	for (Agent a : agents)
	    {
		a.actOn(p);
	    }
    }

    /**
     * Returns a random location in this world.
     *
     * @return a random location
     */
    public Hex randomLocation()
    {
	return new Hex((int)(Math.random() * height),
		       (int)(Math.random() * width));
    }
    
    /**
     * Returns a new location on this world's border.  Each location is returns with
     * roughly equal probability.
     *
     * @return a new locarion on this world's border
     */
    public Hex randomBorder()
    {
        int perimeter = (width + height) * 2 - 4;
        
        int r = (int)(Math.random() * perimeter);
        
        if (r < width)
        {
            // top edge ([0, 0] to [0, width - 1])
            return new Hex(0, r);
        }
        
        r = r - width;
        if (r < height - 1)
        {
            // right edge ([1, width -1 to [height - 1, width - 1])
            return new Hex(r + 1, width - 1);
        }
        
        r = r - (height - 1);
        if (r < width - 1)
        {
            // bottom edge ([height - 1, 0] to [height - 1, width - 2]]
            return new Hex(height - 1, r);
        }
        
        // left edge ([1, 0] to [2*width + 2*height - 4 - width - (height - 1) - (width - 1) + 1, 0]
        // = [1, 0] to [height - 1, 0])
        r = r - (width - 1);
        return new Hex(r + 1, 0);
    }

    /**
     * Returns the number of columns of hexes in this world.
     *
     * @return the width of this world
     */
    public int getWidth()
    {
	return width;
    }

    /**
     * Returns the number of rows in this world.
     *
     * @return the height of this world
     */
    public int getHeight()
    {
	return height;
    }

    /**
     * A map of the agents in this world.  When an agent acts on the map
     * the map will have it act on all the agents already at its location
     * and vice versa.
     */
    public class AgentMap implements Patient
    {
	/**
	 * A map from locations (given as (r,c) strings) to lists of agents
	 * at those locations.
	 */
	private Map<String, List<Agent>> map;
    
	public AgentMap()
	{
	    map = new HashMap<String, List<Agent>>();
	}
	 
	public void accept(Agent a)
	{
	    // build a key from row and column
	    // (sorry, but I don't trust students' toString)
	    String key = a.getLocation().getRow() + "," + a.getLocation().getColumn();

	    // check whether there's already a list of agents at this location
	    if (map.containsKey(key))
		{
		    // yes...get the list
		    List<Agent> l = map.get(key);
		    
		    // and act on all the agents already there and vice versa
		    for (Agent b : l)
			{
			    a.actOn(b);
			    b.actOn(a);
			}
		    l.add(a);
		}
	    else
		{
		    // no one there -- make a new singleton list
		    List<Agent> l = new ArrayList<Agent>();
		    l.add(a);
		    map.put(key, l);
		}
	}
	
	public void acceptAction(Road r)
	{
	    accept(r);
	}
	
	public void acceptAction(Forest f)
	{
	    accept(f);
	}
	
	public void acceptAction(Scent s)
	{
	    accept(s);
	}
	
	public void acceptAction(Carrion c)
	{
	    accept(c);
	}
	
	public void acceptAction(TasmanianDevil d)
	{
	    accept(d);
	}
    }

    public class IllegalWidthException extends IllegalArgumentException
    {
	public IllegalWidthException(String mess)
	{
	    super(mess);
	}
    }

    public class IllegalPopulationException extends IllegalArgumentException
    {
	public IllegalPopulationException(String mess)
	{
	    super(mess);
	}
    }

    public class IllegalInfectedCountException extends IllegalArgumentException
    {
	public IllegalInfectedCountException(String mess)
	{
	    super(mess);
	}
    }
}


   