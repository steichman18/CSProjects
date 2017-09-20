import java.util.ArrayList;
import java.util.List;

/**
 * A simulation of a world full of Tasmanian devils.
 *
 * @author Jim Glenn
 * @version 0.1 2015-10-25
 */

public class P2Simulation
{
    public static void main(String[] args)
    {
        // default values for simulation parameters
        final int DEFAULT_WORLD_SIZE = 12;
        final int DEFAULT_POPULATION = 5;
        final int DEFAULT_INFECTED = 1;
        final int DEFAULT_TRIALS = 100;
	final boolean DEFAULT_ROAD = false;

        // actual values of simulation parameters
        int w = DEFAULT_WORLD_SIZE;
        int pop = DEFAULT_POPULATION;
        int infected = DEFAULT_INFECTED;
        int trials = DEFAULT_TRIALS;
	boolean road = DEFAULT_ROAD;
        
        // read simulation parameters from command line if present
        if (args.length > 0)
        {
            if (args[0].equals("--help"))
            {
                System.err.println("USAGE: java P2Simulation [world-size [population [infected[ trials]]]]");
                System.exit(0);
            }
            w = Integer.parseInt(args[0]);
        }
        
        if (args.length > 1)
        {
            pop = Integer.parseInt(args[1]);
        }
        
        if (args.length > 2)
        {
            infected = Integer.parseInt(args[2]);
        }

        if (args.length > 3)
        {
            trials = Integer.parseInt(args[3]);
        }

	if (args.length > 4)
	    {
		road = (args[4].equals("road"));
	    }

        int grandTotal = 0; // tot steps over all simulations
        for (int i = 0; i < 100; i++)
        {
            // create a world
            World world = new World(w, pop, infected, road);
                    
            // simulate world until all devils are infected, accumulating number of time steps
             grandTotal += world.simulate();        
        }
        
        // print number of time steps to infect all devils 
        System.out.println(grandTotal / 100);
    }
}
