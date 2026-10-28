package io.github.piotr_janczukowicz.github.api;

import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The flavors whose OpenAPI description carries the annotated type or API method. Absent means every flavor. The values
 * are generated from the official OpenAPI descriptions, so they are never edited by hand: a type or method that exists
 * in one flavor only gets this annotation when the matrix lists it as such.
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Flavors {

    /**
     * Flavors that carry the annotated element.
     *
     * @return at least one flavor
     */
    GitHubFlavor[] value();
}
