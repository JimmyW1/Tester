package com.test.util;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class ReflectUtil {
    public static Object newInstance(String classFullName) {
        Object newObject;

        try {
            Class aClass = Class.forName(classFullName);
            newObject = aClass.newInstance();
            return newObject;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
