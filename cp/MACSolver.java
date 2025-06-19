package cp;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class MACSolver extends AbstractSolver {

    private ArcConsistency arcConsistency;
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);

        this.arcConsistency = new ArcConsistency(constraints);
    }

    @Override
    public Map<Variable, Object> solve() {

        // Liste des variables non instanciées
        LinkedList<Variable> notInstancied = new LinkedList<>();

        // Initialisation des domaines pour chaque variable
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for(Variable variable: this.variables){
            notInstancied.add(variable);
            domains.put(variable, new HashSet<>(variable.getDomain()));
        }

        startTime = System.nanoTime();
        Map<Variable, Object> solution = this.mac(new HashMap<>(), notInstancied, domains);
        endTime = System.nanoTime();

        return solution;
    }

    /**
     * Retourne une solution de backtracking en vérifiant la consistence des domaines
     */
    public Map<Variable, Object> mac(Map<Variable, Object> state, LinkedList<Variable> variables, Map<Variable, Set<Object>> domains){
        
        // Si toutes les variables sont instanciées, retourne l'état actuel comme solution
        if(variables.isEmpty()) return state;

        if(!this.arcConsistency.ac1(domains)) return null;

        Variable variable = variables.pop();
        for(Object vi: domains.get(variable)){
            Map<Variable, Object> nextState = new HashMap<>(state);

            nextState.put(variable, vi);
            
            // Vérifier si l'affectation actuelle est consistante
            if(this.isConsistent(nextState)){
                Map<Variable, Object> r = this.mac(nextState, variables, domains);

                // succès
                if(r != null) return r;
            }
        }

        variables.add(variable); // Remettre la variable pour les prochaines branches
        
        return null; // Aucune solution valide
    }

}
