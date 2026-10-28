package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom property defined on an organization.
 *
 * @param propertyName Property name
 * @param url Property API URL
 * @param sourceType Source type
 * @param valueType Value type
 * @param required Whether the property is required
 * @param defaultValue Default value
 * @param description Short description
 * @param allowedValues Allowed values
 * @param valuesEditableBy Who can edit values
 * @param requireExplicitValues Whether setting property values is mandatory
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/custom-property")
public record GitHubCustomProperty(
        String propertyName,
        @Nullable String url,
        @Nullable GitHubCustomPropertySourceType sourceType,
        GitHubCustomPropertyValueType valueType,
        @Nullable Boolean required,
        @Nullable @CatalogSimpleXor("string|array") List<String> defaultValue,
        @Nullable String description,
        @Nullable List<String> allowedValues,
        @Nullable GitHubCustomPropertyValuesEditableBy valuesEditableBy,
        @Nullable Boolean requireExplicitValues) {

    private static final String PARAM_PROPERTY_NAME = "propertyName";
    private static final String PARAM_VALUE_TYPE = "valueType";

    public GitHubCustomProperty {
        requireNonNull(propertyName, PARAM_PROPERTY_NAME);
        requireNonNull(valueType, PARAM_VALUE_TYPE);
        if (defaultValue != null) {
            defaultValue = List.copyOf(defaultValue);
        }
        if (allowedValues != null) {
            allowedValues = List.copyOf(allowedValues);
        }
    }
}
