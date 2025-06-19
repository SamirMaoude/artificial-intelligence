package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

/**
  * Cette classe fournit des méthodes statiques qui permettent
  * de calculer la fréquence et la confiance des items dans un ensemble d'itemsets
*/

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){

        for(Itemset itemset: itemsets){
            if(itemset.getItems().equals(items)) return itemset.getFrequency();
        } 

        throw new IllegalArgumentException("items inconnus");
    }

    // calcule la confiance d'une regle d'association basee sur une premisse et une conclusion
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){

        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);

        return frequency(union, itemsets) / frequency(premise, itemsets);


    }


}
