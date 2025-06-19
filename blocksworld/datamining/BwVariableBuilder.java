package blocksworld.datamining;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelling.BooleanVariable;

public class BwVariableBuilder {

    private int nbBlocks;
    private int nbPiles;
    private Set<BooleanVariable> variables = new HashSet<>();

    /**
     * Génération des items relatifs au monde des blocs
     * @param nbBlocks
     * @param nbPiles
     */
    public BwVariableBuilder(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;

        for(int b1 = 0; b1 < nbBlocks; b1++){
            for(int b2 = b1+1; b2 < nbBlocks; b2 ++){
                if(b1 == b2) continue;

                /**
                 * pour chaque couple de blocs différents {b1,b2}, une variable onb1_b2
                 * prenant la valeur true lorsque le bloc b1 est directement sur le bloc b2 (et false sinon);
                 */
                variables.add(new BooleanVariable("on_"+b1+"_"+b2, true));
                variables.add(new BooleanVariable("on_"+b1+"_"+b2, false));
                
                /**
                 * pour chaque couple de blocs différents {b2,b1}, une variable onb2_b1
                 * prenant la valeur true lorsque le bloc b2 est directement sur le bloc b1 (et false sinon);
                 */
                variables.add(new BooleanVariable("on_"+b2+"_"+b1, true));
                variables.add(new BooleanVariable("on_"+b2+"_"+b1, false));
            }

            /**
             * Si b1 est au sommet d'une pile, fixedb = true, false sinon
             */
            variables.add(new BooleanVariable("fixed"+b1, true));
            variables.add(new BooleanVariable("fixed"+b1, false));
        }

        for(int p = 0; p < nbPiles; p++){
            for(int b = 0; b < nbBlocks; b++){
                /**
                 * pour chaque bloc b et pour chaque pile p, une variable onTable_b_p, prenant la valeur true lorsque
                 * le bloc b est sur la table dans la pile p (et false sinon) ;
                 */
                variables.add(new BooleanVariable("onTable_"+b+"_"+p, true));
                variables.add(new BooleanVariable("onTable_"+b+"_"+p, false));
            }

            /**
             * si p est vide, freep = true, false sinon
             */
            variables.add(new BooleanVariable("free"+p, true));
            variables.add(new BooleanVariable("free"+p, false));
        }



    }

    


    public Set<BooleanVariable> getVariables() {
        return variables;
    }



    /**
     * Transforme une configuration en une instance de boolean database
     * @param state
     * @return
     */
    public Set<BooleanVariable> buildInstance(List<List<Integer>> state){
        Set<BooleanVariable> instance = new HashSet<>();

        for(int p = 0; p < state.size(); p++){
            List<Integer> stack = state.get(p);
            int stackSize = stack.size();
            
            if(stackSize==0){
                // pile vide
                instance.add(new BooleanVariable("free"+p, true));
            }
            else{
                // pile non vide
                instance.add(new BooleanVariable("free"+p, false));

                int prev = -1;
                for(int i = 0; i < stackSize; i++){
                    int b = stack.get(i);

                    // sur la table
                    if(i==0){
                        instance.add(new BooleanVariable("onTable_"+b+"_"+p, true));
                    }
                    else{
                        // pas sur la table
                        instance.add(new BooleanVariable("onTable_"+b+"_"+p, false));
                    }

                    // sommet de la pile
                    if(i==stackSize-1){
                        instance.add(new BooleanVariable("fixed"+b, false));
                    }
                    else{
                        // pas sommet
                        instance.add(new BooleanVariable("fixed"+b, true));
                    }

                    if(prev >= 0){
                        instance.add(new BooleanVariable("on_"+b+"_"+prev, true));

                        for(int b2 = 0; b2<this.nbBlocks; b2++){
                            if(b2==b || b2 == prev) continue;
                            instance.add(new BooleanVariable("on_"+b+"_"+b2, false));
                        }
                    }

                    prev = b;
                }
            }
        }

        return instance;
    }

}
