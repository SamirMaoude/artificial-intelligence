package blocksworld;

import java.util.Set;
import java.util.HashSet;
import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

public class IncreasingBlocksWorld {

    private BlocksWorld blocksWorld;
    private Set<Constraint> constraints = new HashSet<>();

    /**
     * Classe générant les contraintes d'un monde croissant
     * @param blocksWorld
     */
    public IncreasingBlocksWorld(BlocksWorld blocksWorld){

        this.blocksWorld = blocksWorld;

        buildConstraints();
        

    }

    private void buildConstraints(){

        int nbBlocks = this.blocksWorld.getNbBlocks();
        BlocksWorldVariables blocksWorldVariables = this.blocksWorld.getVariables();

        for(int b1 = 0; b1 < nbBlocks; b1++){

            /**
             * Pour tous les blocs, il faut retirer de leurs domaines,
             * les blocs qui leurs sont supérieurs
             *
            */

            Variable onB1 = blocksWorldVariables.getBlockOn(b1);
            Set<Object> b1Domain = new HashSet<>(onB1.getDomain());

            for(Object v: new HashSet<>(b1Domain)){
                if((int)v>b1)b1Domain.remove(v);
            }

            for(int b2 = b1+1; b2 < nbBlocks; b2++){

                Variable onB2 = blocksWorldVariables.getBlockOn(b2);
                Set<Object> b2Domain = new HashSet<>(onB2.getDomain());

                for(Object v: new HashSet<>(b2Domain)){
                    if((int)v>b2)b2Domain.remove(v);
                }
  

                constraints.add(new Implication(onB1, b1Domain, onB2, b2Domain));
                constraints.add(new Implication(onB2, b2Domain, onB1, b1Domain));

            }
        }


    }

    /** 
     * Retourne les contraintes d'un monde de blocks croissant
     * @return Set<Constraint>
     */
    public Set<Constraint> getIncreasingConstraints(){
        return constraints;
    }

}
