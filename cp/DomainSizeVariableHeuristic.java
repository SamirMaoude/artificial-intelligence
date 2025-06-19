package cp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic{

    private boolean greatest;

    public DomainSizeVariableHeuristic(boolean greatest){
        this.greatest = greatest;
    }

    /**
     * Renvoie la variable avec le plus ou le moins de valeur dans son domaine
     * en fonction de greatest
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable bestVariable = null;
        
        // Initialise la meilleure taille de domaine en fonction du critère de l'heuristique
        int bestDomain = this.greatest ? 0 : (int)Integer.MAX_VALUE;

        for(Variable variable: variables){
            
            int domainSize = domains.getOrDefault(variable, new HashSet<>()).size();
            
           
            if(this.greatest){
                // Si on cherche le plus grand domaine
                if(bestDomain < domainSize ){
                    bestDomain = domainSize;
                    bestVariable = variable;
                }
            }
            else{
                // Si on cherche le plus petit domaine
                if(bestDomain > domainSize){
                    bestDomain = domainSize;
                    bestVariable = variable;
                }
            }

        }

        return bestVariable;
    }
    
}
