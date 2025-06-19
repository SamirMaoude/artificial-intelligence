package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {

    private Variable v1, v2;
    private Set<Object> s1, s2;

    /**
     * @param v1
     * @param s1 (sous domaine de v1)
     * @param v2
     * @param s2 (sous domaine de v2)
     */
    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        this.v1 = v1;
        this.v2 = v2;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> liste = new HashSet<Variable>();
        liste.add(v1);
        liste.add(v2);
        return liste;
    }

    /**
     * Vérifie si la contrainte d'implication est satisfaite par l'instanciation donnée.
     * La contrainte est de la forme : (v1 ∈ S1) → (v2 ∈ S2)
     * 
     * @param instanciation
     * 
     * @return true dès lors qu’une valeur de S1 est affectée à v1, une valeur de S2 est affectée à v2
     *         ou si ce n’est pas une valeur de S1 qui est affectée à v1, quelle que soit la valeur affectée à v2
     *         sinon false
     * 
     * @throws IllegalArgumentException Si l'instanciation ne fournit pas de valeur pour l'une des variables
     *                                  concernées (v1 ou v2).
    */

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) throws IllegalArgumentException {

        for (Variable v : this.getScope()) {
            if (instanciation.get(v) == null)
                throw new IllegalArgumentException("L'instanciation ne fournit pas toutes les valeurs pour "+this);
        }

        Object valueOfV1 = instanciation.get(v1);
        Object valueOfV2 = instanciation.get(v2);

        if (!this.s1.contains(valueOfV1))
            return true;

        if (this.s1.contains(valueOfV1) && this.s2.contains(valueOfV2))
            return true;

        return false;
    }

    @Override
    public String toString(){
        String representation1 = this.v1 + ": {";
        for (Object value : this.s1) {
            representation1 += value.toString()+", ";
        }

        representation1 = representation1.substring(0, representation1.length() - 2);

        representation1 += "}";


        String representation2 = this.v2 + ": {";
        for (Object value : this.s2) {
            representation2 += value.toString()+", ";
        }

        representation2 = representation2.substring(0, representation2.length() - 2);

        representation2 += "}";

        return representation1 + " ==> " + representation2;
    }

}
