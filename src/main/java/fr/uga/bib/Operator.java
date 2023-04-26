package fr.uga.bib;

public enum Operator {
    GTH, LTH, GEQ, LEQ, EQ, DIF;

    public <T extends Comparable<T>> boolean test(T value, T condValue) {
        return switch (this) {
            case GTH -> value.compareTo(condValue) > 0;
            case LTH -> value.compareTo(condValue) < 0;
            case GEQ -> value.compareTo(condValue) >= 0;
            case LEQ -> value.compareTo(condValue) <= 0;
            case EQ -> value.compareTo(condValue) == 0;
            case DIF -> value.compareTo(condValue) != 0;
        };
    }
}
