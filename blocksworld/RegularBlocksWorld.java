package blocksworld;

import java.util.Set;
import java.util.HashSet;
import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

public class RegularBlocksWorld {

    private BlocksWorld blocksWorld;
    private Set<Constraint> constraints = new HashSet<>();

    /**
     * Classe générant les contraintes d'un monde régulier
     * @param blocksWorld
     */
    public RegularBlocksWorld(BlocksWorld blocksWorld){

        this.blocksWorld = blocksWorld;

        buildConstraints();

    }

    private void buildConstraints(){

        int nbBlocks = this.blocksWorld.getNbBlocks();
        BlocksWorldVariables blocksWorldVariables = this.blocksWorld.getVariables();

        for(int b1 = 0; b1 < nbBlocks; b1++){
            for(int b2 = 0; b2 < nbBlocks; b2++){

                if(b1==b2) continue;

                int gap = b1 - b2;
                
                
                /**
                 * onB1 = b2 => OnB3 = b1 si b3 = b1 + gap
                 * Il va falloir donc retirer du domaine de b3,
                 * b2 et tous les blocs en dessous de b2 dans la pile
                 * 
                 * Pour tous les autres blocs hors de la pile, il va falloir retirer b1 de leur domaine
                */

                
                for(int b3=0; b3 < nbBlocks; b3++){
                    if(b3 == b1 || b3 == b2) continue;

                    Variable onB3 = blocksWorldVariables.getBlockOn(b3);
                    Set<Object> b3Domain = new HashSet<>(onB3.getDomain());

                    if(b3 == b1 + gap){
                        int v = b2;

                        // S'assurer que Onb3 ne prenne jamais la valeur d'un des blocs de la pile courante
                        while(v>=0 && v<nbBlocks){
                            b3Domain.remove(v);
                            v -= gap;
                        }
                    }
                    else{
                        b3Domain.remove(b1);
                    }


                    

                    Set<Object> b1Domain = new HashSet<>();
                    b1Domain.add(b2);

                    constraints.add(new Implication(blocksWorldVariables.getBlockOn(b1), b1Domain, onB3, b3Domain));
                }
                
                
                
            }
        }


    }

    
    /** 
     * Retourne les contraintes d'un monde de blocks régulier
     * @return Set<Constraint>
     */
    public Set<Constraint> getRegularConstraints(){
        return constraints;
    }


}
