package az.my.datareport.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringToEnumConverter {

    public static <E extends Enum<E>> E convert(String str, Class<E> enumType) {
        E[] enumConstants = enumType.getEnumConstants();
        try {
            Method convertMethod = enumType.getDeclaredMethod("convert", String.class);
            for (E enumConstant : enumConstants) {
                Object result = convertMethod.invoke(enumConstant, str);
                if (result != null) {
                    return enumConstant;
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
