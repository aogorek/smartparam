/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.engine.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.smartparam.engine.core.exception.SmartParamErrorCode;
import org.smartparam.engine.core.exception.SmartParamException;

/**
 *
 * @author Adam Dubiel
 */
public class ReflectionsHelper {

    private ReflectionsHelper() {
    }

    public static Class<?> loadClass(ClassLoader classLoader, String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException exception) {
            throw new SmartParamException(SmartParamErrorCode.REFLECTIVE_OPERATION_ERROR, exception, String.format("Unable to load class %s using %s classloader.", className, classLoader));
        }
    }

    public static <T> T createObject(Class<T> objectClass) {
        return createObject(objectClass, new Class<?>[]{}, new Object[]{});
    }

    public static <T> T createObject(Class<T> objectClass, Class<?>[] constructorArgsClasses, Object[] construtorArgs) {
        try {
            return objectClass.getConstructor(constructorArgsClasses).newInstance(construtorArgs);
        } catch (IllegalAccessException illegalAccessException) {
            throwSmartParamExceptionForObjectConstruction(illegalAccessException, objectClass, construtorArgs);
        } catch (IllegalArgumentException illegalArgumentException) {
            throwSmartParamExceptionForObjectConstruction(illegalArgumentException, objectClass, construtorArgs);
        } catch (InstantiationException instantiationException) {
            throwSmartParamExceptionForObjectConstruction(instantiationException, objectClass, construtorArgs);
        } catch (NoSuchMethodException noSuchMethodException) {
            throwSmartParamExceptionForObjectConstruction(noSuchMethodException, objectClass, construtorArgs);
        } catch (SecurityException securityException) {
            throwSmartParamExceptionForObjectConstruction(securityException, objectClass, construtorArgs);
        } catch (InvocationTargetException invoicationTargetException) {
            throwSmartParamExceptionForObjectConstruction(invoicationTargetException, objectClass, construtorArgs);
        }
        return null;
    }

    private static void throwSmartParamExceptionForObjectConstruction(Exception exception, Class<?> objectClass, Object[] construtorArgs) {
        throw new SmartParamException(SmartParamErrorCode.REFLECTIVE_OPERATION_ERROR, exception,
                String.format("no String[%d] constructor found for class %s", construtorArgs.length, objectClass.getCanonicalName()));
    }

    public static Set<Method> findMethodsAnnotatedWith(Class<? extends Annotation> annotationType, Class<?> parentClass) {
        Set<Method> annotatedMethods = new HashSet<Method>();
        for (Method method : parentClass.getMethods()) {
            if (method.isAnnotationPresent(annotationType)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    public static Set<Field> findFieldsAnnotatedWith(Class<? extends Annotation> annotation, Class<?> parentClass) {
        Set<Field> annotatedFields = new HashSet<Field>();
        for (Field field : parentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }

    public static void runSetter(Method setter, Object setterHostObject, Object argument) {
        try {
            setter.invoke(setterHostObject, argument);
        } catch (IllegalAccessException illegalAccessException) {
            throwSmartParamExceptionForSetterInvocation(illegalAccessException, setter, setterHostObject, argument);
        } catch (IllegalArgumentException illeagalArgumentException) {
            throwSmartParamExceptionForSetterInvocation(illeagalArgumentException, setter, setterHostObject, argument);
        } catch (InvocationTargetException illegalTargetException) {
            throwSmartParamExceptionForSetterInvocation(illegalTargetException, setter, setterHostObject, argument);
        }
    }

    private static void throwSmartParamExceptionForSetterInvocation(Exception exception, Method setter, Object setterHostObject, Object argument) {
        throw new SmartParamException(SmartParamErrorCode.REFLECTIVE_OPERATION_ERROR, exception,
                String.format("Could not invoke setter %s on object %s using %s as argument", setter.getName(), setterHostObject.getClass().getSimpleName(), argument.getClass().getSimpleName()));
    }
}
