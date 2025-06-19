package blocksworld.demos;

import java.util.HashSet;
import java.util.Set;

import blocksworld.BlocksWorld;
import blocksworld.IncreasingBlocksWorld;
import blocksworld.RegularBlocksWorld;
import modelling.Constraint;

public class IncreasingAndRegularWorldSolverDemo {

    /**
     * Génération d'un monde croissant et régulier
     * @param args
     */
    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 2;

        BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        RegularBlocksWorld regularBlocksWorld = new RegularBlocksWorld(blocksWorld);
        IncreasingBlocksWorld increasingBlocksWorld = new IncreasingBlocksWorld(blocksWorld);

        // Construction des contraintes
        Set<Constraint> constraints = new HashSet<>(blocksWorld.getConstraints());
        constraints.addAll(regularBlocksWorld.getRegularConstraints());
        constraints.addAll(increasingBlocksWorld.getIncreasingConstraints());

        // Execution des solveurs et affichage de leurs solutions
        SolverDemo solverDemo = new SolverDemo(blocksWorld, constraints);
        solverDemo.runAllSolvers("Increasing and Regular");
        
    }


     

}
