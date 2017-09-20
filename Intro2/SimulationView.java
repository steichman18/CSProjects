import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * A Swing view of a world full of Tasmanian devils.
 *
 * @author Jim Glenn
 * @version 0.1 2015-11-10
 */

public class SimulationView extends JPanel
{
    /**
     * The world this view displays.
     */
    private World world;

    /**
     * Sets the world displayed by this view.  If the parameter is null
     * then this view will be blank.
     *
     * @param world a world, or null
     */
    public void setWorld(World w)
    {
	world = w;
	repaint();
    }

    /**
     * Draws the world displayed by this view.
     *
     * @param g the graphics context to draw in
     */
    public void paintComponent(Graphics g)
    {
	// paint the background, etc.
	super.paintComponent(g);

	if (world != null)
	    {
		// figure out the size and width of the world
		Hex bottomRight = new Hex(world.getHeight() - 1, world.getWidth() - 1);
		double height = -bottomRight.getCartesian().getY() + Math.sqrt(3) / 2;
		double width = bottomRight.getCartesian().getX() + 1;
		if (world.getHeight() % 2 == 0)
		    {
			width += 0.5;
		    }

		// figure out how many pixels 1 unit in world space equals
		int gridSize = (int)Math.min(getWidth() / width, getHeight() / height);

		// draw the grid
		g.setColor(new Color(128, 128, 128));
		for (int r = 0; r < world.getHeight(); r++)
		    {
			for (int c = 0; c < world.getWidth(); c++)
			    {
				// draw the outline of the grid square

				Point2D.Double curr = new Hex(r, c).getCartesian();
				int left = (int)(curr.getX() * gridSize);
				int top = (int)(-curr.getY() * gridSize);
				g.drawRect(left, top, gridSize, (int)(gridSize * Math.sqrt(3) / 2));
			    }
		    }

		// have the world tell us where all the agents are;
		// "acting on" in this sense will mean "report to the painter"
		world.actOn(new Painter(g, gridSize));
	    }
    }

    /**
     * A class that draws agents.
     */
    private class Painter implements Patient
    {
	/**
	 * The graphics context to draw in.
	 */
	private Graphics g;

	/**
	 * The size of each grid location.
	 */
	private int gridSize;

	/**
	 * The number of devils we've painted so far.
	 */
	private int devilLabel;
	
	// keep track of how many things we've drawn in each location
	private int[][] count = new int[world.getHeight()][world.getWidth()];

	/**
	 * Creates a new painter.
	 *
	 * @param g a graphics context
	 * @param sz a positive integer for the numbner of pixels per hex
	 */
	public Painter(Graphics g, int sz)
	{
	    this.g = g;
	    gridSize = sz;
	}

	/**
	 * Records that there is an agent at the given location and
	 * returns a horizontal shift based on how many agents are already there.
	 *
	 * @param loc a location
	 */
	private int getShift(Hex loc)
	{
	    if (world.contains(loc))
		{
		    int row = loc.getRow();
		    int col = loc.getColumn();
		    // devil is still in this world
		    count[row][col] += 1;
		    return (int)(gridSize / 10.0 * count[row][col]);
		}
	    else
		{
		    return 0;
		}
	}

	/**
	 * Draws a devil.
	 *
	 * @param d a devil
	 */
	public void acceptAction(TasmanianDevil d)
	{
	    // same font for all the devils
	    g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, gridSize / 3));

	    // draw the devil
	    Hex h = d.getLocation();
	    int row = h.getRow();
	    int col = h.getColumn();
	    Point2D.Double pos = h.getCartesian();
	    
	    // calculate a horizontal shift based on how many
	    // things we've already drawn in d's hex
	    int shift = getShift(h);
	    int centerX = (int)((pos.getX() + 0.5) * gridSize + shift);
	    int centerY = (int)((-pos.getY() + Math.sqrt(3) / 4) * gridSize);
	    if (d.isInfected())
		{
		    // red for infected
		    g.setColor(new Color(255, 0, 0));
		}
	    else
		{
		    // green for healthy
		    g.setColor(new Color(0, 255, 0));
		}
	    // a circle
	    g.fillOval(centerX - gridSize / 4, centerY - gridSize / 4, gridSize / 2, gridSize / 2);
	    
	    // draw a label on top
	    g.setColor(Color.WHITE);
	    g.drawString(String.valueOf((char)('A' + devilLabel)), centerX - gridSize / 6, centerY + gridSize / 6);
	    
	    // keep track of the index of the devil we're working on
	    devilLabel++;
	}
	
	/**
	 * Draws piece of carrion.
	 *
	 * @param c carrion
	 */
	public void acceptAction(Carrion c)
	{
	    // draw the carrion
	    Hex h = c.getLocation();
	    int row = h.getRow();
	    int col = h.getColumn();
	    Point2D.Double pos = h.getCartesian();
	    
	    // calculate a horizontal shift based on how many
	    // things we've already drawn in d's hex
	    int shift = getShift(h);
	    int centerX = (int)((pos.getX() + 0.5) * gridSize + shift);
	    int centerY = (int)((-pos.getY() + Math.sqrt(3) / 4) * gridSize);

	    g.setColor(Color.YELLOW.darker());
	    // a circle
	    g.fillOval(centerX - gridSize / 4, centerY - gridSize / 4, gridSize / 2, gridSize / 2);
	}

	/**
	 * Draws a scent.
	 *
	 * @param s a scent
	 */
	public void acceptAction(Scent s)
	{
	    // draw the scent
	    Hex h = s.getLocation();
	    int row = h.getRow();
	    int col = h.getColumn();
	    Point2D.Double pos = h.getCartesian();
	    
	    // calculate a horizontal shift based on how many
	    // things we've already drawn in d's hex
	    int shift = getShift(h);
	    int centerX = (int)((pos.getX() + 0.5) * gridSize + shift);
	    int centerY = (int)((-pos.getY() + Math.sqrt(3) / 4) * gridSize);

	    g.setColor(Color.YELLOW.darker());
	    // a small circle
	    g.fillOval(centerX - gridSize / 8, centerY - gridSize / 8, gridSize / 4, gridSize / 4);
	}

	/**
	 * Draws a road.
	 *
	 * @param r a road
	 */
	public void acceptAction(Road r)
	{
	    // draw the scent
	    Hex h = r.getLocation();
	    int row = h.getRow();
	    int col = h.getColumn();
	    Point2D.Double pos = h.getCartesian();
	    
	    int leftX = (int)(pos.getX() * gridSize);
	    int centerY = (int)((-pos.getY() + Math.sqrt(3) / 4) * gridSize);
	    int thickness = Math.min(5, gridSize / 2);

	    g.setColor(Color.GRAY);
	    // a thick line across
	    g.fillRect(leftX, centerY - thickness / 2, gridSize, thickness);
	}

	/**
	 * Draws a forest.
	 *
	 * @param f a forest
	 */
	public void acceptAction(Forest f)
	{
	    // nothing to draw for forest
	}
    }
}
