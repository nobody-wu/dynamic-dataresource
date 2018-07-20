package datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public class DynamicSwitcherAop {

    private void setDataSource(Class<?> cls, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            if (cls.isAnnotationPresent(DataSource.class)) {
                DataSource source = cls.getAnnotation(DataSource.class);
                DynamicSwitcherUtil.setDataSourceKey(source.value());
            }
            Method m = cls.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DynamicSwitcherUtil.setDataSourceKey(source.value());
            }
        } catch (Exception e) {
        }
    }


    private  void doBefore(JoinPoint joinPoint) {
        Class<?> target = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        for (Class<?> cls : target.getInterfaces()) {
            setDataSource(cls, signature.getMethod());
        }
        setDataSource(target, signature.getMethod());
    }


    private  void doAfter(JoinPoint joinPoint) {
        DynamicSwitcherUtil.removeDataSourceKey();
    }
}
