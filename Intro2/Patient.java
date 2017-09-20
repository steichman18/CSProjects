/**
 * A thing that is acted on by an agent in an agent-based simulation.
 *
 * Apparently the grammatical term for this is "patient"
 * http://english.stackexchange.com/questions/101310/is-there-a-word-for-something-that-gets-acted-upon
 *
 * In object-oriented programming this kind of thing is called a visitor
 * https://en.wikipedia.org/wiki/Visitor_pattern
 *
 * Finally, 10 years after hearing Craig's "we could use a visitor" story,
 * I figured out that we could use a visitor.
 *
 * @author Jim Glenn
 * @version 0.1 2015-11-19
 */

public interface Patient
{
    /**
     * Updates this patient as the result of being acted on by the given
     * devil.
     *
     * @param d a Tasmanian devil
     */
    public void acceptAction(TasmanianDevil d);

    /**
     * Updates this patient as the result of being acted on by the given
     * scent.
     *
     * @param s a scent
     */
    public void acceptAction(Scent s);

    /**
     * Updates this patient as the result of being acted on by the given
     * piece of carrion.
     *
     * @param c a piece of carrion
     */
    public void acceptAction(Carrion c);

    /**
     * Updates this patient as the result of being acted on by the given
     * stand of forest.
     *
     * @param f a stand of forest
     */
    public void acceptAction(Forest f);

    /**
     * Updates this patient as the result of being acted on by the given
     * segment of road.
     *
     * @param f a segment of road
     */
    public void acceptAction(Road r);
}
