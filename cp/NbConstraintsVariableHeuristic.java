package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {


    private Set<Constraint> constraints;
    private boolean greatest;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean greatest){
        this.constraints = constraints;
        this.greatest = greatest;
    }

    /**
     * Renvoie la variable qui apparait dans le plus ou dans le moins de contraintes
     * en fonction de greatest
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {

        Variable bestVariable = null;
        
        // Initialise la meilleure taille de domaine en fonction du critère de l'heuristique
        int bestAppearance = this.greatest ? 0 : (int)Integer.MAX_VALUE;

        for(Variable variable: variables){
            int appearance = 0;
            for(Constraint constraint: this.constraints){
                if(constraint.getScope().contains(variable)) appearance++;
            }

            if(this.greatest){
                // Si on cherche le plus grand nombre d'apparition
                if(bestAppearance < appearance ){
                    bestAppearance = appearance;
                    bestVariable = variable;
                }
            }
            else{
                // Si on cherche le plus petit nombre d'apparition
                if(bestAppearance > appearance){
                    bestAppearance = appearance;
                    bestVariable = variable;
                }
            }


        }

        return bestVariable;
    }

    
    
}
