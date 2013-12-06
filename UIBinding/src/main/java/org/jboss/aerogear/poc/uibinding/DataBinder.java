package org.jboss.aerogear.poc.uibinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.dexmaker.stock.ProxyBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erik Jan de Wit <edewit@redhat.com>
 */
public class DataBinder<T> {
    private Map<String, TextView> bindings = new HashMap<String, TextView>();
    private final T model;

    private DataBinder(T model) {
        this.model = model;
    }

    public DataBinder<T> bind(TextView textView, String property) {
        try {
            Field field = model.getClass().getDeclaredField(property);
            field.setAccessible(true);
            bindings.put(field.getName(), textView);

            textView.addTextChangedListener(new FieldChangedListener(field));
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Model " + model + " does not have a field named " + property);
        }
        return this;
    }

    public static <T> DataBinder<T> forModel(T model) {
        return new DataBinder<T>(model);
    }

    @SuppressWarnings("unchecked")
    public T getModel() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                if (methodName.startsWith("set")) {
                    String property = getPropertyName(methodName);
                    bindings.get(property).setText((String)args[0]);
                }
                return ProxyBuilder.callSuper(proxy, method, args);
            }
        };

        try {
            return (T) ProxyBuilder.forClass(model.getClass()).handler(handler).build();
        } catch (IOException e) {
            throw new RuntimeException("could not proxy model class", e);
        }
    }

    private String getPropertyName(String methodName) {
        String property = methodName.substring("set".length());
        String firstLetter = String.valueOf(property.charAt(0)).toLowerCase();
        property = firstLetter + property.substring(1);
        return property;
    }

    private class FieldChangedListener implements TextWatcher {

        private final Field field;

        public FieldChangedListener(Field field) {
            this.field = field;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                field.set(model, s.toString());
            } catch (IllegalAccessException e) {
                //ignore
            }
        }
    }
}
