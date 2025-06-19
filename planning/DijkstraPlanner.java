package planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;



import modelling.Variable;



public class DijkstraPlanner extends AbstractPlanner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;


    /**
     * @param initialState L'état initial du problème.
     * @param actions L'ensembles des actions possibles.
     * @param goal Le But.
     */
    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.visitedNode = 0;
        this.nodeCount = false;
    }

    /**
     * Utilise l'algo de Dijkstra pour trouver une séquence d'actions
     * permettant de satisfaire le but à partir de l'état initial.
     * 
     * @return Une liste d'actions (List<Action>) si un plan est trouvé, sinon null.
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

        // File de priorité pour les états satisfaisant le but
        PriorityQueue<StateCost> goals = new PriorityQueue<StateCost>();

        father.put(state, null);// L'état initial n'a pas de père
        distance.put(state, 0);
        open.add(new StateCost(0, state));

        while (!open.isEmpty()) {

            StateCost stateCost = open.remove();
            Map<Variable, Object> currentState = stateCost.getState();

            if(this.nodeCount) this.visitedNode++;

            // Si l'état satisfait le but
            if (this.getGoal().isSatisfiedBy(currentState)) {
                endTime = System.nanoTime();
                goals.add(stateCost);
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

                        open.add(new StateCost(cost, nextState));
                    }

                }
            }
        }

        // Générer le plan si le but est atteint
        if(!goals.isEmpty()){
            endTime = System.nanoTime();
            return this.get_dijkstra_plan(father, plan, goals);
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
     * @param goals File de priorité contenant les états satisfaisant le but.
     * @return Une liste d'actions représentant le plan optimal (List<Action>).
     */
    public List<Action> get_dijkstra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
            Map<Map<Variable, Object>, Action> plan, PriorityQueue<StateCost> goals) {

        List<Action> result = new ArrayList<Action>();
        
        Map<Variable, Object> state = null;
        
        // Sélectionner l'état but avec le coût le plus bas
        if(!goals.isEmpty()){
            StateCost stateCost = goals.remove();
            state = stateCost.getState();
        }


        while (father.get(state) != null) {
            Action action = plan.get(state);
            state = father.get(state);
            result.add(action);
        }

        Collections.reverse(result);

        return result;

    }

}
