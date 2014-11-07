package dynamicproxy;

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
