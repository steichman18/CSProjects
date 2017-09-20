import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A window that displays the Tasmanian Devil simualation.
 *
 * @author Jim Glenn
 * @version 0.1 2015-11-10
 */

public class SimulationGUI extends JFrame implements ActionListener
{
    /**
     * The world in this simulation.
     */
    private World world;

    /**
     * The view of that world.
     */
    private SimulationView view;

    /**
     * The text fields for user input.
     */
    private JTextField totField;
    private JTextField infectField;
    private JTextField sizeField;
    private JCheckBox roadCheck;

    /**
     * The label for displaying number infected.
     */
    private JLabel infectedCountLabel;

    /**
     * The label for displaying error messages.
     */
    private JLabel errorLabel;
    
    /**
     * Creates a new window and all the widgets inside it.
     */
    public SimulationGUI()
    {
	// set the title of the window
	super("Tasmanian Devil Simulation");

	// get the content pane so we can add stuff to it
	Container content = getContentPane();

	// top-level is a border layout
	content.setLayout(new BorderLayout());
	
	// controlPanel is the controls at the bottom
	JPanel controlPanel = new JPanel();
	controlPanel.setLayout(new GridLayout(4, 3));

	// label and text field for total devil entry
	controlPanel.add(new JLabel("Total devils:"));
	totField = new JTextField();
	controlPanel.add(totField);

	// button to create a world
	JButton createButton = new JButton("Create");
	controlPanel.add(createButton);
	createButton.addActionListener(new CreateListener());

	// label and text field for infected devil entry
	controlPanel.add(new JLabel("Infected devils:"));
	infectField = new JTextField();
	controlPanel.add(infectField);
 
	// button to step the world one step
	JButton stepButton = new JButton("Step");
	controlPanel.add(stepButton);
	stepButton.addActionListener(this);

	// label and text field for width of world entry
	controlPanel.add(new JLabel("Width of world:"));
	sizeField = new JTextField();
	controlPanel.add(sizeField);

	// label to show how many devils are infected
	infectedCountLabel = new JLabel("Infected:");
	controlPanel.add(infectedCountLabel);
	
	controlPanel.add(new JPanel());
	roadCheck = new JCheckBox("Road");
	controlPanel.add(roadCheck);

	// bottom panel to hold the inputs and the status (error) label
	JPanel bottomPanel = new JPanel(new BorderLayout());
	bottomPanel.add(controlPanel, BorderLayout.CENTER);
	errorLabel = new JLabel(" "); // placeholder so preferred height != 0
	bottomPanel.add(errorLabel, BorderLayout.SOUTH);
	
	// add the controls at the bottom of the window
	content.add(bottomPanel, BorderLayout.SOUTH);

	// make a world and view of that world
	view = new SimulationView();

	// add the main view to the center (it will expand to fill space)
	content.add(view, BorderLayout.CENTER);

	// set size of the window and open it
	setSize(600, 600);
	setVisible(true);

	// set behavior of the close button
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Handles action events from the "Step" button.
     *
     * @param e an event
     */
    public void actionPerformed(ActionEvent e)
    {
	if (world != null)
	    {
		world.update();
		view.repaint();
		infectedCountLabel.setText("Infected: " + world.countInfected());
	    }
    }

    /**
     * Handles action events from the "Create" button.
     */
    private class CreateListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    // reset backgrounds and clear error message
	    sizeField.setBackground(Color.WHITE);
	    totField.setBackground(Color.WHITE);
	    infectField.setBackground(Color.WHITE);
	    errorLabel.setText(" ");

	    try
		{
		    // declarations outside try/catch b/c of scope rules;
		    int size, total, infected;
		    try
			{
			    size = Integer.parseInt(sizeField.getText());
			}
		    catch (NumberFormatException ex)
			{
			    sizeField.setBackground(Color.RED);
			    
			    // only partially handled, so rethrow the exception
			    throw ex;
			}
		    
		    try
			{
			    total = Integer.parseInt(totField.getText());
			}
		    catch (NumberFormatException ex)
			{
			    totField.setBackground(Color.RED);
			    
			    // only partially handled, so rethrow the exception
			    throw ex;
			}
		    
		    try
			{
			    infected = Integer.parseInt(infectField.getText());
			}
		    catch (NumberFormatException ex)
			{
			    infectField.setBackground(Color.RED);
			    
			    // only partially handled, so rethrow the exception
			    throw ex;
			}
		    
		    // maybe an if would better here since it is easy
		    // to use one to test the values of size, etc. and
		    // thus make sure the IllegalArgumentException is
		    // never thrown by the World constructor in the
		    // first place
		    world = new World(size, total, infected, roadCheck.isSelected());
		    view.setWorld(world);
		    infectedCountLabel.setText("Infected: " + world.countInfected());
		}
	    catch (NumberFormatException ex)
		{
		    errorLabel.setText("Please enter numeric values");
		}
	    catch (World.IllegalWidthException ex)
		{
		    errorLabel.setText(ex.getMessage());
		    sizeField.setBackground(Color.RED);
		}
	    catch (World.IllegalPopulationException ex)
		{
		    errorLabel.setText(ex.getMessage());
		    totField.setBackground(Color.RED);
		}
	    catch (World.IllegalInfectedCountException ex)
		{
		    errorLabel.setText(ex.getMessage());
		    infectField.setBackground(Color.RED);
		}
	}
    }


    public static void main(String [] args)
    {
	// make a new window
	new SimulationGUI();
    }
}