package org.jboss.aerogear.poc.uibinding.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    public String value();
}
