package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom property name and associated value.
 *
 * @param propertyName The name of the property
 * @param value The value assigned to the property
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/custom-property-value")
public record GitHubCustomPropertyValue(
        String propertyName,

        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable @CatalogSimpleXor("string|array")
        List<String> value) {

    private static final String PARAM_VALUE = "value";

    public GitHubCustomPropertyValue {
        requireNonNull(propertyName, "propertyName");
        if (value != null) {
            value = List.copyOf(value);
        }
    }

    /**
     * Property with a string value.
     *
     * @param propertyName name of the custom property
     * @param value string value
     * @return property value
     */
    public static GitHubCustomPropertyValue of(final String propertyName, final String value) {
        return new GitHubCustomPropertyValue(propertyName, List.of(requireNonNull(value, PARAM_VALUE)));
    }

    /**
     * Property with a string-list value.
     *
     * @param propertyName name of the custom property
     * @param values string values
     * @return property value
     */
    public static GitHubCustomPropertyValue of(final String propertyName, final List<String> values) {
        return new GitHubCustomPropertyValue(propertyName, requireNonNull(values, PARAM_VALUE));
    }

    /**
     * Property with a null value.
     *
     * @param propertyName name of the custom property
     * @return property value
     */
    public static GitHubCustomPropertyValue ofNull(final String propertyName) {
        return new GitHubCustomPropertyValue(propertyName, null);
    }
}
