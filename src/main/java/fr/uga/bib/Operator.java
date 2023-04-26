package fr.uga.bib;

public enum Operator {
    /**
     * Operator greater than (>).
     */
    GTH,

    /**
     * Operator less than (<).
     */
    LTH,

    /**
     * Operator greater or equal (>=).
     */
    GEQ,

    /**
     * Operator less or equal (<=).
     */
    LEQ,

    /**
     * Operator equal (==).
     */
    EQU,

    /**
     * Operator different (!=).
     */
    DIF;

    /**
     * Compare two values in function of the Operator.
     *
     * @param value     the first value to compare.
     * @param condValue the second value to compare.
     * @return {@code true} if the condition is respected, {@code false} otherwise.
     */
    public <T extends Comparable<T>> boolean test(T value, T condValue) {
        return switch (this) {
            case GTH -> value.compareTo(condValue) > 0;
            case LTH -> value.compareTo(condValue) < 0;
            case GEQ -> value.compareTo(condValue) >= 0;
            case LEQ -> value.compareTo(condValue) <= 0;
            case EQU -> value.compareTo(condValue) == 0;
            case DIF -> value.compareTo(condValue) != 0;
        };
    }
}
