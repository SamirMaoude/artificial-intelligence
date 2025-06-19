package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

public class Apriori extends AbstractItemsetMiner{

    public Apriori(BooleanDatabase booleanDatabase) {
        super(booleanDatabase);
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.booleanDatabase;
    }


    // Extraction des motifs fréquents en fonction d'une fréquence minimale
    @Override
    public Set<Itemset> extract(float minFrequency) {
        
        // Ensemble des motifs fréquents trouvés
        Set<Itemset> result = new HashSet<Itemset>();
        
        List<SortedSet<BooleanVariable>> L = new ArrayList<SortedSet<BooleanVariable>>();

        // Singletons fréquents
        for(Itemset singleton: this.frequentSingletons(minFrequency)){
            SortedSet<BooleanVariable> sortedSet = new TreeSet<BooleanVariable>(AbstractItemsetMiner.COMPARATOR);
            sortedSet.addAll(singleton.getItems());
            L.add(sortedSet);
            result.add(singleton);
        }

        while(!L.isEmpty()){

            List<SortedSet<BooleanVariable>> candidates = new ArrayList<>(L);
            
            L.clear(); // On vide L pour préparer la prochaine itération

            for(int i = 0; i < candidates.size(); i++){

                SortedSet<BooleanVariable> items1 = candidates.get(i);

                for(int j = i+1; j < candidates.size(); j++){

                    SortedSet<BooleanVariable> items2 = candidates.get(j);

                    SortedSet<BooleanVariable> combinedItems = combine(items1, items2);

                    if(combinedItems != null){

                        // Vérifier que tous les sous-ensembles sont fréquents
                        if(allSubsetsFrequent(combinedItems, candidates)){

                            float frequency = this.frequency(combinedItems);

                            //Si la fréquence dépasse le seuil, on l'ajoute aux résultats et à L pour les prochains candidats
                            if(frequency >= minFrequency){
                                result.add(new Itemset(combinedItems, frequency));
                                L.add(combinedItems);
                            }
                            
                        }
                    }
                    

                }
            }

        }


        return result;

    }
    //identifie les ItemSet de taille 1 qui sont frequents dans la base de données
    //@param minFrequency la frequence minimale requise pour qu'un singleton soint consideré comme frequent
    // @return un ensemble d'itemset de taille 1 frequent
    public Set<Itemset> frequentSingletons(float minFrequency){
        Set<Itemset> singletons = new HashSet<>();

        for(BooleanVariable item: this.booleanDatabase.getItems()){

            Set<BooleanVariable> singleton = new HashSet<>();
            singleton.add(item);

            float frequency = this.frequency(singleton);
            if(frequency >= minFrequency) singletons.add(new Itemset(singleton, frequency));
        }

        return singletons;
    }


    /**
     * combine deux ensembles d'items pour generer un nouvel itemset s'ils partagent tous les elements sauf le dernier
     * @param items1 Premier ensemble d'items à combiner
     * @param items2 Deuxième ensemble d'items à combiner
     * @return Un nouvel ensemble combiné d'items ou null si la combinaison n'est pas possible
    */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> items1, SortedSet<BooleanVariable> items2) {
        if (items1.size() == 0 || items2.size() == 0 || items1.equals(items2)) return null;

        
        Iterator<BooleanVariable> it1 = items1.iterator();
        Iterator<BooleanVariable> it2 = items2.iterator();
        SortedSet<BooleanVariable> mergedItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);

        // Vérifier si tous les éléments sauf le dernier sont égaux
        for (int i = 0; i < items1.size() - 1; i++) {
            BooleanVariable v1 = it1.next();
            BooleanVariable v2 = it2.next();
            if (!v1.equals(v2)) return null;
            mergedItems.add(v1);
        }

        // Ajouter le dernier élément de chaque ensemble
        mergedItems.add(it1.next());
        mergedItems.add(it2.next());

        return mergedItems;
    }


    /**
     * Verifie si tous les sous-ensembles d'un itemset donné sont frequent dans une collection
     * @param items l'ensemble d'items à tester
     * @param itemset la collection de sous-ensembles fréquents
     * @return true si tous les sous ensembles de l'ensemble donné sont fréquents sinon false
    */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itemsCollection){
        
        Iterator<BooleanVariable> it = items.iterator();

        // retirer un élément à chaque itération et verifie si le sous-ensemble est fréquent
        while(it.hasNext()){
            SortedSet<BooleanVariable> itemsCopy = new TreeSet<BooleanVariable>(AbstractItemsetMiner.COMPARATOR);
            itemsCopy.addAll(items);
            itemsCopy.remove(it.next());
            if(!itemsCollection.contains(itemsCopy)) return false; // echec
        }

        return true; //succès si tous les sous-ensembles sont frequents
    }

}
