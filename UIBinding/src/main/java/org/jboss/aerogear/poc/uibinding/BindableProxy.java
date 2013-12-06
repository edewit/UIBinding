package org.jboss.aerogear.poc.uibinding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 */
public class BindableProxy extends Proxy {

    protected BindableProxy() {
        super(new UpdatingViewInvocationHandler());
    }

    private static class UpdatingViewInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("set")) {

            }
            return null;
        }
    }
}
