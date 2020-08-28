package com.join.tools.lambda;

import java.io.Serializable;

@FunctionalInterface
public interface MyFunctional<T> extends Serializable {

    Object apply(T source);

}
