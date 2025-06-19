package datamining;

import modelling.BooleanVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Cette classe représente une base de données booléenne contenant un ensemble d'items et une liste de transactions.
 * chaque transaction est un ensemble d'items présents
*/

public class BooleanDatabase {
    
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;


    public BooleanDatabase(Set<BooleanVariable> items){
        this.items = items;
        this.transactions = new ArrayList<>();
    }


    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }


    public Set<BooleanVariable> getItems() {
        return items;
    }


    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    

}
