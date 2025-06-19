package blocksworld.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import blocksworld.BlocksWorld;
import blocksworld.BlocksWorldGenerator;
import blocksworld.BlocksWorldVariables;
import blocksworld.IncreasingBlocksWorld;
import blocksworld.RegularBlocksWorld;
import modelling.Variable;

public class ConstraintsDemo {
    public static void main(String[] args) {


        // Initialisation
        int nbBlocks = 10;
        int nbPiles = 4;

        BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        BlocksWorldVariables blocksWorldVariables = blocksWorld.getVariables();
        RegularBlocksWorld regularBlocksWorld = new RegularBlocksWorld(blocksWorld);
        IncreasingBlocksWorld increasingBlocksWorld = new IncreasingBlocksWorld(blocksWorld);

        List<Stack<Object>> worldStacks = new ArrayList<>();

        for (int p = 0; p < nbPiles; p++) {
            worldStacks.add(new Stack<>());
        }

        /**
         * World : [[1,3,5,7], [8,4,0], [2,9], [6]] (Régulier)
         */

        Stack worldStack0 = worldStacks.get(0);
        worldStack0.addAll(Arrays.asList(1, 3, 5, 7));

        Stack worldStack1 = worldStacks.get(1);
        worldStack1.addAll(Arrays.asList(8, 4, 0));

        Stack worldStack2 = worldStacks.get(2);
        worldStack2.addAll(Arrays.asList(2, 9));

        Stack worldStack3 = worldStacks.get(3);
        worldStack3.addAll(Arrays.asList(6));

        Map<Variable, Object> world = BlocksWorldGenerator.getBlockWorld(worldStacks, blocksWorldVariables);

        BlocksWorldGenerator.displayWorldFeatures(world, blocksWorld.getConstraints(), regularBlocksWorld.getRegularConstraints(),
                increasingBlocksWorld.getIncreasingConstraints());

        /**
         * incWorld : [[1,3,5,7], [0,4,8], [2,9], [6]] (Régulier et croissant)
         */

        List<Stack<Object>> incWorldStacks = new ArrayList<>();

        for (int p = 0; p < nbPiles; p++) {
            incWorldStacks.add(new Stack<>());
        }

        Stack incWorldStack0 = incWorldStacks.get(0);
        incWorldStack0.addAll(Arrays.asList(1, 3, 5, 7));

        Stack incWorldStack1 = incWorldStacks.get(1);
        incWorldStack1.addAll(Arrays.asList(0, 4, 8));

        Stack incWorldStack2 = incWorldStacks.get(2);
        incWorldStack2.addAll(Arrays.asList(2, 9));

        Stack incWorldStack3 = incWorldStacks.get(3);
        incWorldStack3.addAll(Arrays.asList(6));

        Map<Variable, Object> incWorld = BlocksWorldGenerator.getBlockWorld(incWorldStacks, blocksWorldVariables);

        BlocksWorldGenerator.displayWorldFeatures(incWorld, blocksWorld.getConstraints(), regularBlocksWorld.getRegularConstraints(),
                increasingBlocksWorld.getIncreasingConstraints());



        /**
         * badWorld : [[1,3,7,5], [0,4,8], [2,9], [6]] (Ni régulier, ni croissant)
         */

        List<Stack<Object>> badWorldStacks = new ArrayList<>();

        for (int p = 0; p < nbPiles; p++) {
            badWorldStacks.add(new Stack<>());
        }

        Stack badWorldStack0 = badWorldStacks.get(0);
        badWorldStack0.addAll(Arrays.asList(1, 3, 7, 5));

        Stack badWorldStack1 = badWorldStacks.get(1);
        badWorldStack1.addAll(Arrays.asList(0, 4, 8));

        Stack badWorldStack2 = badWorldStacks.get(2);
        badWorldStack2.addAll(Arrays.asList(2, 9));

        Stack badWorldStack3 = badWorldStacks.get(3);
        badWorldStack3.addAll(Arrays.asList(6));

        Map<Variable, Object> badWorld = BlocksWorldGenerator.getBlockWorld(badWorldStacks, blocksWorldVariables);

        BlocksWorldGenerator.displayWorldFeatures(badWorld, blocksWorld.getConstraints(), regularBlocksWorld.getRegularConstraints(),
                increasingBlocksWorld.getIncreasingConstraints());
        

    }

    

    

}
