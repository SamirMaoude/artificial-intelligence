package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class Itemset {

    //represente un itemSet , c'est à dire un ensemble d'items associés à une frequence d'apparition dans un ensemble de transactions
    
    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency){
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Itemset [items=" + items + ", frequency=" + frequency + "]";
    }

    

}
