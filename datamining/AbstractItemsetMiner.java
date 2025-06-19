package datamining;

import java.util.Comparator;
import java.util.Set;

import modelling.BooleanVariable;

/**
 * Cette classe abstraite fournit une méthode pour calculer la frequence d'un ensemble
 * d'items dans une base de données
*/

public abstract class AbstractItemsetMiner implements ItemsetMiner{

    protected BooleanDatabase booleanDatabase;

    public AbstractItemsetMiner(BooleanDatabase booleanDatabase){
        this.booleanDatabase = booleanDatabase;
    }

    public BooleanDatabase getBooleanDatabase() {
        return booleanDatabase;
    }


    /**
     * Calcule la fréquence d'apparition d'un ensemble d'items dans l'ensemble des transactions de la bd
    */
    public float frequency(Set<BooleanVariable> items){
        float occurence = 0;
        float nbTransactions = 0;

        for(Set<BooleanVariable> transaction: this.booleanDatabase.getTransactions()){
            if(transaction.containsAll(items)) occurence++;
            nbTransactions++;
        }

        if(nbTransactions == 0) return 0;
        
        return occurence / nbTransactions;
    }

    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());


}
