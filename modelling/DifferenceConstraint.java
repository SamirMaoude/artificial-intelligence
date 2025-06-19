package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint {

    private Variable v1, v2;


    /**
     * @param v1
     * @param v2
     */
    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> liste = new HashSet<Variable>();
        liste.add(v1);
        liste.add(v2);
        return liste;
    }

    /**
     * Vérifie si la contrainte est satisfaite par l'instanciation donnée.
     * La contrainte est satisfaite si v1 != v2, dans la map instanciation
     * 
     * @param instanciation
     * 
     * @return true si les valeurs associées à v1 et v2 sont différentes, false sinon.
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

        return !valueOfV1.equals(valueOfV2);
    }

    @Override
    public String toString(){
        return this.v1.toString() + " != "+this.v2.toString();
    }
}