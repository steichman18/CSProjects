import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 * A priority queue.  Each item in the queue is referenced by
 * an integer index in the range [0, n).
 *
 * @version 0.1 2017-04-03
 */

public class BinaryHeapPriorityQueue<K> implements PriorityQueue<K>
{

    private ArrayList<element> queue;
    private HashMap<K,Integer> handles;
    private int size;

    public BinaryHeapPriorityQueue()
    {
        queue = new ArrayList<element>();
        handles = new HashMap<K,Integer>();
        size = 0;
    }

    /**
     * Removes the item with the lowest priority from this queue.
     *
     * @return the item with the lowest priority
     */
    public K extractMin()
    {
        K removed = queue.get(0).getitem();

        queue.set(0, queue.get(size-1));
        handles.put(queue.get(0).getitem(),0);
        queue.remove(size-1);
        size--;
        trickleDown(0);

        handles.remove(removed);
        
        return removed;
    }

    /**
     * Returns the lowest priority of all items in this queue.
     *
     * @return the value of the lowest priority
     */
    public double peekMin()
    {
        return queue.get(0).getpri();
    }

    /**
     * Adds the given item to this queue with the given priority.
     * If the item is already in this queue then the effect is the
     * same as decreasePriority.
     *
     * @param item an item, not null
     * @param pri its priority
     */
    public void add(K item, double pri)
    {
        if (handles.containsKey(item))
        {
            decreasePriority(item, pri);
        }
        else
        {
            element entry = new element(item,pri);
            queue.add(size,entry);
            handles.put(item,size);
            size++;
            bubbleUp(size-1);
        }
    }

    /**
     * Returns the priority of the given item.
     *
     * @param item an item in this queue
     * @return the priority of that item
     */
    public double getPriority(K item)
    {
        if (!handles.containsKey(item))
        {
            throw new NoSuchElementException();
        }
        else
        {
            return queue.get(handles.get(item)).getpri();
        }
    }

    /**
     * Decreases the priority of the given item to
     * given value.  There is no effect if the item is not present
     * or if the new priority is higher than the old.
     *
     * @param item an item in this queue
     * @param pri its new priority
     */
    public void decreasePriority(K item, double p)
    {
        if (!handles.containsKey(item))
        {
            throw new NoSuchElementException();
        }
        if (p<getPriority(item))
        {
            int i = handles.get(item);
            queue.get(i).setpri(p);
            bubbleUp(i);
        }
        
    }

    /**
     * Determines if this queue contains the item with the given index.
     *
     * @parma item an item; not null
     * @return true if and only if this queue contains the item with that index
     */
    public boolean contains(K item)
    {
        return (handles.containsKey(item));
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size()
    {
        return size;
    }


    /**
     * Returns the index of the left child of the node at the given index.
     *
     * @param i a nonnegative integer
     */
    public static int left(int i)
    {
        return 2 * i + 1;
    }
    
    /**
     * Returns the index of the right child of the node at the given index.
     *
     * @param i a nonnegative integer
     */
    public static int right(int i)
    {
        return 2 * i + 2;
    }

    /**
     * Returns the index of the parent of the node at the given index.
     *
     * @param i a positive integer
     */
    public static int parent(int i)
    {
        return (i - 1) / 2;
    }    

    private void trickleDown(int i)
    {
    do
        {
        int smallest = -1;
        int r = right(i);
        if (r < size && queue.get(r).compareTo(queue.get(i))<0)
            {
            // two children; entry at i is out of order with right child;
            // determine which child is the smallest
            int l = left(i);
            if (queue.get(l).compareTo(queue.get(r)) < 0)
                {
                // left child is smallest
                smallest = l;
                }
            else
                {
                // right child is smallest
                smallest = r;
                }
            }
        else
            {
            // one or zero children; if one child determine if it is smaller
            int l = left(i);
            if (l < size && queue.get(l).compareTo(queue.get(i))<0)
                {
                // left (only) child is smaller
                smallest = l;
                }
            }
        
        // if there was an order violation then swap
        if (smallest != -1)
            {
            swap(i, smallest);
            }
        
        // move down (or off heap if no order violation)
        i = smallest;
        } while (i != -1);
    }

    private void bubbleUp(int i)
    {
    // get index of parent
    int p = parent(i);
        
    // repeat until at root or priority is >= parent
    while (i > 0 && queue.get(i).compareTo(queue.get(p))<0)
        {
        // swap with parent
        swap(i, p);
            
        // move up
        i = p;
        p = parent(i);
        }
    }

    private void swap(int i, int j)
    {
        handles.put(queue.get(i).getitem(),j);
        handles.put(queue.get(j).getitem(),i);
        element temp = queue.get(i);
        queue.set(i,queue.get(j));
        queue.set(j,temp);

    }



    private class element
    {
        private K item;
        private double pri;

        public element(K i, double p)
        {
            item = i;
            pri = p;
        }

        public int compareTo(element thing)
        {
            return (int)Math.signum(this.getpri() - thing.getpri());
        }

        public double getpri()
        {
            return pri;
        }

        public K getitem()
        {
            return item;
        }

        public void setpri(double p)
        {
            pri = p;
        }

        public void setitem(K i)
        {
            item = i;
        }
    }
}

    