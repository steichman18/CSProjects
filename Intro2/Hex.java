import java.awt.geom.Point2D;

public class Hex
{
    private int row;
    private int column;

    public Hex(int r, int c)
    {
	row = r;
	column = c;
    }

    public int getRow()
    {
	return row;
    }

    public int getColumn()
    {
	return column;
    }

    public Point2D.Double getCartesian()
    {
	double x = 0;
	double y = row*(-0.5)*Math.sqrt(3);
	if (row%2==0)
	    {
	       x = column;
	    }
	else
	    {
	        x = column + 0.5;
	    }

	Point2D.Double cartesian = new Point2D.Double(x,y);
	return cartesian;
    }

    public double distance(Hex other)
    {
	double dist =this.getCartesian().distance(other.getCartesian());
	return dist;
    }

    public boolean isAdjacent(Hex other)
    {
	if (this.getColumn() == other.getColumn() && (this.getRow() == other.getRow() + 1 ||
        this.getRow() == other.getRow() - 1))
	    {
		return true;
	    }
	else if (this.getRow() == other.getRow() && (this.getColumn() == other.getColumn() + 1 || 
	   this.getColumn() == other.getColumn() - 1))
	    {
		return true;
	    }
	else if ((this.getRow() == other.getRow() + 1 && this.getColumn() == other.getColumn() + 1)
        || (this.getRow() == other.getRow() - 1 && this.getColumn() == other.getColumn() + 1))
	    {
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public boolean equals(Hex other)
    {
	Point2D.Double cart1 = this.getCartesian();
	Point2D.Double cart2 = other.getCartesian();
	if (cart1.getX() == cart2.getX() && cart1.getY() == cart2.getY())
	    {
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public String toString()
    {
	return "[" + row + ", " + column + "]";
    }

    public Hex update(Hex goal)
    {
	double totalDist = this.distance(goal);
	int x1 = this.getColumn();
	int y1 = this.getRow();
	int x2 = goal.getColumn();
	int y2 = goal.getRow();
	Hex[] hexes = new Hex[6];
	if (y1%2==0)
	    {
		hexes[0] = new Hex(y1+1,x1-1);
		hexes[1] = new Hex(y1,x1-1);
		hexes[2] = new Hex(y1-1,x1-1);
		hexes[3] = new Hex(y1-1,x1);
		hexes[4] = new Hex(y1,x1+1);
		hexes[5] = new Hex(y1+1,x1);
	    }
	else
	    {
		hexes[0] = new Hex(y1+1,x1);
		hexes[1] = new Hex(y1,x1-1);
		hexes[2] = new Hex(y1-1,x1);
		hexes[3] = new Hex(y1-1,x1+1);
		hexes[4] = new Hex(y1,x1+1);
		hexes[5] = new Hex(y1+1,x1+1);
	    }
	int index=-1;
	for (int i = 0; i<6; i++)
	    {
		double newdist = hexes[i].distance(goal);
		if (newdist < totalDist)
		    {
			index = i;
			totalDist = newdist;
		    }
	    }
	// System.out.println(index);
	// System.out.println("("+x1+","+y1+"),("+x2+","+y2+")");
	return hexes[index];
    }
}


