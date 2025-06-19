package cp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import modelling.Constraint;
import modelling.Variable;

public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    public Map<Variable, Object> solve() {

        // Liste des variables non instanciées
        Set<Variable> notInstancied = new HashSet<>();

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
     * Retourne une solution de backtracking en utilisant des heuristiques sur les
     * variables et sur leurs valeurs
     */
    public Map<Variable, Object> mac(Map<Variable, Object> state, Set<Variable> variables, Map<Variable, Set<Object>> domains){
        
        // Si toutes les variables sont instanciées, retourne l'état actuel comme solution
        if(variables.isEmpty()) return state;

        // choix de la variable à traiter, à travers l'heuristique
        Variable variable = this.variableHeuristic.best(variables, domains);
        variables.remove(variable);

        // Choisir l'ordre de traitement des valeurs en fonction de l'heuristique
        for(Object vi: this.valueHeuristic.ordering(variable, domains.get(variable))){
            Map<Variable, Object> nextState = new HashMap<>(state);

            nextState.put(variable, vi);

            // Vérifier si l'affectation actuelle est consistante
            if(this.isConsistent(nextState)){
                Map<Variable, Object> r = this.mac(nextState, variables, domains);
                
                // succès
                if(r != null) return r;
            }
        }

        variables.add(variable);// Remettre la variable pour les prochaines branches
        
        return null; // Aucune solution valide
    }
    
}
