package com.join.tools.lambda;

import org.springframework.beans.BeanUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyBeanUtils {

    public static <T> void copyProperties(Object source, Object target, MyFunctional<T> ... ignoreProperties){

        String[] ignorePropertieNames=null;

        if(ignoreProperties!=null && ignoreProperties.length>0){
            ignorePropertieNames=new String[ignoreProperties.length];
            for (int i = 0; i < ignoreProperties.length; i++) {
                MyFunctional fn=ignoreProperties[i];
                ignorePropertieNames[i]=getPropertyName(fn);
            }
        }

        BeanUtils.copyProperties(source,target,ignorePropertieNames);
    }

    public static <T> String getPropertyName(MyFunctional<T> fn) {
        try {
            Class lambdaClass=fn.getClass();
            System.out.println("-------------分割线1-----------");
            //打印类名：
            System.out.print("类名：");
            System.out.println(lambdaClass.getName());
            //打印接口名：
            System.out.print("接口名：");
            Arrays.stream(lambdaClass.getInterfaces()).forEach(System.out::print);
            System.out.println();
            //打印方法名：
            System.out.print("方法名：");
            for (Method method : lambdaClass.getDeclaredMethods()) {
                System.out.print(method.getName()+"  ");
            }
            System.out.println();
            System.out.println("-------------分割线2-----------");
            System.out.println();

            Method method = lambdaClass.getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String getterMethod = serializedLambda.getImplMethodName();
            System.out.println("lambda表达式调用的方法名："+getterMethod);
            String fieldName = Introspector.decapitalize(getterMethod.replace("get", ""));
            System.out.println("根据方法名得到的字段名："+fieldName);
            return fieldName;

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
