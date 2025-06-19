package blocksworld.heuristics;

import java.util.Map;

import modelling.BooleanVariable;
import modelling.Variable;
import planning.Heuristic;

public class MisplacedBlocksHeuristic implements Heuristic {

    private Map<Variable, Object> goal;

    /**
     * Cette heuristique compte le nombre de blocs mal placés dans un état
     * quelconque, par rapport à l'état but
     * @param goal
     */
    public MisplacedBlocksHeuristic(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        // Retourner le nombre de blocs mal placés
        return (float) misplacedBlocks(state, goal);
    }

    /**
     * Méthode qui compte le nombre de blocs mal placés
    */
    public int misplacedBlocks(Map<Variable, Object> state, Map<Variable, Object> goal) {
        int count = 0;
        
        // Parcours de l'état actuel et de l'objectif pour vérifier les blocs mal placés
        for (Variable variable : goal.keySet()) {
            Object stateValue = state.get(variable);
            Object goalValue = goal.get(variable);
            
            if(variable instanceof BooleanVariable) continue;
            // Comparaison de la valeur de l'état actuel avec celle de l'objectif
            if (!goalValue.equals(stateValue)) {
                count++;
            }
        }
        
        return count;
    }
}
