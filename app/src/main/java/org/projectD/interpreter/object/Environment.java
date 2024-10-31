package org.projectD.interpreter.object;

import java.util.HashMap;
import java.util.Map;
import org.projectD.interpreter.object.ObjectTypeDemo.Object;

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
}
