/**
 * An agent in an agent-based simulation.
 */

public interface Agent extends Patient
{
    /**
     * Updates this agent for the result of one time step.
     */
    public void update();

    /**
     * Calls acceptAction for the patient parameter.
     *
     * @param p a patient
     */
    public void actOn(Patient p);

    /**
     * Reports whether this agent is alive.
     *
     * @return true if this agent is alive, false otherwise
     */
    public boolean isAlive();

    /**
     * Spawns an agent.
     *
     * @return the spawned agent, or null for none
     */
    public Agent spawn();

    /**
     * Returns the hex where this agent is located.
     *
     * @return the hex where this agent is located
     */
    public Hex getLocation();
}
