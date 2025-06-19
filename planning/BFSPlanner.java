package planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import modelling.Variable;

public class BFSPlanner extends AbstractPlanner {

    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    /**
     * @param initialState L'état initial du problème.
     * @param actions L'ensembles des actions possibles.
     * @param goal Le But.
     */
    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.visitedNode = 0;
        this.nodeCount = false;
    }


    /**
     * Effectue la recherche en largeur (BFS) pour trouver une séquence d'actions
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

        // Ensemble des états déjà visités
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(state);

        // File pour les états à explorer
        Queue<Map<Variable, Object>> open = new LinkedList<Map<Variable, Object>>();
        open.add(state);

        father.put(state, null); // L'état initial n'a pas de père

        // Si l'état initial satisfait déjà l'objectif, retourner un plan vide
        if (this.getGoal().isSatisfiedBy(state)) {
            endTime = System.nanoTime();
            return new ArrayList<Action>();
        }

        while (!open.isEmpty()) {

            Map<Variable, Object> currentState = open.remove();

            closed.add(currentState);

            if(this.nodeCount) this.visitedNode++;

            // Explorer les actions possibles
            for (Action action : this.getActions()) {
                if (action.isApplicable(currentState)) {
                    Map<Variable, Object> nextState = action.successor(currentState);
                    
                    // Si le successeur n'a pas été visité
                    if (!closed.contains(nextState) && !open.contains(nextState)) {

                        father.put(nextState, currentState); // Enregistrer le père de l'état

                        plan.put(nextState, action); // Enregistrer l'action menant à cet état

                        // Si l'état satisfait le but, générer le plan
                        if (this.getGoal().isSatisfiedBy(nextState)) {
                            endTime = System.nanoTime();
                            return this.get_bfs_plan(father, plan, nextState);
                        }

                        open.add(nextState);
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
    public List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
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
