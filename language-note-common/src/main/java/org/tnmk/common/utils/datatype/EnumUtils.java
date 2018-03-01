package org.tnmk.common.utils.datatype;

import org.springframework.beans.BeanUtils;
import org.tnmk.common.exception.BadArgumentException;
import org.tnmk.common.exception.UnexpectedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author khoi.tran on 8/31/16.
 */
public final class EnumUtils {
    private EnumUtils(){
        //Utils
    }
    public static <T extends Enum<T>> T validateExistEnum(Class<T> enumClass, String enumName) {
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (IllegalArgumentException ex) {
            String msg = String.format("Not found enum instance. enumClass: %s, enumName: %s", enumClass, enumName);
            throw new BadArgumentException(msg, ex);
        }
    }

    public static <T extends Enum<T>> T validateExistEnum(Class<T> enumClass, String enumFieldName, String enumFieldValue) {
        try {
            T enumObject = deserializeEnumValue(enumClass, enumFieldName, enumFieldValue);
            if (enumObject == null) {
                String msg = String.format("Not found enum instance. enumClass: %s, enumFieldName: %s, enumFieldValue: %s", enumClass, enumFieldName, enumFieldValue);
                throw new BadArgumentException(msg);
            }
            return enumObject;
        } catch (IllegalArgumentException ex) {
            String msg = String.format("Not found enum instance. enumClass: %s, enumFieldName: %s, enumFieldValue: %s", enumClass, enumFieldName, enumFieldValue);
            throw new BadArgumentException(msg, ex);
        }
    }

    /**
     * @param enumClass for example: AnimalType
     * @param enumFieldName for example: 'usualName'
     * @param enumFieldValue for example: "dog"
     * @param <T>
     * @return AnimalType.DOG whose AnimalType.DOG.usualName is "dog".
     *
     */
    public static <T extends Enum<T>> T deserializeEnumValue(Class<T> enumClass, String enumFieldName, String enumFieldValue) {
        Method fieldGetter = BeanUtils.getPropertyDescriptor(enumClass, enumFieldName).getReadMethod();
        return deserializeEnumValue(enumClass, fieldGetter, enumFieldValue);
    }

    /**
     * @param enumClass for example: AnimalType
     * @param enumFieldGetter for example: the method getUsualName()
     * @param enumFieldValue for example: "dog"
     * @param <T>
     * @return AnimalType.DOG whose AnimalType.DOG.getUsualName() is "dog".
     */
    public static <T extends Enum<T>> T deserializeEnumValue(Class<T> enumClass, Method enumFieldGetter, String enumFieldValue) {
        if (enumFieldValue == null) {
            return null;
        }
        try {
            for (T ienum : enumClass.getEnumConstants()) {
                Object fieldValue = enumFieldGetter.invoke(ienum);
                String fieldStringValue = StringUtils.toString(fieldValue);
                if (enumFieldValue.trim().equalsIgnoreCase(fieldStringValue)) {
                    return ienum;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedException("Cannot get value from " + enumClass + "." + enumFieldGetter.getName() + "()", e);
        }
        return null;
    }
}
