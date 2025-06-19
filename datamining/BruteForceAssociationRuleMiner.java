package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    private BooleanDatabase database;

    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return database;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {

        // Apriori pour obtenir les itemsets fréquents
        Apriori apriori = new Apriori(this.getDatabase());
        Set<Itemset> itemsets = apriori.extract(minFrequency);

        // Liste des règles d'association extraites
        Set<AssociationRule> results = new HashSet<>();

        for(Itemset itemset: itemsets){
            
            Set<BooleanVariable> items = itemset.getItems();

            // Génération de toutes les prémisses possibles
            Set<Set<BooleanVariable>> candidatePremises = allCandidatePremises(items);

            for(Set<BooleanVariable> premise: candidatePremises){
                
                // Conclusion : les éléments restants après avoir retiré ceux de la prémisse
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                float confidence = confidence(premise, conclusion, itemsets);

                // Si la confiance est supérieure ou égale au seuil, on ajoute la règle aux résultats
                if(confidence >= minConfidence){
                    results.add(new AssociationRule(premise, conclusion, itemset.getFrequency(), confidence));
                }
                

            }
        }

        return results;
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> allSubsets = new HashSet<>();
        generateSubsets(new HashSet<>(), items, allSubsets, items);
        return allSubsets;
    }
    
    /**
     * Méthode récursive pour générer tous les sous-ensembles possibles
     *
     * @param currentSubset le sous-ensemble actuel en cours de formation
     * @param remainingItems les éléments restants à traiter pour la génération de sous-ensembles
     * @param allSubsets l'ensemble de tous les sous-ensembles générés
     * @param originalSet l'ensemble initial des éléments pour vérifier la taille du sous-ensemble
     */
    private static void generateSubsets(Set<BooleanVariable> currentSubset, Set<BooleanVariable> remainingItems, Set<Set<BooleanVariable>> allSubsets, Set<BooleanVariable> originalSet) {
        if (remainingItems.isEmpty()) {
            // Ajouter le sous-ensemble seulement s'il est non vide et n'est pas égal à l'ensemble complet
            if (!currentSubset.isEmpty() && !currentSubset.equals(originalSet)) {
                allSubsets.add(new HashSet<>(currentSubset));
            }
            return;
        }
    
        BooleanVariable nextItem = remainingItems.iterator().next();
        Set<BooleanVariable> remaining = new HashSet<>(remainingItems);
        remaining.remove(nextItem);

        // Générer des sous-ensembles en incluant l'élément courant
        currentSubset.add(nextItem);
        generateSubsets(currentSubset, remaining, allSubsets, originalSet);
        
        // Générer des sous-ensembles sans inclure l'élément courant
        currentSubset.remove(nextItem);
        generateSubsets(currentSubset, remaining, allSubsets, originalSet);
    
    }

}
