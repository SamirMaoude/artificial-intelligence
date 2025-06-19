package cp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve() {

        // Liste des variables non instanciées
        LinkedList<Variable> notInstancied = new LinkedList<>();

        notInstancied.addAll(this.variables);

        startTime = System.nanoTime();
        Map<Variable, Object> solution = this.backtracking(new HashMap<>(), notInstancied);
        endTime = System.nanoTime();

        return solution;
    }

    public Map<Variable, Object> backtracking(Map<Variable, Object> state, LinkedList<Variable> variables){
        
        // Retourne l'état actuel si toutes les variables sont instanciées
        if(variables.isEmpty()) return state;

        Variable variable = variables.pop();

        for(Object vi: variable.getDomain()){
            Map<Variable, Object> nextState = new HashMap<>(state);

            nextState.put(variable, vi);

            // Vérifier si l'affectation actuelle est consistante
            if(this.isConsistent(nextState)){
                Map<Variable, Object> r = this.backtracking(nextState, variables);

                // succès
                if(r != null) return r;
            }
        }


        variables.add(variable); // Remettre la variable pour les prochaines branches
        
        return null; // Aucune solution valide
    }

}
