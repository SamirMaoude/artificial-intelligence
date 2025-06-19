package blocksworld.demos;

import java.util.HashSet;
import java.util.Set;

import blocksworld.BlocksWorld;
import blocksworld.IncreasingBlocksWorld;
import modelling.Constraint;

public class IncreasingWorldSolverDemo {
    /**
     * Génération d'un monde croissant
     * @param args
     */
    public static void main(String[] args) {
        int nbBlocks = 5;
        int nbPiles = 2;

        BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        IncreasingBlocksWorld increasingBlocksWorld = new IncreasingBlocksWorld(blocksWorld);

        // Construction des contraintes
        Set<Constraint> constraints = new HashSet<>(blocksWorld.getConstraints());
        constraints.addAll(increasingBlocksWorld.getIncreasingConstraints());

        // Execution des solveurs et affichage de leurs solutions
        SolverDemo solverDemo = new SolverDemo(blocksWorld, constraints);
        solverDemo.runAllSolvers("Increasing");

    }

}
