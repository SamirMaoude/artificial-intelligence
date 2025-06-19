package blocksworld.demos;

import java.util.HashSet;
import java.util.Set;

import blocksworld.BlocksWorld;
import blocksworld.RegularBlocksWorld;
import modelling.Constraint;

public class RegularWorldSolverDemo {

    /**
     * Génération d'un monde régulier
     * @param args
     */
    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 2;

        BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        RegularBlocksWorld regularBlocksWorld = new RegularBlocksWorld(blocksWorld);

        // Construction des contraintes
        Set<Constraint> constraints = new HashSet<>(blocksWorld.getConstraints());
        constraints.addAll(regularBlocksWorld.getRegularConstraints());

        // Execution des solveurs et affichage de leurs solutions
        SolverDemo solverDemo = new SolverDemo(blocksWorld, constraints);
        solverDemo.runAllSolvers("Regular");
        

    }

}
