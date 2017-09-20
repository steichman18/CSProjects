import java.lang.management.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * Adds m integers chosen randomly and uniformly from 0,...,n-1.
 *
 * @author Jim Glenn
 * @version 0.1 2017-02-22
 */
public class RandomAdds
{
    public static void main(String[] args)
    {
	// read command-line arguments
	if (args.length < 3)
	    {
		System.err.println("USAGE: java RandomAdds {hybrid | redblack} n m");
		System.exit(1);
	    }

	int n = Integer.parseInt(args[1]);
	int m = Integer.parseInt(args[2]);

	// start timer
	ThreadMXBean bean = ManagementFactory.getThreadMXBean();
	long start = bean.getCurrentThreadUserTime();

	if (args[0].equals("hybrid"))
	    {
		Set211<Integer> s = new HybridArraySet211<Integer>();
		
		// add m random integers to our set implementation
		for (int i = 0; i < m; i++)
		    {
			int x = (int)(Math.random() * n);
			//System.out.println(x);
			s.add(x);
			//System.out.println(s);
		    }
		
		// get and display elapsed time
		long end = bean.getCurrentThreadUserTime();
		System.out.println((end - start) / 1e+9);
		System.out.println(s.size() + " unique items");
	    }
	else
	    {
		// same thing but with a TreeSer
		Set<Integer> s = new TreeSet<Integer>();
		for (int i = 0; i < m; i++)
		    {
			int x = (int)(Math.random() * n);
			s.add(x);
		    }

		long end = bean.getCurrentThreadUserTime();
		//System.out.println((end - start) / 1e+9);
		//System.out.println(s.size() + " unique items");
	    }
    }
}
