package fr.uga.bib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class TypeTest {

    @Test
    public void convert() throws TypeException {
        assertNull("", Type.convert(Boolean.class, null));

        assertEquals("", true, Type.convert(Boolean.class, "true"));
        assertEquals("", Byte.valueOf((byte) 1), Type.convert(Byte.class, '1'));
        assertEquals("",
                Short.valueOf((short) 1),
                Type.convert(Short.class, 1.0)
        );
        assertEquals("", Integer.valueOf(1), Type.convert(Integer.class, 1.0f));
        assertEquals("", Long.valueOf(1L), Type.convert(Long.class, 1L));
        assertEquals("", Float.valueOf(1.0f), Type.convert(Float.class, 1));
        assertEquals("",
                Double.valueOf(1.0),
                Type.convert(Double.class, (short) 1)
        );
        assertEquals("",
                Character.valueOf('1'),
                Type.convert(Character.class, true)
        );
        assertEquals("", "1", Type.convert(String.class, (byte) 1));
    }

    @Test
    public void convertBoolean() throws TypeException {
        assertNull("", Type.convertBoolean(Byte.class, null));

        assertEquals("", true, Type.convertBoolean(Boolean.class, true));
        assertEquals("",
                Byte.valueOf((byte) 1),
                Type.convertBoolean(Byte.class, true)
        );
        assertEquals("",
                Short.valueOf((short) 1),
                Type.convertBoolean(Short.class, true)
        );
        assertEquals("",
                Integer.valueOf(1),
                Type.convertBoolean(Integer.class, true)
        );
        assertEquals("",
                Long.valueOf(1L),
                Type.convertBoolean(Long.class, true)
        );
        assertEquals("",
                Float.valueOf(1.0f),
                Type.convertBoolean(Float.class, true)
        );
        assertEquals("",
                Double.valueOf(1.0),
                Type.convertBoolean(Double.class, true)
        );
        assertEquals("",
                Character.valueOf('1'),
                Type.convertBoolean(Character.class, true)
        );
        assertEquals("", "true", Type.convertBoolean(String.class, true));

        assertEquals("", false, Type.convertBoolean(Boolean.class, false));
        assertEquals("",
                Byte.valueOf((byte) 0),
                Type.convertBoolean(Byte.class, false)
        );
        assertEquals("",
                Short.valueOf((short) 0),
                Type.convertBoolean(Short.class, false)
        );
        assertEquals("",
                Integer.valueOf(0),
                Type.convertBoolean(Integer.class, false)
        );
        assertEquals("",
                Long.valueOf(0L),
                Type.convertBoolean(Long.class, false)
        );
        assertEquals("",
                Float.valueOf(0.0f),
                Type.convertBoolean(Float.class, false)
        );
        assertEquals("",
                Double.valueOf(0.0),
                Type.convertBoolean(Double.class, false)
        );
        assertEquals("",
                Character.valueOf('0'),
                Type.convertBoolean(Character.class, false)
        );
        assertEquals("", "false", Type.convertBoolean(String.class, false));

        assertThrows("",
                TypeException.class,
                () -> Type.convertBoolean(Type.class, true)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertBoolean(DataFrame.class, false)
        );
    }

    @Test
    public void convertByte() throws TypeException {
        assertNull("", Type.convertNumber(Short.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, (byte) 1));
        assertEquals("", false, Type.convertNumber(Boolean.class, (byte) 5));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, (byte) 66)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, (byte) 100)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, (byte) -27)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, (byte) -128)
        );
        assertEquals("",
                Float.valueOf(98.0f),
                Type.convertNumber(Float.class, (byte) 98)
        );
        assertEquals("",
                Double.valueOf(32.0),
                Type.convertNumber(Double.class, (byte) 32)
        );
        assertEquals("", "127", Type.convertNumber(String.class, (byte) 127));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, (byte) 42)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, (byte) 100)
        );
    }

    @Test
    public void convertShort() throws TypeException {
        assertNull("", Type.convertNumber(Integer.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, (short) 1));
        assertEquals("", false, Type.convertNumber(Boolean.class, (short) 55));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, (short) 66)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, (short) 100)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, (short) -27)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, (short) -128)
        );
        assertEquals("",
                Float.valueOf(98.0f),
                Type.convertNumber(Float.class, (short) 98)
        );
        assertEquals("",
                Double.valueOf(32.0),
                Type.convertNumber(Double.class, (short) 32)
        );
        assertEquals("", "127", Type.convertNumber(String.class, (short) 127));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, (short) 42)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, (short) 100)
        );
    }

    @Test
    public void convertInteger() throws TypeException {
        assertNull("", Type.convertNumber(Long.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, 1));
        assertEquals("", false, Type.convertNumber(Boolean.class, 55));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, 66)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, 100)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, -27)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, -128)
        );
        assertEquals("",
                Float.valueOf(98.0f),
                Type.convertNumber(Float.class, 98)
        );
        assertEquals("",
                Double.valueOf(32.0),
                Type.convertNumber(Double.class, 32)
        );
        assertEquals("", "127", Type.convertNumber(String.class, 127));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, 42)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, 100)
        );
    }

    @Test
    public void convertLong() throws TypeException {
        assertNull("", Type.convertNumber(Float.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, 1L));
        assertEquals("", false, Type.convertNumber(Boolean.class, 55L));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, 66L)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, 100L)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, -27L)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, -128L)
        );
        assertEquals("",
                Float.valueOf(98.0f),
                Type.convertNumber(Float.class, 98L)
        );
        assertEquals("",
                Double.valueOf(32.0),
                Type.convertNumber(Double.class, 32L)
        );
        assertEquals("", "127", Type.convertNumber(String.class, 127L));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, 42L)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, 100L)
        );
    }

    @Test
    public void convertFloat() throws TypeException {
        assertNull("", Type.convertNumber(Double.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, 1.0f));
        assertEquals("", false, Type.convertNumber(Boolean.class, 55.5f));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, 66.6f)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, 100.7f)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, -27.55f)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, -128.99f)
        );
        assertEquals("",
                Float.valueOf(98.99f),
                Type.convertNumber(Float.class, 98.99f)
        );
        assertEquals("",
                Double.valueOf(32.23455810546875),
                Type.convertNumber(Double.class, 32.23456f)
        );
        assertEquals("", "127.89", Type.convertNumber(String.class, 127.89f));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, 42.0f)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, 100.21f)
        );
    }

    @Test
    public void convertDouble() throws TypeException {
        assertNull("", Type.convertNumber(Character.class, null));

        assertEquals("", true, Type.convertNumber(Boolean.class, 1.0));
        assertEquals("", false, Type.convertNumber(Boolean.class, 55.5));
        assertEquals("",
                Byte.valueOf((byte) 66),
                Type.convertNumber(Byte.class, 66.6)
        );
        assertEquals("",
                Short.valueOf((short) 100),
                Type.convertNumber(Short.class, 100.7)
        );
        assertEquals("",
                Integer.valueOf(-27),
                Type.convertNumber(Integer.class, -27.55)
        );
        assertEquals("",
                Long.valueOf(-128L),
                Type.convertNumber(Long.class, -128.99)
        );
        assertEquals("",
                Float.valueOf(98.99f),
                Type.convertNumber(Float.class, 98.99)
        );
        assertEquals("",
                Double.valueOf(32.23789),
                Type.convertNumber(Double.class, 32.23789)
        );
        assertEquals("", "127.89", Type.convertNumber(String.class, 127.89));

        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Type.class, 42.0)
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertNumber(Character.class, 100.21)
        );
    }

    @Test
    public void convertCharacter() throws TypeException {
        assertNull("", Type.convertCharacter(String.class, null));

        assertEquals("", true, Type.convertCharacter(Boolean.class, '1'));
        assertEquals("", false, Type.convertCharacter(Boolean.class, 'a'));
        assertEquals("",
                Byte.valueOf((byte) 0),
                Type.convertCharacter(Byte.class, '0')
        );
        assertEquals("",
                Short.valueOf((short) 1),
                Type.convertCharacter(Short.class, '1')
        );
        assertEquals("",
                Integer.valueOf(2),
                Type.convertCharacter(Integer.class, '2')
        );
        assertEquals("",
                Long.valueOf(3L),
                Type.convertCharacter(Long.class, '3')
        );
        assertEquals("",
                Float.valueOf(4.0f),
                Type.convertCharacter(Float.class, '4')
        );
        assertEquals("",
                Double.valueOf(5.0),
                Type.convertCharacter(Double.class, '5')
        );
        assertEquals("",
                Character.valueOf('a'),
                Type.convertCharacter(Character.class, 'a')
        );
        assertEquals("", "6", Type.convertCharacter(String.class, '6'));

        assertThrows("",
                TypeException.class,
                () -> Type.convertCharacter(Type.class, '7')
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertCharacter(Integer.class, '?')
        );
    }

    @Test
    public void convertString() throws TypeException {
        assertNull("", Type.convertString(null, null));

        assertEquals("", true, Type.convertString(Boolean.class, "true"));
        assertEquals("", false, Type.convertString(Boolean.class, "test"));
        assertEquals("",
                Byte.valueOf((byte) 45),
                Type.convertString(Byte.class, "45")
        );
        assertEquals("",
                Short.valueOf((short) 753),
                Type.convertString(Short.class, "753")
        );
        assertEquals("",
                Integer.valueOf(-945135),
                Type.convertString(Integer.class, "-945135")
        );
        assertEquals("",
                Long.valueOf(9876543210L),
                Type.convertString(Long.class, "9876543210")
        );
        assertEquals("",
                Float.valueOf(-89.09f),
                Type.convertString(Float.class, "-89.09")
        );
        assertEquals("",
                Double.valueOf(98765.4321),
                Type.convertString(Double.class, "98765.4321")
        );
        assertEquals("",
                Character.valueOf('w'),
                Type.convertString(Character.class, "word")
        );
        assertEquals("",
                "A-string -42.5 ?",
                Type.convertString(String.class, "A-string -42.5 ?")
        );

        assertNull("", Type.convertString(Character.class, null));
        assertNull("", Type.convertString(Character.class, ""));

        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Type.class, "Wrong type")
        );

        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Integer.class, "")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Byte.class, "byte")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Short.class, "short")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Integer.class, "integer")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Long.class, "long")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Float.class, "float")
        );
        assertThrows("",
                TypeException.class,
                () -> Type.convertString(Double.class, "double")
        );
    }

    @Test
    public void checkTypeString() throws TypeException {
        assertEquals("", Boolean.class, Type.checkType("Boolean"));
        assertEquals("", Boolean.class, Type.checkType("Bool"));
        assertEquals("", Byte.class, Type.checkType("Byte"));
        assertEquals("", Short.class, Type.checkType("Short"));
        assertEquals("", Integer.class, Type.checkType("Integer"));
        assertEquals("", Integer.class, Type.checkType("Int"));
        assertEquals("", Long.class, Type.checkType("Long"));
        assertEquals("", Float.class, Type.checkType("Float"));
        assertEquals("", Double.class, Type.checkType("Double"));
        assertEquals("", Character.class, Type.checkType("Character"));
        assertEquals("", Character.class, Type.checkType("Char"));
        assertEquals("", String.class, Type.checkType("String"));

        assertThrows("", TypeException.class, () -> Type.checkType("Type"));
    }

    @Test
    public void CheckTypeClass() throws TypeException {
        assertEquals("", "Boolean", Type.checkType(Boolean.class));
        assertEquals("", "Byte", Type.checkType(Byte.class));
        assertEquals("", "Short", Type.checkType(Short.class));
        assertEquals("", "Integer", Type.checkType(Integer.class));
        assertEquals("", "Long", Type.checkType(Long.class));
        assertEquals("", "Float", Type.checkType(Float.class));
        assertEquals("", "Double", Type.checkType(Double.class));
        assertEquals("", "Character", Type.checkType(Character.class));
        assertEquals("", "String", Type.checkType(String.class));

        assertThrows("", TypeException.class, () -> Type.checkType(Type.class));
    }
}
