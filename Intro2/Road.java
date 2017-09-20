import java.util.*;
import java.lang.Math;
public class Road extends AbstractAgent
{
    public Road(World w, int r, int c)
    {
	homeWorld = w;
	location = new Hex(r,c);
    }

     public void actOn(Patient p)
    {
	p.acceptAction(this);
    }

    public Carrion spawn()
    {
	Random random = new Random();
	int num = random.nextInt(100);
	if (num==1)
	    {
		Carrion c = new Carrion(homeWorld, location.getRow(), location.getColumn());
		return c;
	    }
	else 
	    {
		return null;
	    }
    }
}