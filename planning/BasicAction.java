package planning;

import java.util.HashMap;
import java.util.Map;

import modelling.Variable;

public class BasicAction implements Action {

    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effect;
    private int cost;


    /** 
     * @param precondition 
     * @param effect effet appliqué lorsque la precondition est respectée
     * @param cost Le coût associé à l'exécution de l'action.
     */
    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }


    /**
     * Vérifie si l'action est applicable dans un état donné.
     * L'action est applicable si toutes les variables dans les préconditions sont
     * égales aux valeurs correspondantes dans l'état actuel.
     * 
     * @param state L'état courant
     * 
     * @return true si l'état satisfait les préconditions de l'action, false sinon.
     */

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {

        for (Variable variable : this.precondition.keySet()) {
            
            // Si une précondition n'est pas satisfaite dans state, l'action n'est pas applicable
            if ((this.precondition.get(variable) != state.get(variable))) {
                return false;
            }

        }

        return true;

    }

    /**
     * Détermine le successeur après l'application de l'action.
     * Si l'action est applicable, cette méthode modifie l'état en fonction des effets de l'action.
     * Si elle n'est pas applicable, l'état reste inchangé.
     * 
     * @param state L'état courant
     * 
     * @return Le nouvel état après application de l'action, ou l'état initial si l'action n'est pas applicable.
     */

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {

        if (!this.isApplicable(state)) return state;

        Map<Variable, Object> nextState = new HashMap<>(state); // copie

        for (Variable variable : this.effect.keySet()) {
            Object value = this.effect.get(variable);
            nextState.put(variable, value);
        }

        return nextState;

    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public String toString(){

        String representationPrecondition = "{";
        for (Variable variable : this.precondition.keySet()) {
            Object value = this.precondition.get(variable);
            representationPrecondition += variable.toString()+": "+value.toString()+", ";
        }

        representationPrecondition = representationPrecondition.substring(0, representationPrecondition.length() - 2);

        representationPrecondition += "}";


        String representationEffect = "{";
        for (Variable variable : this.effect.keySet()) {
            Object value = this.effect.get(variable);
            representationEffect += variable.toString()+": "+value.toString()+", ";
        }

        representationEffect = representationEffect.substring(0, representationEffect.length() - 2);

        representationEffect += "}";

        return representationPrecondition + " =====> " +representationEffect + " cost: "+this.getCost();
    }

}
