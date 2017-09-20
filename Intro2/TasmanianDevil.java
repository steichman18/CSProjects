public class TasmanianDevil extends AbstractAgent
{
    private Hex goalLocation;

    private boolean sick;
    
    public TasmanianDevil(World w, boolean s)
    {
	homeWorld = w;
	location = homeWorld.randomLocation();
	goalLocation = homeWorld.randomLocation();
	sick = s;
    }

     public void actOn(Patient p)
    {
	p.acceptAction(this);
    }


    public Hex getGoal()
    {
	return goalLocation;
    }

    public String toString()
    {
	return location.toString() + "-->" + goalLocation.toString()+ " Infected: " + sick;
    }

    public void update()
    {
	if (location.equals(goalLocation))
	    {
		goalLocation = homeWorld.randomLocation();
	    }
	else
	    {
		location = location.update(goalLocation);
	    }
    }
   
    public boolean isInfected()
    {
	return sick;
    }

    public void acceptAction(TasmanianDevil d)
    {
	double random = Math.random();
	if (random<=0.8 && (sick==true || d.sick==true))
	    {
		sick= true;
		d.sick = true;
	    }

    }

    public void acceptAction(Scent s)
    {
	goalLocation = s.getOriginalLocation();
    }	
}