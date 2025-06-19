package planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import modelling.Variable;

public class AStarPlanner extends AbstractPlanner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;

        
    /**
     * @param initialState L'état initial du problème.
     * @param actions L'ensembles des actions possibles.
     * @param goal Le But.
     * @param heuristic heuristique pour estimer les coûts restants.
     */
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.visitedNode = 0;
        this.nodeCount = false;
    }

    /**
     * Planifie une séquence d'actions à partir de l'état initial jusqu'au but en utilisant l'algorithme A*.
     * 
     * @return Une liste d'actions correspondant au plan trouvé, ou null si aucun plan n'est trouvé.
     */
    @Override
    public List<Action> plan() {

        startTime = System.nanoTime();

        Map<Variable, Object> state = this.getInitialState();

        // Enregistre le père de chaque état pour remonter le chemin vers l'état initial
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();

        // Stocke l'action menant à chaque état
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();


        Map<Map<Variable, Object>, Integer> distance = new HashMap<>();

        // File de priorité pour les états à explorer. Les états avec le plus petit coût seront visités en priorité
        PriorityQueue<StateCost> open = new PriorityQueue<StateCost>();

        father.put(state, null);// L'état initial n'a pas de père
        distance.put(state, 0);
        open.add(new StateCost(this.heuristic.estimate(state), state));
        
        while (!open.isEmpty()) {

            StateCost stateCost = open.remove();
            Map<Variable, Object> currentState = stateCost.getState();

            if(this.nodeCount) this.visitedNode++;

            // Si l'état satisfait le but, générer le plan
            if (this.getGoal().isSatisfiedBy(currentState)) {
                endTime = System.nanoTime();
                return this.get_astar_plan(father, plan, currentState);
            }

            

            // Explorer les actions possibles
            for (Action action : this.getActions()) {
                if (action.isApplicable(currentState)) {
                    Map<Variable, Object> nextState = action.successor(currentState);

                    if(distance.get(nextState) == null) distance.put(nextState, Integer.MAX_VALUE);

                    // Mise à jour si un chemin plus court est trouvé
                    if (distance.get(nextState) > distance.get(currentState) + action.getCost()) {
                        
                        int cost = distance.get(currentState) + action.getCost();

                        distance.put(nextState, cost);
                        father.put(nextState, currentState);
                        plan.put(nextState, action);

                        open.add(new StateCost(this.heuristic.estimate(nextState) + cost, nextState));
                    }

                }
            }
        }

        endTime = System.nanoTime();
        return null;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    /**
     * Génère le plan final en remontant du but vers l'état initial.
     * 
     * @param father
     * @param plan
     * @param state L'état à partir duquel remonter le plan.
     * 
     * @return Une liste d'actions représentant le plan (List<Action>).
     */
    public List<Action> get_astar_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
            Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> state) {

        List<Action> result = new ArrayList<Action>();


        while (father.get(state) != null) {
            Action action = plan.get(state);
            state = father.get(state);
            result.add(action);
        }

        Collections.reverse(result);

        return result;

    }

    

}
