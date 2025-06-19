package blocksworld.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import blocksworld.BlocksWorld;
import blocksworld.BlocksWorldActions;
import blocksworld.BlocksWorldGenerator;
import blocksworld.BlocksWorldVariables;
import blocksworld.heuristics.MisplacedBlocksHeuristic;
import blocksworld.heuristics.SimilarityHeuristic;
import bwmodel.BWState;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import modelling.Variable;
import planning.AStarPlanner;
import planning.Action;
import planning.BFSPlanner;
import planning.Goal;
import planning.Heuristic;
import planning.BasicGoal;
import planning.DFSPlanner;
import planning.DijkstraPlanner;

public class PlannerDemo {

    /**
     * Execution des différents algorithmes de plannification pour trouver un plan,
     * afin de quitter d'un état à un autre
     * @param args
     */
    public static void main(String[] args) {

        int nbBlocks = 4;
        int nbPiles = 4;
        int alpha = -1;
        
        BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        BlocksWorldVariables variables = blocksWorld.getVariables();


        List<Stack<Object>> initStacks = new ArrayList<>();
        List<Stack<Object>> goalStacks = new ArrayList<>();

        for (int p = 0; p < nbPiles; p++) {
            initStacks.add(new Stack<>());
            goalStacks.add(new Stack<>());
        }

        /**
         * Init World : [[0,1,2,3], [], [], []]
         * Goal World : [[], [], [], [3,2,1,0]]
         */

        Stack initStack0 = initStacks.get(0);
        initStack0.addAll(Arrays.asList(0,1,2,3));

        Stack goalStack3 = goalStacks.get(3);
        goalStack3.addAll(Arrays.asList(3,2,1,0));


        Map<Variable, Object> init = BlocksWorldGenerator.getBlockWorld(initStacks, variables);
        Map<Variable, Object> goalState = BlocksWorldGenerator.getBlockWorld(goalStacks, variables);

        System.out.println("Initial state");
        BlocksWorldGenerator.printWorld(init);

        System.out.println("Goal state");
        BlocksWorldGenerator.printWorld(goalState);
        
        BlocksWorldGenerator.displayWorld(init, blocksWorld, "Init State");
        BlocksWorldGenerator.displayWorld(goalState, blocksWorld, "Goal State");


        Goal goal = new BasicGoal(goalState);

        Set<Action> actions = new BlocksWorldActions(nbBlocks, nbPiles).getActions();

        // A* Cosine similarity heuristique
        Heuristic heuristic = new SimilarityHeuristic(goalState, alpha);
        AStarPlanner aStarPlanner = new AStarPlanner(init, actions, goal, heuristic);
        aStarPlanner.activateNodeCount(true);
        List<Action> aStarPlan = aStarPlanner.plan();

        // A* blocs mal placés heuristique
        Heuristic misplacedHeuristic = new MisplacedBlocksHeuristic(goalState);
        AStarPlanner aStarPlanner2 = new AStarPlanner(init, actions, goal, misplacedHeuristic);
        aStarPlanner2.activateNodeCount(true);
        List<Action> aStarPlan2 = aStarPlanner2.plan();

        // Dijkstra planner
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(init, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        List<Action> dijkstraPlan = dijkstraPlanner.plan();

        // DFSPlanner
        DFSPlanner dfsPlanner = new DFSPlanner(init, actions, goal);
        dfsPlanner.activateNodeCount(true);
        List<Action> dfsPlan = dfsPlanner.plan();

        // BFSPlanner
        BFSPlanner bfsPlanner = new BFSPlanner(init, actions, goal);
        bfsPlanner.activateNodeCount(true);
        List<Action> bfsPlan = bfsPlanner.plan();

        // SIMULATION DES PLANS DES DIFFERENTS PLANNERS
        
        // DFS
        System.out.println("DFS: " + dfsPlanner.getVisitedNode() + " nodes visited");
        System.out.println("DFS Execution time: " + TimeUnit.NANOSECONDS.toMillis(dfsPlanner.getExecutionTime()) + " milliseconds.");
        simulation(init, dfsPlan, blocksWorld, "DFS");

        // BFS
        System.out.println("BFS: " + bfsPlanner.getVisitedNode() + " nodes visited");
        System.out.println("BFS Execution time: " + TimeUnit.NANOSECONDS.toMillis(bfsPlanner.getExecutionTime()) + " milliseconds.");
        simulation(init, bfsPlan, blocksWorld, "BFS");

        // Dijkstra
        System.out.println("Dijkstra: " + dijkstraPlanner.getVisitedNode() + " nodes visited");
        System.out.println("Dijkstra Execution time: " + TimeUnit.NANOSECONDS.toMillis(dijkstraPlanner.getExecutionTime()) + " milliseconds.");
        simulation(init, dijkstraPlan, blocksWorld, "Dijkstra");

        // A* cosine similarity
        System.out.println("A* with cosine similarity heuristic: " + aStarPlanner.getVisitedNode() + " nodes visited");
        System.out.println("A* with cosine similarity heuristic Execution time: " + TimeUnit.NANOSECONDS.toMillis(aStarPlanner.getExecutionTime()) + " milliseconds.");
        simulation(init, aStarPlan, blocksWorld, "A* with cosine similarity heuristic");

        // A* Misplaced blocks
        System.out.println("A* with misplaced blocks heuristic: " + aStarPlanner2.getVisitedNode() + " nodes visited");
        System.out.println("A* with misplaced blocks heuristic Execution time: " + TimeUnit.NANOSECONDS.toMillis(aStarPlanner2.getExecutionTime()) + " milliseconds.");
        simulation(init, aStarPlan2, blocksWorld, "A* with misplaced blocks heuristic");

    }


    public static void displayPlan(List<Action> plan){

        System.out.println();
        int cost = 0;
        for(Action action: plan){
            System.out.println(action);
            cost += action.getCost();
        }

        System.out.println("Coût total: " + cost);
        System.out.println(plan.size()+" actions à effectuer");
        
    }

    public static void simulation(Map<Variable, Object> worldInit, List<Action> plan, BlocksWorld blocksWorld, String name){

        Map<Variable, Object> init = new HashMap<>(worldInit);
        
        if(plan!=null){

            Map<Variable, Object> state = new HashMap<Variable, Object>(init);
            BWIntegerGUI gui = new BWIntegerGUI(blocksWorld.getNbBlocks());
            JFrame frame = new JFrame(name+" simulation");
            BWState<Integer> bwState = BlocksWorldGenerator.makeBWState(init, blocksWorld);
            BWComponent<Integer> component = gui.getComponent(bwState);
            frame.add(component);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            frame.setVisible(true);

            
            int nbAction = plan.size();

            int speed = 4_000;

            if(nbAction>20){
                speed = 100;
            }
            else if(nbAction>10){
                speed = 3_000;
            }

            // Playing plan
            for (Action a: plan) {
                try { Thread.sleep(speed); }
                catch (InterruptedException e) { e.printStackTrace(); }
                state=a.successor(state);
                component.setState(BlocksWorldGenerator.makeBWState(state, blocksWorld));

                // System.out.println(a);
            }
            System.out.println(nbAction+" actions executed.");
            System.out.println("Simulation of "+name+" plan: done.\n");
        }
        else{
            System.out.println("No plan");
        }
    }

    

}
