package org.jboss.aerogear.poc.uibinding;

import org.jboss.aerogear.poc.uibinding.annotations.Alias;
import org.jboss.aerogear.poc.uibinding.annotations.Bindable;

/**
 */
@Bindable
public class Pojo {

    @Alias("edit_message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
