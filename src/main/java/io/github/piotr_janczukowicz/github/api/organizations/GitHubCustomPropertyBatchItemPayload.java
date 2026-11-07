package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Payload for a single custom property definition when creating or updating custom properties in a batch.
 *
 * @param propertyName The name of the property
 * @param valueType The type of the value for the property
 * @param required Whether the property is required
 * @param defaultValue Default value of the property
 * @param description Short description of the property
 * @param allowedValues An ordered list of the allowed values of the property; the property can have up to 200 allowed
 *     values
 * @param valuesEditableBy Who can edit the values of the property
 * @param regex Regular expression (RE2 syntax) that values of the property must match; only supported for string
 *     properties
 * @param requireExplicitValues Whether setting property values is mandatory
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/custom-property-batch-item-payload")
public record GitHubCustomPropertyBatchItemPayload(
        String propertyName,
        GitHubCustomPropertyValueType valueType,
        @Nullable Boolean required,
        @Nullable @CatalogSimpleXor("string|array") OptionalNull<List<String>> defaultValue,
        @Nullable OptionalNull<String> description,
        @Nullable OptionalNull<List<String>> allowedValues,
        @Nullable OptionalNull<GitHubCustomPropertyValuesEditableBy> valuesEditableBy,
        @Nullable OptionalNull<String> regex,
        @Nullable Boolean requireExplicitValues) {

    private static final String PARAM_PROPERTY_NAME = "propertyName";
    private static final String PARAM_VALUE_TYPE = "valueType";

    public GitHubCustomPropertyBatchItemPayload {
        requireNonNull(propertyName, PARAM_PROPERTY_NAME);
        requireNonNull(valueType, PARAM_VALUE_TYPE);
        if (defaultValue != null && defaultValue.value() != null) {
            defaultValue = OptionalNull.of(List.copyOf(defaultValue.value()));
        }
        if (allowedValues != null && allowedValues.value() != null) {
            allowedValues = OptionalNull.of(List.copyOf(allowedValues.value()));
        }
    }
}
