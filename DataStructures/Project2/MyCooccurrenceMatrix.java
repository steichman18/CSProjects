import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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

public class MyCooccurrenceMatrix implements CooccurrenceMatrix
{

    private Double[][] matrix;
    private HashMap<String,Integer> coords;

    /**
     * 2 data structures - 2d ArrayList and HashMap
     * ArrayList holds counts for cooccurrences
     * HashMap with (K,V) as (word, position in matrix)
     */
    public MyCooccurrenceMatrix(List<String> keywords)
    {
        //create array of Doubles, with a space to hold counts for each possible cooccurrence
        matrix = new Double[keywords.size()][keywords.size()];
        //create empty HashMap
        coords = new HashMap();
        for (int i=0;i<keywords.size();i++)
        {
            //add (word, coordinate) pairs to HashMap
            coords.put(keywords.get(i),i);
            for (int r=0;r<keywords.size();r++)
            {
                //initialize each cooccurrence count as 0.0
                matrix[i][r] = 0.0;
            }
        }
        //System.out.println("matrix size: "+keywords.size());
        //System.out.println("hashmap size: "+coords.size());
    }

    /**
     * Updates this cooccurrence matrix for the given context.
     * After this call, each entry in this cooccurrence matrix will reflect
     * the cooccurrence of each keyword in the contextx previously passed
     * to update and this one.
     *
     * @param context a list of words
     */
    public void update(List<String> context)
    {
        //create list to compute cooccurrences from
        ArrayList<String> words = new ArrayList<String>();
        for (String w:context)
        {
            if (coords.containsKey(w) && !words.contains(w))
            {
                //add words to list that are in HashMap and haven't already been added to the list
                words.add(w);
            }
        }
        for (String w:words)
        {
            for (String w2:words)
            {
                //update the cooccurrence count for the new line
                matrix[coords.get(w)][coords.get(w2)] += 1.0;
            }
        }
    }

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
    public double[] getVector(String keyword)
    {
        //create an array of doubles to hold the counts
        double[] vec = new double[coords.size()];
        if (!coords.containsKey(keyword))
        {
            //throw exception if keyword was not in the original list of keywords
            throw new IllegalArgumentException("Keyword not in the original list");
        }
        else
        {
            for (int i=0; i<coords.size();i++)
            {
                //add each cooccurrence to the list of counts
                vec[i] = matrix[coords.get(keyword)][i]/matrix[coords.get(keyword)][coords.get(keyword)];
            }
        }
        return vec;
    }
}