package org.projectD.interpreter.object;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.projectD.interpreter.ast.Ast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ObjectTypeDemo {
    public static interface Object {
        ObjectType getType();
        String inspect();
    }

    public enum ObjectType {
        EMPTY_OBJ, ERROR_OBJ, INTEGER_OBJ, DOUBLE_OBJ, BOOLEAN_OBJ, STRING_OBJ, RETURN_VALUE_OBJ, FUNCTION_OBJ, ARRAY_OBJ, TUPLE_OBJ
    }

    public static class HashKey {
        ObjectType type;
        Number value;

        public HashKey(ObjectType type, Number value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof HashKey)) {return false;}
            var temp = (HashKey) obj;
            return temp.type == this.type && temp.value.equals(this.value);
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
    }

    public static interface Hashable {
        HashKey hashKey();
    }

    public static class Integer implements Object, Hashable {
        private final long value;

        public Integer(long value) {
            this.value = value;
        }

        public long getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public ObjectType getType() {
            return ObjectType.INTEGER_OBJ;
        }

        @Override
        public String inspect() {
            return String.valueOf(value);
        }

        @Override
        public HashKey hashKey() {
            return new HashKey(getType(), value);
        }
    }

    public static class Double implements Object, Hashable {
        private final double value;

        public Double(double value) {
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public ObjectType getType() {
            return ObjectType.DOUBLE_OBJ;
        }

        @Override
        public String inspect() {
            return String.valueOf(value);
        }

        @Override
        public HashKey hashKey() {
            return new HashKey(getType(), value);
        }
    }

    public static class Boolean implements Object, Hashable {
        private final boolean value;

        public Boolean(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public ObjectType getType() {
            return ObjectType.BOOLEAN_OBJ;
        }

        @Override
        public String inspect() {
            return String.valueOf(value);
        }

        @Override
        public HashKey hashKey() {
            return new HashKey(getType(), value ? 1 : 0);
        }
    }

    public static class Null implements Object {
        @Override
        public ObjectType getType() {
            return ObjectType.EMPTY_OBJ;
        }

        @Override
        public String toString() {
            return "null";
        }

        @Override
        public String inspect() {
            return "null";
        }
    }

    public static class ReturnValue implements Object {
        private final Object value;

        public ReturnValue(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return "return" + value.toString();
        }

        @Override
        public ObjectType getType() {
            return ObjectType.RETURN_VALUE_OBJ;
        }

        @Override
        public String inspect() {
            return value.inspect();
        }
    }

    public static class Error implements Object {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String toString() {
            return "err: " + message;
        }

        @Override
        public ObjectType getType() {
            return ObjectType.ERROR_OBJ;
        }

        @Override
        public String inspect() {
            return "ERROR: " + message;
        }
    }

    public static class FunctionObject implements Object {
        private final List<Ast.Identifier> parameters;
        private final Ast.BlockStatement body;
        private final Environment env;

        public FunctionObject(List<Ast.Identifier> parameters, Ast.BlockStatement body, Environment env) {
            this.parameters = parameters;
            this.body = body;
            this.env = env;
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();

            List<String> params = new ArrayList<>();
            for (var p : this.parameters) {
                params.add(p.toString());
            }

            out.append("func");
            out.append("(");

            StringJoiner joiner = new StringJoiner(", ");
            for (String param : params) {
                joiner.add(param);
            }

            out.append(joiner.toString());
            out.append(") ");
            out.append(body.toString());

            return out.toString();
        }

        public Ast.BlockStatement getBody() {
            return this.body;
        }

        public List<Ast.Identifier> getParameters() {
            return this.parameters;
        }

        public Environment getEnvironment() {
            return this.env;
        }

        @Override
        public ObjectType getType() {
            return ObjectType.FUNCTION_OBJ;
        }

        @Override
        public String inspect() {
            String params = parameters.stream()
                .map(Ast.Identifier::toString)
                .collect(Collectors.joining(", "));
            return "fn(" + params + ") {\n" + body + "\n}";
        }
    }

    public static class StringObject implements Object, Hashable {
        private final String value;

        public StringObject(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return this.value;
        }

        @Override
        public ObjectType getType() {
            return ObjectType.STRING_OBJ;
        }

        @Override
        public String inspect() {
            return value;
        }

        @Override
        public HashKey hashKey() {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
                long hashValue = Arrays.hashCode(hashBytes);
                return new HashKey(getType(), hashValue);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Hashing error", e);
            }
        }
    }

    public static class ArrayObject implements Object {
        private final List<Object> elements;

        public ArrayObject(List<Object> elements) {
            this.elements = elements;
        }

        public List<Object> getValue() {
            return this.elements;
        }

        @Override
        public String toString() {
            return this.inspect();
        }

        @Override
        public ObjectType getType() {
            return ObjectType.ARRAY_OBJ;
        }

        @Override
        public String inspect() {
            return elements.stream()
                .map(Object::inspect)
                .collect(Collectors.joining(", ", "[", "]"));
        }
    }

    public static class HashPair {
        public final Object key;
        public final Object value;

        public HashPair(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getKey() {
            return this.key;
        }
    }

    public static class TupleObject implements Object {
        private final Map<HashKey, HashPair> pairs;

        public TupleObject(Map<HashKey, HashPair> pairs) {
            this.pairs = pairs;
        }

        public Map<HashKey, HashPair> getPairs() {
            return this.pairs;
        }

        @Override
        public String toString() {
            return this.inspect();
        }

        @Override
        public ObjectType getType() {
            return ObjectType.TUPLE_OBJ;
        }

        @Override
        public String inspect() {
            return pairs.values().stream()
                .map(pair -> pair.key.inspect() + ": " + pair.value.inspect())
                .collect(Collectors.joining(", ", "{", "}"));
        }
    }
}
