package ru.smiling.devilll.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class LoggingInvocationHandler implements InvocationHandler {
    private final Object instance;
    private final List<Method> loggableMethods;

    public LoggingInvocationHandler(Object inst) {
        this.instance = inst;
            this.loggableMethods =
                    Arrays.stream(inst.getClass().getMethods())
                            .filter(m -> m.isAnnotationPresent(Log.class))
                            .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method originalMethod = instance.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (loggableMethods.contains(originalMethod)) {
            System.out.println("executed method:" + method + " with args: " + args[0]);
        }
        return method.invoke(instance, args);
    }
}
