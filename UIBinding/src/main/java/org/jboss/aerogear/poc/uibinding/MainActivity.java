package org.jboss.aerogear.poc.uibinding;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jboss.aerogear.poc.uibinding.annotations.Alias;
import org.jboss.aerogear.poc.uibinding.annotations.Bindable;
import org.jboss.aerogear.poc.uibinding.annotations.Model;

import java.lang.reflect.Field;

public class MainActivity extends AeroGearActivity {

    @Model
    private final Pojo testPojo = new Pojo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        testPojo.setMessage("2 way binding");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void sendFeedback(View button) {
        String message = testPojo.getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
