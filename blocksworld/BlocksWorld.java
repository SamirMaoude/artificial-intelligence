package blocksworld;

import java.util.Set;
import java.util.HashSet;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

public class BlocksWorld {

    protected int nbBlocks;
    protected int nbPiles;
    protected Set<Constraint> constraints = new HashSet<>();
    protected BlocksWorldVariables blocksWorldVariables;
    
    /**
     * Classe générant toutes les contraintes spécifiques à un monde de blocs valide
     * @param nbBlocks
     * @param nbPiles
     */
    public BlocksWorld(int nbBlocks, int nbPiles){

        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.blocksWorldVariables = new BlocksWorldVariables(this.nbBlocks, this.nbPiles);

        
        
        for(int b1 = 0; b1 < nbBlocks; b1++){
            for(int b2 = 0; b2 < nbBlocks; b2++){
                if(b1 == b2) continue;

                if(b2 > b1){
                    /**
                     * pour chaque couple de blocs différents {b1,b2}, les variables onb1 et onb2 ne peuvent pas prendre la
                     * même valeur (cela signifierait que les deux blocs sont posés sur un même troisième bloc, ou qu’ils
                     * sont sur la table dans la même pile);
                     */
                    constraints.add(new DifferenceConstraint(blocksWorldVariables.getBlockOn(b1), blocksWorldVariables.getBlockOn(b2)));

                    /**
                     * pour chaque couple de blocs différents {b1,b2} onb1 = b2 => onb2 != b1
                     */

                    Variable onb1 = blocksWorldVariables.getBlockOn(b1);
                    Set<Object> b1Domain = new HashSet<>();
                    b1Domain.add(b2);

                    Variable onb2 = blocksWorldVariables.getBlockOn(b2);
                    Set<Object> b2Domain = new HashSet<>(onb2.getDomain());
                    b2Domain.remove(b1);

                    constraints.add(new Implication(onb1, b1Domain, onb2, b2Domain));


                    /**
                     * pour chaque couple de blocs différents {b1,b2} onb2 = b1 => onb1 != b2
                     */

                    Set<Object> b2Domain1 = new HashSet<>();
                    b2Domain1.add(b1);
 
                    Set<Object> b1Domain1 = new HashSet<>(onb1.getDomain());
                    b1Domain1.remove(b2);
                    constraints.add(new Implication(onb2, b2Domain1, onb1, b1Domain1));

                }

                /**
                 * pour chaque couple de blocs {b1,b2}, si la variable onb1 a la valeur b2, alors la variable fixedb2 doit
                 * avoir la valeur true (car s’il y a un bloc juste au-dessus de b2, celui-ci est indéplaçable);
                */

                Set<Object> b1Domain = new HashSet<>();
                b1Domain.add(b2);

                Set<Object> b2Domain = new HashSet<>();
                b2Domain.add(true);

                constraints.add(new Implication(blocksWorldVariables.getBlockOn(b1), b1Domain, blocksWorldVariables.getBlockFixed(b2), b2Domain));

                
            }

        }

        for(int b = 0; b < nbBlocks; b++){
            for(int p = 0; p < nbPiles; p++){
                /**
                 * pour chaque bloc b et pour chaque pile p, si la variable onb a la valeur −(p + 1) (c’est-à-dire la
                 * valeur représentant la pile p), alors la variable freep doit avoir la valeur false (car s’il y a un bloc
                 * dans p, celle-ci n’est pas libre)
                 */

                Set<Object> pDomain = new HashSet<>();
                pDomain.add(false);
                Set<Object> bDomain = new HashSet<>();
                bDomain.add(-(p+1));

                constraints.add(new Implication(blocksWorldVariables.getBlockOn(b), bDomain, blocksWorldVariables.getPileFree(p), pDomain));
            }
        }

        

    }

    
    /** 
     * Retourne les contraintes relatives au monde des blocs
     * @return Set<Constraint>
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public BlocksWorldVariables getVariables() {
        return blocksWorldVariables;
    }

    public int getNbBlocks() {
        return nbBlocks;
    }

    public int getNbPiles() {
        return nbPiles;
    }

    
    
}
