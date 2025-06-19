package modelling;

import java.util.HashSet;

public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, new HashSet<Object>() {
            {
                add(true);
                add(false);
            }
        });
    }

    public BooleanVariable(String name, Boolean value){
        super(name, new HashSet<Object>() {
            {
                add(value);
            }
        });
    }

    @Override
    public String toString(){
        String representation = this.getName() + ": {";
        for (Object value : this.getDomain()) {
            representation += value.toString()+", ";
        }

        representation = representation.substring(0, representation.length() - 2);

        representation += "}";

        return representation;
    }

}