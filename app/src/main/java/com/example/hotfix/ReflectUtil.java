package com.example.hotfix;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @author zhangjun
 * @date 2019-9-26
 */
public class ReflectUtil {

    /**
     * 两个数组合并
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    public static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    public static Object getPathList(Object baseDexClassLoader) throws Exception {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    public static Object getDexElements(Object obj) throws Exception {
        return getField(obj,obj.getClass(),"dexElements");
    }

    /**
     * 通过反射获得对应类
     * @param obj Object类对象
     * @param cl class对象
     * @param field 获得类的字符串名称
     * @return
     */
    public static Object getField(Object obj, Class<?> cl, String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        LogUtil.log(cl.getName()+" getField  "+localField.getName());
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 通过反射修改值
     * @param obj 待修改值
     * @param cl  class对象
     * @param field  待修改值的字符串名称
     * @param value  修改值
     */
    public static void setField(Object obj,Class<?> cl, String field, Object value) throws Exception {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj,value);
    }
}
