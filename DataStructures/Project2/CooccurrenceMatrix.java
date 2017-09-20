import java.util.List;

/**
 * A cooccurrence matrix for a predefined list of keywords.  A cooccurrence
 * matrix records how often each keyword appears in the same context
 * as each other keyword.  If keyword x appears in m contexts and keyword y
 * appears in n of those m contexts then the entry in the cooccurrence matrix
 * at position (x, y) would be n/m.
 *
 * @author Jim Glenn
 * @version 0.1 2017-03-22
 */

public interface CooccurrenceMatrix
{
    /**
     * Updates this cooccurrence matrix for the given context.
     * After this call, each entry in this cooccurrence matrix will reflect
     * the cooccurrence of each keyword in the contextx previously passed
     * to update and this one.
     *
     * @param context a list of words
     */
    public void update(List<String> context);

    /**
     * Returns the cooccurrence vector for the given keyword.  For each
     * keyword the returned array contains the proportion of contexts
     * the given word appeared in that also contained that other keyword.
     * The frequencies in the array are given in the same order as the
     * keywords in the list originally used to construct this matrix and
     * the entry for the given word is 1.0.
     *
     * @param keyword a word in the list originally passed to the constructor
     * @return an array containing the normalized frequency for the
     * given keyword and every other keyword
     * @throws IllegalArgumentException if keyword was not in the list passed
     * to the constructor
     */
    public double[] getVector(String keyword);
}
