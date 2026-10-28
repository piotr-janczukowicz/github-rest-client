package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Value of an update-attribute operation: exactly one of a JSON string, an object with the named user attributes, or an
 * array of email objects.
 *
 * @param text string value
 * @param object named user attributes to update
 * @param array email objects to set
 */
@CatalogSchema("operations/scim/update-attribute-for-user/requestBody/properties/Operations/items/properties/value")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateAttributeForUserValue(
        @Nullable String text,
        @Nullable GitHubUpdateAttributeForUserValueObject object,
        @Nullable List<GitHubUpdateAttributeForUserValueEmail> array) {

    private static final String VALUE_PARAMETER = "value";
    private static final int EXACTLY_ONE_BRANCH = 1;

    public GitHubUpdateAttributeForUserValue {
        final int branches = (text != null ? 1 : 0) + (object != null ? 1 : 0) + (array != null ? 1 : 0);
        if (branches != EXACTLY_ONE_BRANCH) {
            throw new IllegalArgumentException("exactly one of text, object, or array is required");
        }
        if (array != null) {
            array = List.copyOf(array);
        }
    }

    /**
     * String value.
     *
     * @param value string value
     * @return value holding a string
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubUpdateAttributeForUserValue text(final String value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubUpdateAttributeForUserValue(value, null, null);
    }

    /**
     * Object value.
     *
     * @param value named user attributes
     * @return value holding an object
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubUpdateAttributeForUserValue object(final GitHubUpdateAttributeForUserValueObject value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubUpdateAttributeForUserValue(null, value, null);
    }

    /**
     * Array value.
     *
     * @param value email objects
     * @return value holding an array
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubUpdateAttributeForUserValue array(final List<GitHubUpdateAttributeForUserValueEmail> value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubUpdateAttributeForUserValue(null, null, value);
    }

    /**
     * Wire JSON value for Jackson and docs schema alignment.
     *
     * @return string, named-attribute object, or email array branch value
     */
    @JsonValue
    public Object jsonValue() {
        if (text != null) {
            return text;
        }
        if (object != null) {
            return object;
        }
        return requireNonNull(array, "array");
    }
}
