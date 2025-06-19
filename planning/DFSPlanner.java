package planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Collections;

import modelling.Variable;

public class DFSPlanner extends AbstractPlanner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;


    /**
     * 
     * @param initialState L'état initial du problème.
     * @param actions L'ensembles des actions possibles.
     * @param goal Le But.
     */
    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.visitedNode = 0;
        this.nodeCount = false;
    }

    /**
     * Effectue la recherche en profondeur (DFS) pour trouver une séquence d'actions
     * permettant de satisfaire le but à partir de l'état initial.
     * 
     * @return Une liste d'actions (List<Action>) si un plan est trouvé, sinon null.
     */
    @Override
    public List<Action> plan() {
        startTime = System.nanoTime();
        Map<Variable, Object> state = this.getInitialState();
        Stack<Action> plan = new Stack<Action>();
        Set<Map<Variable, Object>> closed = new HashSet<>(); // Ensemble d'états déjà visités
        closed.add(state);

        Stack<Action> result = this.DFS(state, plan, closed);

        endTime = System.nanoTime();
        if (result != null) {
            List<Action> planToArray = new ArrayList<Action>();
            while (!result.empty()) {
                Action action = result.pop();
                planToArray.add(action);
            }

            Collections.reverse(planToArray); // Renverser le tableau avant son renvoi

            return planToArray;
        }

        
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
     * Effectue une recherche en profondeur pour trouver une séquence d'actions menant au but.
     * 
     * @param state L'état courant.
     * @param plan La pile représentant le plan d'actions en cours.
     * @param closed Un ensemble d'états déjà visités.
     * 
     * @return Une pile d'actions menant au but, ou null si aucun plan n'est trouvé.
     */

    public Stack<Action> DFS(Map<Variable, Object> state, Stack<Action> plan, Set<Map<Variable, Object>> closed) {

        if (this.nodeCount) this.visitedNode++;

        // Si le but est satisfait avec l'état courant, renvoyer le plan
        if (this.getGoal().isSatisfiedBy(state))
            return plan;

        // Parcourir toutes les actions disponibles
        for (Action action : this.getActions()) {
            Map<Variable, Object> nextState;

            if (action.isApplicable(state)) {

                nextState = action.successor(state);

                // Si le successeur n'a pas été vu, continuer la recherche
                if (!closed.contains(nextState)) {
                    plan.push(action);
                    closed.add(nextState);
                    Stack<Action> subplan = this.DFS(nextState, plan, closed);
                    if (subplan != null)
                        return subplan; // Solution

                    plan.pop(); // Retour sur trace
                }

            }

        }

        return null;

    }

    

}
