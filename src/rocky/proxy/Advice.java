/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package rocky.proxy;

import java.lang.reflect.Method;

/**
 * Created by PC on 2014/10/28.
 */
public interface Advice {
    public boolean preAdvice(Object target, Method method, Object[] args);

    public void afterAdvice(Object target, Method method, Object[] args, AdviceResult result);

    public void errorAdvice(Object target, Method method, Object[] args, Throwable e);

    public void finallyAdvice(Object target, Method method, Object[] args);
}
