package planning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class Demo {

    public static void main(String args[]) {

        /*
         * 
         * Considérez le problème suivant
         * soit des états décrits par un ensemble V de 4 variables booléennes : a, b, c, d
         * soit {!a, !b, !c, !d} l’état initial
         * soit {a, b, c, d} l’état but
         * soit l’heuristique h qui associe à chaque état le nombre de variables à faux
        
         * Actions
         * ∀v ∈ V : Sv : (PRE = T, EFF = {v}) coûtant 2
         * D1 : (PRE = T, EFF = {a, b}) coûtant 3
         * D2 : (PRE = T, EFF = {c, d}) coûtant 3
         * T1 : (PRE = T, EFF = {a, b, c}) coûtant 8
         * T2 : (PRE = T, EFF = {b, c, d}) coûtant 8
         */


        Map<Variable, Object> state = new HashMap<>();
        Set<Object> domain = new HashSet<Object>();
        domain.add(true);
        domain.add(false);


        // Initial state
        Variable a = new Variable("a", domain);
        Variable b = new Variable("b", domain);
        Variable c = new Variable("c", domain);
        Variable d = new Variable("d", domain);
        state.put(a, false);
        state.put(b, false);
        state.put(c, false);
        state.put(d, false);


        // Goal State
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(a, true);
        goalState.put(b, true);
        goalState.put(c, true);
        goalState.put(d, true);
        Goal goal = new BasicGoal(goalState);
        


        // Actions
        Set<Action> actions = new LinkedHashSet<>();
        Map<Variable, Object> effectA = new HashMap<>();
        effectA.put(a, true);
        Action sA= new BasicAction(new HashMap<>(), effectA, 2);
        actions.add(sA);

        Map<Variable, Object> effectB = new HashMap<>();
        effectB.put(b, true);
        Action sB= new BasicAction(new HashMap<>(), effectB, 2);
        actions.add(sB);

        Map<Variable, Object> effectC = new HashMap<>();
        effectC.put(c, true);
        Action sC= new BasicAction(new HashMap<>(), effectC, 2);
        actions.add(sC);

        Map<Variable, Object> effectD = new HashMap<>();
        effectD.put(d, true);
        Action sD= new BasicAction(new HashMap<>(), effectD, 2);
        actions.add(sD);

        Map<Variable, Object> effectD1 = new HashMap<>();
        effectD1.put(a, true);
        effectD1.put(b, true);
        Action D1= new BasicAction(new HashMap<>(), effectD1, 3);
        actions.add(D1);


        Map<Variable, Object> effectD2 = new HashMap<>();
        effectD2.put(c, true);
        effectD2.put(d, true);
        Action D2= new BasicAction(new HashMap<>(), effectD2, 3);
        actions.add(D2);

        Map<Variable, Object> effectT1 = new HashMap<>();
        effectT1.put(a, true);
        effectT1.put(b, true);
        effectT1.put(c, true);
        Action T1= new BasicAction(new HashMap<>(), effectT1, 8);
        actions.add(T1);

        Map<Variable, Object> effectT2 = new HashMap<>();
        effectT2.put(b, true);
        effectT2.put(c, true);
        effectT2.put(d, true);
        Action T2= new BasicAction(new HashMap<>(), effectT2, 8);
        actions.add(T2);
        



        // DFS PLANNER
        DFSPlanner dfsPlanner = new DFSPlanner(state, actions, goal);
        dfsPlanner.activateNodeCount(true);
        List<Action> plan = dfsPlanner.plan();
        displayPlan(plan);
        System.out.println("DFS: " + dfsPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");


        // BFS PLANNER
        BFSPlanner bfsPlanner = new BFSPlanner(state, actions, goal);
        bfsPlanner.activateNodeCount(true);
        plan = bfsPlanner.plan();
        displayPlan(plan);
        System.out.println("BFS: " + bfsPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");

        // Dijkstra PLANNER
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(state, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        plan = dijkstraPlanner.plan();
        displayPlan(plan);
        System.out.println("Dijkstra: " + dijkstraPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");

        // A* PLANNER
        AStarPlanner aStarPlanner = new AStarPlanner(state, actions, goal, new DemoHeuristic());
        aStarPlanner.activateNodeCount(true);
        plan = aStarPlanner.plan();
        displayPlan(plan);
        System.out.println("A*: " + aStarPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");



        /*
         * Nous allons modeliser Un graphe
         * 
         * Soit [(x, y)] = {(u, t),........} représente la liste d'adjacence du sommet x avec y l'heuristique, u le voisin et t le cout
         * On a : 
         * 
         * [(a,3)] = {(b, 2), (d, 5), (e, 4), (f, 5)}
         * [(b,2)] = {(a, 2), (c, 9), (f, 5)}
         * [(c, 0)] = {(b, 9), (f, 4), (d, 2)}
         * [(d, 2)] = {(a, 5), (c, 2), (e, 4), (f, 4)}
         * [(e, 4)] = {(a, 4), (d, 4)}
         * [(f, 1)] = {(a, 5), (b, 5), (c, 4), (d, 4)}
         * 
         * Trouvons le plan menant de a à c !
        */


        System.out.println("\n\n\n");
        System.out.println("*********** GRAPHE ************");


        Map<Variable, Object> initialState = new HashMap<>();
        domain = new HashSet<Object>();
        domain.add('a');
        domain.add('b');
        domain.add('c');
        domain.add('d');
        domain.add('e');
        domain.add('f');


        // Initial state
        Variable start = new Variable("node", domain);
        initialState.put(start, 'a');
        actions = new LinkedHashSet<>();


        /*
         * [(a,3)] = {(b, 2), (d, 5), (e, 4), (f, 5)}
         */
        Map<Variable, Object> precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'a');

        // A---->B
        Map<Variable, Object> effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'b');
        Action action = new BasicAction(precondition, effect, 2);
        actions.add(action);

        // A---->D
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'd');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);

        // A---->E
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'e');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        // A---->F
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'f');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);


        /*
         * [(b,2)] = {(a, 2), (c, 9), (f, 5)}
         */

        // B---->A
        precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'b');
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'a');
        action = new BasicAction(precondition, effect, 2);
        actions.add(action);

        // B---->C
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'c');
        action = new BasicAction(precondition, effect, 9);
        actions.add(action);

        // B---->F
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'f');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);


        /*
         * [(c, 0)] = {(b, 9), (f, 4), (d, 2)}
        */

        // C---->B
        precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'c');
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'b');
        action = new BasicAction(precondition, effect, 9);
        actions.add(action);

        // C---->D
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'd');
        action = new BasicAction(precondition, effect, 2);
        actions.add(action);

        // C---->F
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'f');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);


        /* 
         * [(d, 2)] = {(a, 5), (c, 2), (e, 4), (f, 4)}
        */

        //  D---->A
        precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'd');
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'a');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);

        //  D---->C
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'c');
        action = new BasicAction(precondition, effect, 2);
        actions.add(action);

        //  D---->E
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'e');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        //  D---->F
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'f');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);


        /*
         * [(e, 4)] = {(a, 4), (d, 4)}
        */

        //  E---->A
        precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'e');
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'a');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        //  E---->D
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'd');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        /*
         * [(f, 1)] = {(a, 5), (b, 5), (c, 4), (d, 4)}
        */

        //  F---->A
        precondition = new HashMap<>();
        precondition.put(new Variable("node", domain), 'f');
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'a');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);

        //  F---->B
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'b');
        action = new BasicAction(precondition, effect, 5);
        actions.add(action);

        //  F---->C
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'c');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        //  F---->D
        effect = new HashMap<>();
        effect.put(new Variable("node", domain), 'd');
        action = new BasicAction(precondition, effect, 4);
        actions.add(action);

        // Goal
        goalState = new HashMap<>();
        goalState.put(new Variable("node", domain), 'c');
        goal = new BasicGoal(goalState);

        // DFS PLANNER
        dfsPlanner = new DFSPlanner(initialState, actions, goal);
        dfsPlanner.activateNodeCount(true);
        plan = dfsPlanner.plan();
        displayPlan(plan);
        System.out.println("DFS: " + dfsPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");


        // BFS PLANNER
        bfsPlanner = new BFSPlanner(initialState, actions, goal);
        bfsPlanner.activateNodeCount(true);
        plan = bfsPlanner.plan();
        displayPlan(plan);
        System.out.println("BFS: " + bfsPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");

        // Dijkstra PLANNER
        dijkstraPlanner = new DijkstraPlanner(initialState, actions, goal);
        dijkstraPlanner.activateNodeCount(true);
        plan = dijkstraPlanner.plan();
        displayPlan(plan);
        System.out.println("Dijkstra: " + dijkstraPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");

        // A* PLANNER
        aStarPlanner = new AStarPlanner(initialState, actions, goal, new GraphHeuristic());
        aStarPlanner.activateNodeCount(true);
        plan = aStarPlanner.plan();
        displayPlan(plan);
        System.out.println("A*: " + aStarPlanner.getVisitedNode() + " noeuds visités");
        System.out.println("******************************************");


        
    }

    public static void displayPlan(List<Action> plan){

        System.out.println();
        int cost = 0;
        for(Action action: plan){
            System.out.println(action + "------> Coût: " + action.getCost());
            cost += action.getCost();
        }

        System.out.println("Coût total: " + cost);
        System.out.println(plan.size()+" actions à effectuer");
        
    }

}
