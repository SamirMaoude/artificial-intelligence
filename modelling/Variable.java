package modelling;

import java.util.Objects;
import java.util.Set;

public class Variable {

    protected String name;
    protected Set<Object> domain;

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Variable)) {
            return false;
        }
        Variable variable = (Variable) object;
        return this.name.equals(variable.getName()) && this.domain.equals(variable.getDomain());

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, domain);

    }

    public Set<Object> getDomain() {
        return this.domain;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

}