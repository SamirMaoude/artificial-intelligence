package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint {

    private Variable v;
    private Set<Object> s;

    /**
     * @param v
     * @param s (sous domaine de v)
     */

    public UnaryConstraint(Variable v, Set<Object> s) {
        this.v = v;
        this.s = s;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> liste = new HashSet<Variable>();
        liste.add(v);
        return liste;
    }


    /**
     * Vérifie si la contrainte est satisfaite par l'instanciation donnée.
     * La contrainte est satisfaite si V inclu dans S
     * 
     * @param instanciation
     * 
     * @return true si v inclu dans S, false sinon.
     * 
     * @throws IllegalArgumentException Si l'instanciation ne fournit pas de valeur pour V
    */

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {

        for (Variable v : this.getScope()) {
            if (instanciation.get(v) == null)
                throw new IllegalArgumentException("L'instanciation ne fournit pas de valeurs");
        }

        Object valueOfV = instanciation.get(v);
        
        return this.s.contains(valueOfV);
    }

    @Override
    public String toString(){
        String representation = this.v + ": {";
        for (Object value : this.s) {
            representation += value.toString()+", ";
        }

        representation = representation.substring(0, representation.length() - 2);

        representation += "}";

        return representation;
    }

}
