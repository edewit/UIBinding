package org.jboss.aerogear.poc.uibinding;

import android.widget.TextView;

import com.google.dexmaker.stock.ProxyBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
* Created by edewit on 24/3/14.
*/
class BindableProxy implements InvocationHandler {
    private Map<String, TextView> bindings = new HashMap<String, TextView>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.startsWith("set")) {
            String property = getPropertyName(methodName);
            bindings.get(property).setText((String)args[0]);
        }
        return ProxyBuilder.callSuper(proxy, method, args);
    }

    private String getPropertyName(String methodName) {
        String property = methodName.substring("set".length());
        String firstLetter = String.valueOf(property.charAt(0)).toLowerCase();
        property = firstLetter + property.substring(1);
        return property;
    }

    public void addField(String fieldName, TextView textView) {
        bindings.put(fieldName, textView);
    }
}
