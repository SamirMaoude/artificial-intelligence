package blocksworld.heuristics;

import java.util.Map;

import modelling.BooleanVariable;
import modelling.Variable;
import planning.Heuristic;

public class SimilarityHeuristic implements Heuristic{

    private Map<Variable, Object> goal;
    private int alpha = -1; // Facteur alpha pour ajuster la similarité

    /**
     * Cette heuristique permet de calculer le cosinus de l'angle entre les vecteurs
     * représentant un état quelconque et l'état but
     * @param goal
     * @param alpha
     */
    public SimilarityHeuristic(Map<Variable, Object> goal, int alpha){
        this.goal = goal;
        this.alpha = alpha;
    }

    /**
     * Méthode d'estimation de l'heuristique, elle retourne la similarité cosinus entre l'état actuel et l'objectif
    */
    @Override
    public float estimate(Map<Variable, Object> state) {
        return this.alpha * cosineSimilarity(goal, state);
    }

    public static float dotProduct(Map<Variable, Object> goal, Map<Variable, Object> state) {
        float product = 0;
        for (Variable x: goal.keySet()) {

            
            if(!(x instanceof BooleanVariable)){
                int u = (int) goal.get(x);
                int v = (int) state.get(x);

                product += u * v;
            }
            
            
        }
        return product;
    }

    public static float norm(Map<Variable, Object> state) {
        return (float)Math.sqrt(dotProduct(state, state));
    }


    public static float cosineSimilarity(Map<Variable, Object> goal, Map<Variable, Object> state) {
        float product = dotProduct(goal, state);
        float norm1 = norm(goal);
        float norm2 = norm(state);
        
        return product / (norm1 * norm2);
    }





}
