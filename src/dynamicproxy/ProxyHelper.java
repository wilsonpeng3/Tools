package dynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyHelper {
    /**
     * @param target 拦截的目标对象
     * @param advice 要应用的通知，如果为null，则直接执行目标方法。
     * @param <T>
     * @return
     */
    public static <T> T proxy(T target, Advice advice) {
        if (target == null) {
            throw new IllegalArgumentException("param 'target' can not be null.");
        }
        return proxy(target.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (advice == null) {
                    return method.invoke(target, args);
                }

                AdviceResult tmp = new AdviceResult(null);
                //执行preAdvice，检测是否需要执行目标方法
                if (advice.preAdvice(target, method, args)) {
                    try {
                        Object realResult = method.invoke(target, args);
                        tmp.setRealResult(realResult);
                        advice.afterAdvice(o, method, args, tmp);
                    } catch (Throwable e) {
                        advice.errorAdvice(target, method, args, e);
                    } finally {
                        advice.finallyAdvice(target, method, args);
                    }
                } else {
                    //nothing to do  不执行目标方法，直接返回
                }
                return tmp.getRealResult();
            }
        });
    }

    public static <T> T proxy(Class clazz, MethodInterceptor interceptor) {
        Enhancer en = new Enhancer();
        en.setSuperclass(clazz);
        en.setCallback(interceptor);
        T tt = (T) en.create();
        return tt;
    }
}

