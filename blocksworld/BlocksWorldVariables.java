package blocksworld;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.BooleanVariable;
import modelling.Variable;

public class BlocksWorldVariables {

    private int nbBlocks;
    private int nbPiles;

    private Map<Integer, Variable> onB = new HashMap<>();
    private Map<Integer, BooleanVariable> fixedB = new HashMap<>();
    private Map<Integer, BooleanVariable> freeP = new HashMap<>();
    private Set<Variable> variables = new HashSet<>();

    /**
     * Classe définissant les variables du mondes des blocs
     * @param nbBlocks
     * @param nbPiles
     */
    public BlocksWorldVariables(int nbBlocks, int nbPiles) {
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;

        // Création des variables On et fixed des blocks
        for (int b = 0; b < nbBlocks; b++) {

            Variable on = new Variable("on" + b, makeDomain(b));
            onB.put(b, on);
            
            BooleanVariable fixed = new BooleanVariable("fixed" + b);
            fixedB.put(b, fixed);

            // Ajout au Set des variables
            variables.add(on);
            variables.add(fixed);
        }

        // Création des variables free des piles
        for (int p = 0; p < nbPiles; p++) {

            BooleanVariable free = new BooleanVariable("free" + p);
            freeP.put(p, free);

            // Ajout au Set des variables
            variables.add(free);
        }
    }

    
    /** 
     * Construction du domaine d'un bloc
     * @param block
     * @return Set<Object>
     */
    private Set<Object> makeDomain(int block) {
        Set<Object> domain = new HashSet<>();

        for (int p = 0; p < nbPiles; p++) {
            domain.add(-(p+1));
        }

        for (int b = 0; b < nbBlocks; b++) {
            if (b != block)
                domain.add(b);
        }

        return domain;
    }

    
    /** 
     * Récupérer la variable pour savoir ce sur quoi un block est
     * @param block
     * @return Variable
     */
    public Variable getBlockOn(int block) {
        return onB.get(block);
    }

    
    /** 
     * Récupérer la variable pour savoir si un block est fixé
     * @param block
     * @return BooleanVariable
     */
    public BooleanVariable getBlockFixed(int block) {
        return fixedB.get(block);
    }

    
    /** 
     * Récupérer la variable pour savoir si une pile est libre
     * @param pile
     * @return BooleanVariable
     */
    public BooleanVariable getPileFree(int pile) {
        return freeP.get(pile);
    }

    
    /** 
     * @return Collection<Variable>
     */
    public Collection<Variable> getOnB() {
        return onB.values();
    }

    public Collection<BooleanVariable> getFixedB() {
        return fixedB.values();
    }

    public Collection<BooleanVariable> getFreeP() {
        return freeP.values();
    }

    /**
     * Ensemble complet de toutes les variables du monde
     * @return variables
     */
    public Set<Variable> getAllVariables(){
        return variables;
    }

    

}
