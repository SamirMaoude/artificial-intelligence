package cp;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import modelling.Constraint;
import modelling.Variable;

public class ArcConsistency {

    private Set<Constraint> constraints;

    public ArcConsistency(Set<Constraint> constraints){

        for(Constraint constraint : constraints){
            int scopeSize = constraint.getScope().size();
            if(!(scopeSize == 1 || scopeSize == 2)){
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }
        }

        this.constraints = constraints;

    }

    /**
     * Applique la consistance des nœuds en supprimant les valeurs non valides 
     * des domaines des variables en fonction des contraintes.
     * 
     * @param domains Map<Variable, Set<Object>>
     * @return true si la consistance des noeuds est maintenue, false sinon
    */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains){

        // Vérifier la consistance pour chaque variable x
        for(Variable x: domains.keySet()){

            Set<Variable> scope = new HashSet<>();
            scope.add(x);

            // Vérifier chaque valeur dans le domaine de la variable x
            for(Object v: new HashSet<>(domains.get(x))){
                
                for(Constraint c: this.constraints){

                    if(c.getScope().containsAll(scope) && scope.containsAll(c.getScope())){
                        Map<Variable, Object> state = new HashMap<>();
                        state.put(x, v);

                        // Supprimer la valeur si elle ne satisfait pas la contrainte
                        if(!c.isSatisfiedBy(state)){
                            domains.get(x).remove(v);
                        }
                        
                    }
                }

            }
        }

        // Vérifier si toutes les variables ont encore des valeurs dans leur domaine
        for(Variable x: domains.keySet()){
            if(domains.get(x).isEmpty()) return false;
        }

        return true;
    }


    /**
     * Révise le domaine d'une variable v1 en fonction des valeurs de la variable v2 
     * pour maintenir la consistance des arcs.
     * 
     * @param v1
     * @param d1 Le domaine de v1
     * @param v2
     * @param d2 Le domaine de v2
     * @return true si des valeurs ont été supprimées du domaine de v1, false sinon
    */
    public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2){


        boolean del = false;

        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);

        // Vérifier chaque valeur vi dans le domaine de v1
        for(Object vi: new HashSet<>(d1)){
            boolean viable = false;

            // Vérifier chaque valeur vj dans le domaine de v2
            for(Object vj: d2){
                boolean allStatisfied = true;

                for(Constraint c: this.constraints){
                    if(c.getScope().containsAll(scope) && scope.containsAll(c.getScope())){
                        Map<Variable, Object> state = new HashMap<>();
                        state.put(v1, vi);
                        state.put(v2, vj);

                        // Si une contrainte n'est pas satisfaite, vj n'est pas support de vi
                        if(!c.isSatisfiedBy(state)){
                            allStatisfied = false;
                            break;
                        }
                    }
                }

                if(allStatisfied){
                    viable = true;
                    break;
                }

            }

            if(!viable){
                d1.remove(vi);
                del = true;
            }
        }

        return del;
    }

    public boolean ac1(Map<Variable, Set<Object>> domains){
        
        if(!this.enforceNodeConsistency(domains))return false;



        boolean change = false;
        do{
            change = false;

            for(Variable v1: domains.keySet()){
                
                for(Variable v2: domains.keySet()){
                    if(!v1.equals(v2)){
                        if(this.revise(v1, domains.get(v1), v2, domains.get(v2))){
                            change = true; // Un changement a eu lieu
                        }
                    }
                }
            }
        }while(change==true);

        for(Variable x: domains.keySet()){
            if(domains.get(x).isEmpty()) return false;
        }


        return true; // succès
    }

}
