import java.lang.management.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;

/**
 * Adds m integers chosen randomly and uniformly from 0,...,n-1.
 *
 * @author Jim Glenn
 * @version 0.1 2017-02-22
 */
public class RandomAddsEdited
{
    public static void main(String[] args)
    {
	// read command-line arguments
	// if (args.length < 3)
	//     {
	// 	System.err.println("USAGE: java RandomAdds {hybrid | redblack} n m");
	// 	System.exit(1);
	//     }

	// int n = Integer.parseInt(args[1]);
	// int m = Integer.parseInt(args[2]);
	// String type = args[0];
    ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("hybrid","redblack"));
    ArrayList<Integer> nList = new ArrayList<Integer>(Arrays.asList(10,100,1000,10000,100000));
	ThreadMXBean bean = ManagementFactory.getThreadMXBean();
	long start;
	long end;

    for (String type:typeList)
    {
    	for (int n:nList)
    	{
    		for (int m = n/10;m<1000*n;m=1000*m)
    		{
				System.out.println("n: "+n+" m: "+m+" type: "+type);

    			double timeSum = 0;
    			double uniqueSum = 0;
    			for (int rep = 0;rep<10;rep++)
	    			{
					// start timer
					start = bean.getCurrentThreadUserTime();

					if (type.equals("hybrid"))
					    {
						Set211<Integer> s = new HybridArraySet211<Integer>();
						
						// add m random integers to our set implementation
						for (int i = 0; i < m; i++)
						    {
							int x = (int)(Math.random() * n);
							s.add(x);
						    }
						
						// get and display elapsed time
						end = bean.getCurrentThreadUserTime();
						timeSum += (end-start);
						uniqueSum += s.size();
						System.out.println(s.size() + " unique");
					    }
					else
					    {
						// same thing but with a TreeSet
						Set<Integer> s = new TreeSet<Integer>();
						for (int i = 0; i < m; i++)
						    {
							int x = (int)(Math.random() * n);
							s.add(x);
						    }

						end = bean.getCurrentThreadUserTime();
						timeSum += (end-start);
						uniqueSum += s.size();
						System.out.println(s.size() + " unique");
						}	
					System.out.println((end-start)/(10*1e+9) + " sec");


				    }
				double timeSumCalc = timeSum/(1e+9);
				System.out.println(timeSumCalc/100 + " average time");
				System.out.println(uniqueSum/10 + " unique items");
	    		}
			}
		}
	}
}