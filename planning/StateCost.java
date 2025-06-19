package planning;

import java.util.Map;

import modelling.Variable;

public class StateCost implements Comparable<StateCost> {
    

    private Float cost;
    private Map<Variable, Object> state;

    /**
     * 
     * @param cost Le coût associé à l'état (Float).
     * @param state
     */
    public StateCost(Float cost, Map<Variable, Object> state){
        this.cost = cost;
        this.state = state;
    }

    public StateCost(Integer cost, Map<Variable, Object> state){
        this.cost = cost.floatValue();
        this.state = state;
    }


    public Float getCost() {
        return cost;
    }


    public Map<Variable, Object> getState() {
        return state;
    }

    /**
     * Compare cet objet StateCost à un autre en fonction du coût.
     * 
     * Ceci sera utile pour les PriorityQueue utilisés dans Dijkstra et A*,
     * en vue de séléctionner l'état avec le moindre coût
     * 
     * @param o L'autre objet StateCost à comparer.
     * @return 1 si le coût de cet objet est supérieur, -1 si inférieur, 0 si égal.
     */
    @Override
    public int compareTo(StateCost o) {

        /*
         * Comparer par ordre décroissant par rapport à la distance
         */
        if (this.getCost() > o.getCost()) return 1;
        
        if (this.getCost() < o.getCost()) return -1;
                        
        return 0;
    }



    
}