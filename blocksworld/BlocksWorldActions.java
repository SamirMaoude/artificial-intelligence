package blocksworld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Variable;
import planning.Action;
import planning.BasicAction;

public class BlocksWorldActions {

    private int nbBlocks;
    private int nbPiles;

    Set<Action> actions = new HashSet<>();

    /**
     * Génère toutes les actions possibles pour un monde de blocs donné
     * @param nbBlocks
     * @param nbPiles
     */
    public BlocksWorldActions(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;

        BlocksWorldVariables variables = new BlocksWorldVariables(nbBlocks, nbPiles);


        /**
         * déplacer un bloc b1 du dessus d’un bloc b2 vers le dessus d’un bloc b3
         */
        for(int b1 = 0; b1 < nbBlocks; b1++){

            for(int b2 = 0; b2 < nbBlocks; b2++){
                
                if(b1 == b2) continue;

                for(int b3 = 0; b3 < nbBlocks; b3++){
                    if(b1==b3 || b2 == b3) continue;

                    Map<Variable, Object> precondition = new HashMap<>();
                    precondition.put(variables.getBlockOn(b1), b2);
                    precondition.put(variables.getBlockFixed(b1), false);
                    precondition.put(variables.getBlockFixed(b2), true);
                    precondition.put(variables.getBlockFixed(b3), false);
                    


                    Map<Variable, Object> effect = new HashMap<>();
                    effect.put(variables.getBlockOn(b1), b3);
                    effect.put(variables.getBlockFixed(b1), false);
                    effect.put(variables.getBlockFixed(b2), false);
                    effect.put(variables.getBlockFixed(b3), true);

                    actions.add(new BasicAction(precondition, effect, 1));


                }

            }
        }

        /**
         * déplacer un bloc b1 du dessus d’un bloc b2 vers une pile vide p,
         */
        for(int b1 = 0; b1 < nbBlocks; b1++){

            for(int b2 = 0; b2 < nbBlocks; b2++){
                
                if(b1 == b2) continue;

                for(int p = 0; p < nbPiles; p++){
                    Map<Variable, Object> precondition = new HashMap<>();
                    precondition.put(variables.getBlockOn(b1), b2);
                    precondition.put(variables.getBlockFixed(b1), false);
                    precondition.put(variables.getBlockFixed(b2), true);
                    precondition.put(variables.getPileFree(p), true);
                    


                    Map<Variable, Object> effect = new HashMap<>();
                    effect.put(variables.getBlockOn(b1), -(p+1));
                    effect.put(variables.getBlockFixed(b1), false);
                    effect.put(variables.getBlockFixed(b2), false);
                    effect.put(variables.getPileFree(p), false);

                    actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }

        /**
         * déplacer un bloc b1 du dessous d’une pile p vers le dessus d’un bloc b2
         */
        for(int b1 = 0; b1 < nbBlocks; b1++){
            for(int p = 0; p < nbPiles; p++){
                for(int b2 = 0; b2 < nbBlocks; b2++){
                    if(b1==b2) continue;

                    Map<Variable, Object> precondition = new HashMap<>();
                    precondition.put(variables.getBlockOn(b1), -(p+1));
                    precondition.put(variables.getBlockFixed(b1), false);
                    precondition.put(variables.getBlockFixed(b2), false);
                    precondition.put(variables.getPileFree(p), false);
                    


                    Map<Variable, Object> effect = new HashMap<>();
                    effect.put(variables.getBlockOn(b1), b2);
                    effect.put(variables.getBlockFixed(b1), false);
                    effect.put(variables.getBlockFixed(b2), true);
                    effect.put(variables.getPileFree(p), true);

                    actions.add(new BasicAction(precondition, effect, 1));


                }
            }
        }


        /**
         * déplacer un bloc b du dessous d’une pile p1 vers une pile vide p2
         */
        for(int b = 0; b < nbBlocks; b++){
            for(int p1 = 0; p1 < nbPiles; p1++){
                for(int p2 = 0; p2 < nbPiles; p2++){
                    if(p1==p2) continue;

                    Map<Variable, Object> precondition = new HashMap<>();
                    precondition.put(variables.getBlockOn(b), -(p1+1));
                    precondition.put(variables.getBlockFixed(b), false);
                    precondition.put(variables.getPileFree(p1), false);
                    precondition.put(variables.getPileFree(p2), true);
                    


                    Map<Variable, Object> effect = new HashMap<>();
                    effect.put(variables.getBlockOn(b), -(p2+1));
                    effect.put(variables.getBlockFixed(b), false);
                    effect.put(variables.getPileFree(p1), true);
                    effect.put(variables.getPileFree(p2), false);

                    actions.add(new BasicAction(precondition, effect, 1));

                }
            }
        }
    }

    public Set<Action> getActions() {
        return actions;
    }

    


}
