public abstract class AbstractAgent implements Agent
{
    protected World homeWorld;

    protected Hex location; 

    public abstract void actOn(Patient p);

    public void update()
    {
	spawn();
    }

    public boolean isAlive()
    {
	return true;
    }
    //leave this for forrest, road, tasmanian devil
    //for scent make this false if off the end of the world
    //for carrion make this false if it's at same place as devil
    //should this be an abstract method instead?? or keep as true?

    public Agent spawn()
    {
	return null;
    }
    //null if Tasmanian devil, scent
    //forrest and road spawn carrion at different rates
    //carrion spawns scent at every update

    public Hex getLocation()
    {
	return location;
    }

    public void acceptAction(TasmanianDevil d)
    {
    }

    public void acceptAction(Scent s)
    {
    }

    public  void acceptAction(Carrion c)
    {
    }

    public void acceptAction(Forest f)
    {
    }

    public  void acceptAction(Road r)
    {
    }
}