import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

public class CatInTheHatAnalysis
{
    public static void main(String[] args)
    {
	List<String> keywords = makeList("ball cold sun play wet");

	CooccurrenceMatrix t = new MyCooccurrenceMatrix(keywords);

	t.update(makeList("the sun did not shine"));
	t.update(makeList("it was too wet to play"));
	t.update(makeList("so we sat in the house all that cold cold wet day"));
	t.update(makeList("i sat there with sally"));
	t.update(makeList("we sat there we two"));
	t.update(makeList("and i said how i wish we had something to do"));
	t.update(makeList("too wet to go out and too cold to play ball"));
	t.update(makeList("so we sat in the house"));
	t.update(makeList("we did nothing at all"));

	for (String word : keywords)
	    {
		System.out.println(word + ": " + Arrays.toString(t.getVector(word)));
	    }
    }

    /**
     * Returns a list of the words in the given sentence in the same order
     * they appear in the sentence.
     *
     * @param sentence a string containing words separated by single spaces
     * @return a list containing the words in the sentence
     */
    public static List<String> makeList(String sentence)
    {
	List<String> l = new ArrayList<String>();
	Scanner tok = new Scanner(sentence);
	while (tok.hasNext())
	    {
		l.add(tok.next());
	    }
	return l;
    }
}

