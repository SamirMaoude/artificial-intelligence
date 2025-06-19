package blocksworld.demos;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import blocksworld.BlocksWorld;
import blocksworld.BlocksWorldGenerator;
import blocksworld.BlocksWorldVariables;
import cp.BacktrackSolver;
import cp.DomainSizeVariableHeuristic;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.RandomValueHeuristic;
import cp.ValueHeuristic;
import cp.VariableHeuristic;
import modelling.Constraint;
import modelling.Variable;

public class SolverDemo {

    private Set<Constraint> constraints;
    private BlocksWorld blocksWorld;
    private BlocksWorldVariables blocksWorldVariables;

    public SolverDemo(BlocksWorld blocksworld, Set<Constraint> constraints){
        this.blocksWorld = blocksworld;
        this.blocksWorldVariables = blocksWorld.getVariables();
        this.constraints = constraints;
    }

    public void runAllSolvers(String feature){
        // BacktrackSolver
        BacktrackSolver backtrackSolver = new BacktrackSolver(blocksWorldVariables.getAllVariables(), constraints);
        Map<Variable, Object> backtrackSolverSolution = backtrackSolver.solve();
        System.out.println("BacktrackSolver Execution time: " + TimeUnit.NANOSECONDS.toMillis(backtrackSolver.getExecutionTime()) + " milliseconds.");
        displaySolverSolution(backtrackSolverSolution, blocksWorld, "BacktrackSolver "+feature);

        // MACSolver
        MACSolver macSolver = new MACSolver(blocksWorldVariables.getAllVariables(), constraints);
        Map<Variable, Object> macSolverSolution = macSolver.solve();
        System.out.println("MACSolver Execution time: " + TimeUnit.NANOSECONDS.toMillis(macSolver.getExecutionTime()) + " milliseconds.");
        displaySolverSolution(macSolverSolution, blocksWorld, "MACSolver "+ feature);

        // MeuristicMACSolver
        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(true);
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        HeuristicMACSolver heuristicMACSolver = new HeuristicMACSolver(blocksWorldVariables.getAllVariables(), constraints, varHeuristic, valHeuristic);
        Map<Variable, Object> heuristicMACSolverSolution = heuristicMACSolver.solve();
        System.out.println("HeuristicMACSolver Execution time: " + TimeUnit.NANOSECONDS.toMillis(heuristicMACSolver.getExecutionTime()) + " milliseconds.");
        displaySolverSolution(heuristicMACSolverSolution, blocksWorld, "HeuristicMACSolver "+feature);
    }


    private static void displaySolverSolution(Map<Variable, Object> solution, BlocksWorld blocksWorld, String Solver){
        if(solution==null){
            System.out.println(Solver+" n'a trouvé aucune solution viable");
            return;
        }
        System.out.println("Solution de "+Solver);
        BlocksWorldGenerator.displayWorldFeatures(solution, null, null, null);
        BlocksWorldGenerator.displayWorld(solution, blocksWorld, Solver+" solution");
    }

}
