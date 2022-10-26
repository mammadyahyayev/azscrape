package az.my.datareport.converter;

import az.my.datareport.parser.Assert;

public class StringToEnumConverter {

    /**
     * @param str      given source that need to match any Constant name of the given Enum type
     * @param enumType target type
     * @param <E>      required enum type
     * @return Enum type that matches the source
     * @throws ConversionFailedException, if given source doesn't match the given enum class type
     */
    public static <E extends Enum<E>> E convert(String str, Class<E> enumType) {
        Assert.required(str, "String str value required for the conversion");
        Assert.required(enumType, "Enum type required for the conversion");

        E[] enumConstants = enumType.getEnumConstants();
        for (E enumConstant : enumConstants) {
            if (enumConstant.name().equals(str.toUpperCase())) {
                return enumConstant;
            }
        }

        throw new ConversionFailedException("Can't convert from " + str + " to " + enumType.getName());
    }
}
