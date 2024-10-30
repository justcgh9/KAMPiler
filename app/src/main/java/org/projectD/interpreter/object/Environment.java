package org.projectD.interpreter.object;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    Map<String, Object> store;
    Environment outer;

    public Environment() {
        this.store = new HashMap<>();
        this.outer = null;
    }

    public Environment(Environment outer) {
        this.store = new HashMap<>();
        this.outer = outer;
    }

    Object get(String name) {
        Object obj = this.store.get(name);
        if (obj == null && outer != null) {
            outer.get(name);
        }
        return obj;
    }

    Object set(String name, Object val) {
        this.store.put(name, val);
        return val;
    }
}
