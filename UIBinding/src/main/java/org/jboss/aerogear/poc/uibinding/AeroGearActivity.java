package org.jboss.aerogear.poc.uibinding;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jboss.aerogear.poc.uibinding.annotations.Alias;
import org.jboss.aerogear.poc.uibinding.annotations.Model;

import java.lang.reflect.Field;

/**
 * Inherited from this activity to enable model binding
 */
public class AeroGearActivity extends Activity {
    private static final String TAG = "AeroGearActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Model.class)) {
                field.setAccessible(true);
                try {
                    Object model = field.get(this);
                    bindModel(model, field);
                    break;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("unable to bind field", e);
                } catch (NoSuchFieldException e) {
                    // ignore
                }
            }
        }
    }

    private void bindModel(Object model, Field modelField) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            final String name;
            if (field.isAnnotationPresent(Alias.class)) {
                name = field.getAnnotation(Alias.class).value();
            } else {
                name = field.getName();
            }

            int id = R.id.class.getDeclaredField(name).getInt(null);
            View view = findViewById(id);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                Object proxy = DataBinder.forModel(model).bind(textView, field.getName()).getModel();
                modelField.set(this, proxy);
            } else {
                Log.i(TAG, "could not bind to component because it's not of type TextView");
            }
        }
    }
}
