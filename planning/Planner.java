package planning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Planner
 */
public interface Planner {

    public List<Action> plan();

    public Map<Variable, Object> getInitialState();

    public Set<Action> getActions();

    public Goal getGoal();

    public void activateNodeCount(boolean activate);

    public int getVisitedNode();

    public long getExecutionTime();


}