package modelling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Demo {

    public static void main(String args[]) {

        // 1- Variables
        Set<Object> domain1 = new HashSet<>();
        domain1.add(1);
        domain1.add(2);
        domain1.add(3);
        Variable variable1 = new Variable("Variable 1", domain1);

        Set<Object> domain2 = new HashSet<>();
        domain2.add(3);
        domain2.add(4);
        domain2.add(5);
        domain2.add(6);
        Variable variable2 = new Variable("Variable 2", domain2);

        // 2- Contraintes

        // UnaryConstraint
        Set<Object> domain = new HashSet<>();
        domain.add(1);
        domain.add(2);
        domain.add(3);
        domain.add(4);
        domain.add(5);
        domain.add(6);
        UnaryConstraint unaryConstraint = new UnaryConstraint(variable1, domain);

        // DifferenceConstraint
        DifferenceConstraint differenceConstraint = new DifferenceConstraint(variable1, variable2);

        // Implication
        Implication implication = new Implication(variable1, domain1, variable2, domain2);

        // 3- Instantiation

        // Cas où unaryConstraint est satisfaite
        Map<Variable, Object> instantiation = new HashMap<>();
        instantiation.put(variable1, 1);
        boolean result = unaryConstraint.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : true

        // Cas où unaryConstraint est insatisfaite
        instantiation = new HashMap<>();
        instantiation.put(variable1, 7);
        result = unaryConstraint.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : false

        // Cas où differenceConstraint est satisfaite
        instantiation = new HashMap<>();
        instantiation.put(variable1, 1);
        instantiation.put(variable2, 5);
        result = differenceConstraint.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : true

        // Cas où differenceConstraint est insatisfaite
        instantiation = new HashMap<>();
        instantiation.put(variable1, 3);
        instantiation.put(variable2, 3);
        result = differenceConstraint.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : false

        // Cas où implication est satisfaite
        instantiation = new HashMap<>();
        instantiation.put(variable1, 3);
        instantiation.put(variable2, 5);
        implication.isSatisfiedBy(instantiation);
        result = implication.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : true

        // Cas où implication est insatisfaite
        instantiation = new HashMap<>();
        instantiation.put(variable1, 3);
        instantiation.put(variable2, 1);
        implication.isSatisfiedBy(instantiation);
        result = implication.isSatisfiedBy(instantiation);
        System.out.println(result); // Attendu : false

    }
}
