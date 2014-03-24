package org.jboss.aerogear.poc.uibinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.dexmaker.stock.ProxyBuilder;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author Erik Jan de Wit <edewit@redhat.com>
 */
public class DataBinder<T> {
    private final BindableProxy bindableProxy;
    private final T model;
    private final Class<?> modelClass;

    @SuppressWarnings("unchecked")
    private DataBinder(T model) {
        bindableProxy = new BindableProxy();
        try {
            ProxyBuilder<?> proxyBuilder = ProxyBuilder.forClass(model.getClass());
            modelClass = model.getClass();
            this.model = (T) proxyBuilder.handler(bindableProxy).build();
        } catch (IOException e) {
            throw new RuntimeException("could not proxy model class", e);
        }
    }

    public DataBinder<T> bind(TextView textView, String property) {
        try {
            Field field = modelClass.getDeclaredField(property);
            field.setAccessible(true);
            bindableProxy.addField(field.getName(), textView);

            textView.addTextChangedListener(new FieldChangedListener(field));
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Model " + model + " does not have a field named " + property);
        }
        return this;
    }

    public static <T> DataBinder<T> forModel(T model) {
        return new DataBinder<T>(model);
    }

    public T getModel() {
        return model;
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
