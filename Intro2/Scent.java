public class Scent extends AbstractAgent
{
    private Hex goalLocation;

    private Hex originalLocation;

    public Scent(World w, int r, int c)
    {
	homeWorld = w;
	location = new Hex(r,c);
	originalLocation = location;
	goalLocation = homeWorld.randomLocation();
    }

    public void actOn(Patient p)
    {
	p.acceptAction(this);
    }

    public void update()
    {
        if (isAlive())
	    {
		location = location.update(goalLocation);
	    }
    }

    public boolean isAlive()
    {
	if (location.equals(goalLocation))
	    {
		return false;
	    }
	else
	    {
		return true;
	    }
    }

    public Hex getOriginalLocation()
    {
	return originalLocation;
    }
}



