package values;

public enum ValType {
    MIN, MAX, VAL;
    public static final ValType[] ALL = MIN.getDeclaringClass().getEnumConstants();
}
