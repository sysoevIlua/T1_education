package org.sysoev.service;

import org.sysoev.annotations.*;
import org.sysoev.exceptions.AnnotationException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {

    private static Method beforeSuite = null;
    private static Method afterSuite = null;
    private static Method beforeTest = null;
    private static Method afterTest = null;
    private static List<Method> testMethods = new ArrayList<>();


    public static void runTests(Class<?> c) {

        checkMethods(c, false);

        try {
            Object instance = c.getDeclaredConstructor().newInstance();

            //Вызов beforeSuite
            if (beforeSuite != null) {
                beforeSuite.invoke(null);
            }

            //Сортировка testMethods
            testMethods.sort(Comparator.comparingInt(
                    method -> method.getAnnotation(Test.class).priority()
            ));

            //Вызов testMethods
            for (Method testMethod : testMethods) {
                if (beforeTest != null) beforeTest.invoke(instance);

                System.out.println("Вызов теста с приоритетом: " + testMethod.getAnnotation(Test.class).priority());
                testMethod.invoke(instance);

                if (afterTest != null) afterTest.invoke(instance);
                System.out.println("============================");

            }

            //Вызов afterSuite
            if (afterSuite != null) {
                afterSuite.invoke(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void checkMethods(Class<?> c, boolean declaredMethods) {
        Method[] a;
        if (declaredMethods)
            a = c.getDeclaredMethods();
        else
            a = c.getMethods();

        for (Method method : a) {

            if (method.isAnnotationPresent(BeforeSuite.class)) {
                validateBeforeSuite(method);
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                validateAfterSuite(method);
            }

            if (method.isAnnotationPresent(Test.class)) {
                validateTest(method);
            }

            if (method.isAnnotationPresent(AfterTest.class)) {
                validateAfterTest(method);
            }

            if (method.isAnnotationPresent(BeforeTest.class)) {
                validateBeforeTest(method);
            }
        }
    }

    private static void validateBeforeSuite(Method method) {
        if (beforeSuite != null) {
            throw new AnnotationException("BeforeSuite указан более одного раза");
        }
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new AnnotationException("Аннотация BeforeSuite указана не над статическим методом");
        }
        beforeSuite = method;
    }

    private static void validateAfterSuite(Method method) {
        if (afterSuite != null) {
            throw new AnnotationException("AfterSuite указан более одного раза");
        }
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new AnnotationException("Аннотация AfterSuite указана не над статическим методом");
        }
        afterSuite = method;
    }

    private static void validateTest(Method method) {
        int priority = method.getAnnotation(Test.class).priority();
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("Значение priority (" + priority + ") находится вне границ [1..10]");
        }
        testMethods.add(method);
    }

    private static void validateAfterTest(Method method) {
        if (afterTest != null) {
            throw new AnnotationException("AfterTest указан более одного раза");
        }
        afterTest = method;
    }

    private static void validateBeforeTest(Method method) {
        if (beforeTest != null) {
            throw new AnnotationException("BefoteTest указан более одного раза");
        }
        beforeTest = method;
    }

}
