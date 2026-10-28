package io.github.piotr_janczukowicz.github.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The GitHub Enterprise Server releases whose REST description carries the annotated type or API method, for an element
 * that is also annotated {@code @Flavors(GitHubFlavor.GHES)} and does not exist in every supported release. Absent
 * means every supported release. The values are generated from the official OpenAPI descriptions, so they are never
 * edited by hand.
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GhesVersions {

    /**
     * First release that carries the element, written {@code MAJOR.MINOR}.
     *
     * @return the release, or an empty string if the element exists in the oldest supported release
     */
    String since() default "";

    /**
     * Last release that carries the element, written {@code MAJOR.MINOR}.
     *
     * @return the release, or an empty string if the element exists in the newest supported release
     */
    String until() default "";
}
