package planning;

import java.util.Map;

import modelling.Variable;

public class GraphHeuristic implements Heuristic {


    @Override
    public float estimate(Map<Variable, Object> state) {
        Variable variable = new Variable("node", null);
        
        Object value = state.getOrDefault(variable, null);

        if(value.equals('a')) return 3;
        if(value.equals('b')) return 2;
        if(value.equals('c')) return 0;
        if(value.equals('d')) return 2;
        if(value.equals('e')) return 4;
        if(value.equals('f')) return 1;

        return 0;
    }

}
