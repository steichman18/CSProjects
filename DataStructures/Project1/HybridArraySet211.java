import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class HybridArraySet211<E extends Comparable<? super E>> implements Set211<E>
{

    private ArrayList<E> sorted;
    private ArrayList<E> unsorted;
    private int size;

    /**
    * Creates an empty hybrid set
    */
    public HybridArraySet211()
    {
        size = 0;
        sorted = new ArrayList<E>();
        unsorted = new ArrayList<E>();
    }

    /**
     * Adds the given item to this set.
     *
     * @param toAdd the item to add
     */
    public void add(E toAdd)
    {
        //System.out.println(contains(toAdd));
        if (contains(toAdd)==false) 
        {
            unsorted.add(toAdd);
            size++;
            if (unsorted.size()>Math.sqrt(sorted.size()))
            {
                //System.out.println("merge");
                merge();
            }
        } 
    }
    /**
     * Determines if this set contains the given item.
     *
     * @param item the item to check for
     * @return true if and only it that item is in this set
     */
    public boolean contains(E item)
    {
        int x = Arrays.binarySearch(sorted.toArray(),item);
        //System.out.println(x + " result of Binary Search");
        if (x>-1)
        {
            return true;
        }
        else
        {
            boolean y = unsorted.contains(item);
            //System.out.println(y + " result of Sequential Search");
            if (y)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }

    /**
     * Removes the given item from this set.  There is no effect if the
     * item is not in this set.
     *
     * @param item the item to remove
     */
    public void remove(E item)
    {
        if (contains(item))
        {
            int x = Arrays.binarySearch(sorted.toArray(),item);
            if (x!=-1)
            {
                sorted.remove(x);
            }
            else
            {
                unsorted.remove(item);
            }
            size--;
        }   
    }

    /**
     * Returns the number of unique items in this set.
     *
     * @return the number of items in this set
     */
    public int size()
    {
        return size;
    }

    /**
     * Merges the sorted and unsorted arrays
     * adds the elements of unsorted to sorted
     * sorts sorted
     */
    public void merge()
    {
        sorted.addAll(unsorted);
        Collections.sort(sorted);
        unsorted.clear();
    }

    /**
     * Cocatenates the results for toString for both the sorted and unsorted arrays
     */
    public String toString()
    {
        return(sorted.toString()+unsorted.toString());
    }
}