/**
 * A set.
 *
 * @author Jim Glenn
 * @version 0.1 2017-02-22
 */

public interface Set211<E extends Comparable<? super E>>
{

    /**
     * Adds the given item to this set.
     *
     * @param toAdd the item to add
     */
    public void add(E toAdd);

    /**
     * Determines if this set contains the given item.
     *
     * @param item the item to check for
     * @return true if and only it that item is in this set
     */
    public boolean contains(E item);

    /**
     * Removes the given item from this set.  There is no effect if the
     * item is not in this set.
     *
     * @param item the item to remove
     */
    public void remove(E item);

    /**
     * Returns the number of unique items in this set.
     *
     * @return the number of items in this set
     */
    public int size();
}