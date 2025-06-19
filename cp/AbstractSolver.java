package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    protected long startTime = 0;
    protected long endTime = 0;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){

        this.variables = variables;
        this.constraints = constraints;

    }

    /**
     * Vérifie si toutes les contraintes sont satisfaites par l'état
     *
     * @param state
     * @return true si l'état est consistant, false sinon
    */
    public boolean isConsistent(Map<Variable, Object> state){
        for (Constraint constraint : this.constraints) {
            if(state.keySet().containsAll(constraint.getScope())){

                // Retourne false dès qu'une contrainte est insatisfaite
                if(!constraint.isSatisfiedBy(state)) return false;

            }
        }

        return true; //succès
    }

    @Override
    public long getExecutionTime() {
        return endTime - startTime;
    }
    
}
