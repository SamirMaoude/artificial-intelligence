package blocksworld.datamining;

import java.util.List;
import java.util.Random;
import java.util.Set;

import bwgenerator.BWGenerator;
import datamining.Apriori;
import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;
import modelling.BooleanVariable;

public class BwDataminingDemo {
    public static void main(String[] args) {

        // Initialisation
        int nbBlocks = 10;
        int nbPiles = 3;
        int n = 10_000;
        float frequency = (float) (2.0/3);
        float confidence = (float) (95.0/100);

        BwVariableBuilder variableBuilder = new BwVariableBuilder(nbBlocks, nbPiles);
        BWGenerator generator = new BWGenerator(nbBlocks, nbPiles); 
        Random random = new Random();

        // Remplissage de la base de données
        BooleanDatabase db = new BooleanDatabase(variableBuilder.getVariables());
        for (int i = 0; i < n; i++) {
            // Drawing a state at random
            List<List<Integer>> state = generator.generate(random);
            // Converting state to instance
            Set<BooleanVariable> instance = variableBuilder.buildInstance(state);
            // Adding state to database
            db.add(instance);
        }

        // Extraction des motifs
        System.out.println("******************* Patterns extraction ******************");
        Apriori apriori = new Apriori(db);
        Set<Itemset> patterns = apriori.extract(frequency);
        for(Itemset itemset: patterns){
            System.out.println(itemset+"\n");
        }
        System.out.print(patterns.size()+" patterns extracted\n");


        System.out.println("******************* Association rules extraction ******************");
        // Extraction des règles d'association
        BruteForceAssociationRuleMiner bruteForceAssociationRuleMiner = new BruteForceAssociationRuleMiner(db);

        Set<AssociationRule> extractedRules = bruteForceAssociationRuleMiner.extract(frequency, confidence);

        for(AssociationRule rule: extractedRules){
            System.out.println(rule+"\n");
        }

        System.out.println(extractedRules.size()+" rules extracted");
    }
}
