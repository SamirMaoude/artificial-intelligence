package blocksworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import modelling.Constraint;
import modelling.Variable;

public class BlocksWorldGenerator {


    
    /** 
     * Générer un monde de blocks à partir d'une liste de piles
     * @param stacks
     * @param blocksWorldVariables
     * @return Map<Variable, Object> (le monde)
     */
    public static Map<Variable, Object> getBlockWorld(List<Stack<Object>> stacks, BlocksWorldVariables blocksWorldVariables){
        Map<Variable, Object> world = new HashMap<>();

        for(int p = 0; p < stacks.size(); p++){
            Stack<Object> stack = stacks.get(p);

            if(!stack.empty()){
                Object prev = null;

                while(!stack.empty()){
                    Object current = stack.pop();
                    if(prev != null){
                        world.put(blocksWorldVariables.getBlockOn((int)prev), (int)current);
                        world.put(blocksWorldVariables.getBlockFixed((int)current), true);
                    }
                    else{
                        world.put(blocksWorldVariables.getBlockFixed((int)current), false);
                    }
                    prev = current;
                }

                world.put(blocksWorldVariables.getBlockOn((int)prev), -(p+1));

                    
                world.put(blocksWorldVariables.getPileFree(p), false);
                

            }
            else{
                world.put(blocksWorldVariables.getPileFree(p), true);
            }
        }

        return world;
    }

    /**
     * Générer une configuration au hasard pour un monde de blocks donné
     * @param blocksWorld
     * @return
     */
    public static Map<Variable, Object> randomWorld(BlocksWorld blocksWorld){
        List<Stack<Object>> stacks = new LinkedList<>();

        List<Integer> blocks = new ArrayList<>();

        for(int b = 0; b < blocksWorld.getNbBlocks(); b++){
            blocks.add(b);
        }

        for(int p = 0; p < blocksWorld.getNbPiles(); p++){
            stacks.add(new Stack<>());
        }

        int nbPiles = blocksWorld.getNbPiles();
        while(!blocks.isEmpty()){

            Random random = new Random();

            int i = random.nextInt(blocks.size());

            int b = blocks.remove(i).intValue();

            int p = random.nextInt(nbPiles);

            stacks.get(p).add(b);
        }

        return getBlockWorld(stacks, blocksWorld.getVariables());
    }

    public static void printWorld(Map<Variable, Object> world){
        System.out.println("======================================================");
        for(Variable v: world.keySet()){
            System.out.println(v.getName()+"  ========>  "+world.get(v));
        }
        System.out.println("======================================================\n\n");

    }
    
    public static BWState<Integer> makeBWState(Map<Variable, Object> world, BlocksWorld blocksWorld){

        BlocksWorldVariables variables = blocksWorld.getVariables();
        int n = blocksWorld.getNbBlocks();

        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = variables.getBlockOn(b); // get instance of Variable for "on_b"
            int under = (int) world.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        BWState<Integer> state = builder.getState();

        return state;
    }

    public static void displayWorldFeatures(Map<Variable, Object> world, Set<Constraint> worldConstraints,
            Set<Constraint> regularConstraints, Set<Constraint> increasingConstraints) {

        System.out.println("Représentation du monde:");
        BlocksWorldGenerator.printWorld(world);
        
        if(worldConstraints!=null || increasingConstraints != null || regularConstraints != null){
            System.out.println("****************************************************");
            if(worldConstraints!=null)System.out.println(isViable(world, worldConstraints) ? "Ce monde est valide" : "Ce monde est invalide");
            if(regularConstraints!=null)System.out.println(isViable(world, regularConstraints) ? "Ce monde est régulier" : "Ce monde n'est pas régulier");
            if(increasingConstraints!=null)System.out.println(isViable(world, increasingConstraints) ? "Ce monde est croissant" : "Ce monde n'est pas croissant");
            System.out.println("****************************************************");
        }
        
    }

    public static boolean isViable(Map<Variable, Object> world, Set<Constraint> constraints) {

        for (Constraint constraint : constraints) {
            if (!constraint.isSatisfiedBy(world)) {
                System.out.println("Cette contrainte " + constraint + " est insatisfaite");
                return false;
            }
        }

        return true;
    }

    public static void displayWorld(Map<Variable, Object> world, BlocksWorld blocksWorld, String title){
       
        BWState<Integer> state = BlocksWorldGenerator.makeBWState(world, blocksWorld);
        // Displaying
        BWIntegerGUI gui = new BWIntegerGUI(blocksWorld.getNbBlocks());
        JFrame frame = new JFrame(title);
        frame.add(gui.getComponent(state));
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
    }

}
