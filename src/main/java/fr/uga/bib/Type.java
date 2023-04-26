package fr.uga.bib;

class Type {
    protected final static String unsupportedType = "Unsupported type '%s'";
    protected final static String unableConversion
            = "Unable to convert '%s' to '%s'";

    /**
     * Convert an {@code Object} value to the given type.
     *
     * @param type  the desired return type.
     * @param value the value to convert.
     * @return the converted value.
     * @throws TypeException if the type is not supported or if the value can
     *                       not be converted to the given type.
     */
    public static <T> T convert(Class<T> type, Object value)
            throws TypeException {
        if (value == null) {
            return null;
        }

        String valueTypeName = checkType(value.getClass());
        return switch (valueTypeName.toLowerCase()) {
            case "boolean" -> convertBoolean(type, (Boolean) value);
            case "byte" -> convertNumber(type, (Byte) value);
            case "short" -> convertNumber(type, (Short) value);
            case "integer" -> convertNumber(type, (Integer) value);
            case "long" -> convertNumber(type, (Long) value);
            case "float" -> convertNumber(type, (Float) value);
            case "double" -> convertNumber(type, (Double) value);
            case "character" -> convertCharacter(type, (Character) value);
            case "string" -> convertString(type, (String) value);
            default -> null; // never happen (type already check)
        };
    }

    /**
     * Convert a boolean value to the given type.
     *
     * @param type  the desired return type.
     * @param value the value to convert.
     * @return the converted value.
     * @throws TypeException if the type is not supported.
     */
    public static <T> T convertBoolean(Class<T> type, Boolean value)
            throws TypeException {
        if (value == null) {
            return null;
        }

        String typeName = checkType(type);
        return (T) switch (typeName.toLowerCase()) {
            case "boolean" -> value;
            case "byte" -> value ? (byte) 1 : (byte) 0;
            case "short" -> value ? (short) 1 : (short) 0;
            case "integer" -> value ? 1 : 0;
            case "long" -> value ? 1L : 0L;
            case "float" -> value ? 1.0f : 0.0f;
            case "double" -> value ? 1.0 : 0.0;
            case "character" -> value ? '1' : '0';
            case "string" -> value ? "true" : "false";
            default -> null; // never happen (type already check)
        };
    }

    /**
     * Convert a number to the given type.
     *
     * @param convertType the desired return type.
     * @param value       the value to convert.
     * @return the converted value.
     * @throws TypeException if the type is not supported or if the value can
     *                       not be converted to the given type.
     */
    public static <T, U extends Number> T convertNumber(Class<T> convertType,
                                                        U value
    ) throws TypeException {
        if (value == null) {
            return null;
        }

        String convertTypeName = checkType(convertType);
        return (T) switch (convertTypeName.toLowerCase()) {
            case "boolean" -> Integer.valueOf(value.intValue()).equals(1);
            case "byte" -> value.byteValue();
            case "short" -> value.shortValue();
            case "integer" -> value.intValue();
            case "long" -> value.longValue();
            case "float" -> value.floatValue();
            case "double" -> value.doubleValue();
            case "character" -> {
                String msg = String.format(unableConversion,
                        value,
                        convertTypeName
                );
                throw new TypeException(msg);
            }
            case "string" -> String.valueOf(value);
            default -> null; // never happen (type already check)
        };
    }

    /**
     * Convert a character to the given type.
     *
     * @param type  the desired return type.
     * @param value the value to convert.
     * @return the converted value.
     * @throws TypeException if the type is not supported or if the value can
     *                       not be converted to the given type.
     */
    public static <T> T convertCharacter(Class<T> type, Character value)
            throws TypeException {
        if (value == null) {
            return null;
        }

        String typeName = checkType(type);
        if (typeName.equalsIgnoreCase("boolean") ||
                typeName.equalsIgnoreCase("byte") ||
                typeName.equalsIgnoreCase("short") ||
                typeName.equalsIgnoreCase("integer") ||
                typeName.equalsIgnoreCase("long") ||
                typeName.equalsIgnoreCase("float") ||
                typeName.equalsIgnoreCase("double")) {
            long numValue = Character.getNumericValue(value);
            if (numValue >= 0) {
                return (T) switch (typeName.toLowerCase()) {
                    case "boolean" -> numValue == 1;
                    case "byte" -> (byte) numValue;
                    case "short" -> (short) numValue;
                    case "integer" -> (int) numValue;
                    case "long" -> numValue;
                    case "float" -> (float) numValue;
                    case "double" -> (double) numValue;
                    default -> null; // never happen (type already check)
                };
            }
            String msg = String.format(unableConversion, value, typeName);
            throw new TypeException(msg);
        } else if (typeName.equalsIgnoreCase("character")) {
            //noinspection unchecked
            return (T) value;
        } else if (typeName.equalsIgnoreCase("string")) {
            //noinspection unchecked
            return (T) String.valueOf((char) value);
        }
        return null; // never happen (type already check)
    }

    /**
     * Convert a string to the given type.
     *
     * @param type  the desired return type.
     * @param value the value to convert.
     * @return the converted value.
     * @throws TypeException if the type is not supported or if the value can
     *                       not be converted to the given type.
     */
    public static <T> T convertString(Class<T> type, String value)
            throws TypeException {
        if (value == null) {
            return null;
        }

        String typeName = checkType(type);
        try {
            return (T) switch (typeName.toLowerCase()) {
                case "boolean" -> Boolean.valueOf(value);
                case "byte" -> Byte.valueOf(value);
                case "short" -> Short.valueOf(value);
                case "integer" -> Integer.valueOf(value);
                case "long" -> Long.valueOf(value);
                case "float" -> Float.valueOf(value);
                case "double" -> Double.valueOf(value);
                case "character" -> value.charAt(0);
                case "string" -> value;
                default -> null; // never happen (type already check)
            };
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null; // char case if value is null or empty
        } catch (NumberFormatException e) {
            String msg = String.format(unableConversion, value, typeName);
            throw new TypeException(msg);
        }
    }

    /**
     * Check if the given type is supported.
     *
     * @param typeName the name of the type to check.
     * @return the class of the type if supported.
     * @throws TypeException if the given type is not supported.
     */
    public static Class<?> checkType(String typeName) throws TypeException {
        return switch (typeName.toLowerCase()) {
            case "boolean", "bool" -> Boolean.class;
            case "byte" -> Byte.class;
            case "short" -> Short.class;
            case "integer", "int" -> Integer.class;
            case "long" -> Long.class;
            case "float" -> Float.class;
            case "double" -> Double.class;
            case "character", "char" -> Character.class;
            case "string" -> String.class;
            default -> {
                String msg = String.format(unsupportedType, typeName);
                throw new TypeException(msg);
            }
        };
    }

    /**
     * Check if the given type is supported.
     *
     * @param type the class of the type to check.
     * @return the name of the type if supported.
     * @throws TypeException if the given type is not supported.
     */
    public static String checkType(Class<?> type) throws TypeException {
        String typeName = type.getSimpleName();
        checkType(typeName);
        return typeName;
    }
}
