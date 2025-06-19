package planning;

import java.util.Map;

import modelling.Variable;

public class BasicGoal implements Goal {

    private Map<Variable, Object> instantiation;

    public BasicGoal(Map<Variable, Object> instantiation) {
        this.instantiation = instantiation;
    }

    /**
     * Vérifie si toutes les variable du but sont satisfaites par l'état
     * 
     * @param state 
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        for (Variable variable : this.instantiation.keySet()) {
            if (this.instantiation.get(variable) != state.get(variable))
                return false;
        }
        return true;
    }

    @Override
    public String toString(){
        String representation = "{";
        for (Variable variable : this.instantiation.keySet()) {
            Object value = this.instantiation.get(variable);
            representation += variable.toString()+": "+value.toString()+", ";
        }

        representation = representation.substring(0, representation.length() - 2);

        representation += "}";

        return representation;
    }

}
