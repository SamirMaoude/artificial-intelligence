package planning;

import java.util.Map;

import modelling.Variable;

public class DemoHeuristic implements Heuristic {

    @Override
    public float estimate(Map<Variable, Object> state) {
        float ans = 0;
        for (Variable variable : state.keySet()) {
            Object value = state.get(variable);

            if(value.equals(false)) ans++;
        }

        return ans;
    }

}
