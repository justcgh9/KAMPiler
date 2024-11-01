package org.projectD.interpreter.object;

import java.util.HashMap;
import java.util.Map;
import org.projectD.interpreter.object.ObjectTypeDemo.Object;

public class Environment {
    Map<String, Object> store;
    Environment outer;
    boolean isFunctional;

    public Environment() {
        this.store = new HashMap<>();
        this.outer = null;
    }

    public Environment(Environment outer) {
        this.store = new HashMap<>();
        this.outer = outer;
        this.isFunctional = false;
    }

    public Environment(Environment outer, boolean isFunctional) {
        this.store = new HashMap<>();
        this.outer = outer;
        this.isFunctional = isFunctional;
    }

    public Object get(String name) {
        Object obj = this.store.get(name);
        if (obj == null && outer != null) {
            obj = outer.get(name);
        }
        return obj;
    }

    public Object set(String name, Object val) {
        this.store.put(name, val);
        return val;
    }

    public boolean isFunctional() {
        return this.isFunctional;
    }

    public Environment parent() {
        return this.outer;
    }
}
