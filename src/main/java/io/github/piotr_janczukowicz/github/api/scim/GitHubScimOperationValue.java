package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Value of a SCIM user operation: exactly one of a JSON string, a JSON object, or an array of JSON objects.
 *
 * @param text string value
 * @param object object value, with no property constraints in the spec
 * @param array array of object values
 */
@CatalogSchema("components/schemas/scim-user/properties/operations/items/properties/value")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimOperationValue(
        @Nullable String text,
        @Nullable Map<String, Object> object,
        @Nullable List<Map<String, Object>> array) {

    private static final String VALUE_PARAMETER = "value";
    private static final int EXACTLY_ONE_BRANCH = 1;

    public GitHubScimOperationValue {
        final int branches = (text != null ? 1 : 0) + (object != null ? 1 : 0) + (array != null ? 1 : 0);
        if (branches != EXACTLY_ONE_BRANCH) {
            throw new IllegalArgumentException("exactly one of text, object, or array is required");
        }
    }

    /**
     * String value.
     *
     * @param value string value
     * @return value holding a string
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubScimOperationValue text(final String value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubScimOperationValue(value, null, null);
    }

    /**
     * Object value.
     *
     * @param value object value
     * @return value holding an object
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubScimOperationValue object(final Map<String, Object> value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubScimOperationValue(null, value, null);
    }

    /**
     * Array value.
     *
     * @param value array of object values
     * @return value holding an array
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubScimOperationValue array(final List<Map<String, Object>> value) {
        requireNonNull(value, VALUE_PARAMETER);
        return new GitHubScimOperationValue(null, null, value);
    }

    /**
     * Wire JSON value for Jackson and docs schema alignment.
     *
     * @return string, object, or array branch value
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
