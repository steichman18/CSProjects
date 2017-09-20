public class Carrion extends AbstractAgent
{
    private boolean eaten=false;

    public Carrion(World w, int r, int c)
    {
	homeWorld = w;
	location = new Hex(r,c);
    }

    public void actOn(Patient p)
    {
	p.acceptAction(this);
    }


    public boolean isAlive()
    {
	if (eaten == true)
	    {
		return false;
	    }

	else
	    {
		return true;
	    }
    }

    public Scent spawn()
    {
	Scent newScent = new Scent(homeWorld, location.getRow(), location.getColumn());
	return newScent;
    }

    public void acceptAction(TasmanianDevil d)
    {
	eaten = true;
    }
}
   

		