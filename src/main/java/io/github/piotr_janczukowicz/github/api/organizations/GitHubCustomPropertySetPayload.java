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
 * Custom property set payload.
 *
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
@CatalogSchema({
    "components/schemas/custom-property-set-payload",
    "operations/orgs/custom-properties-for-repos-create-or-update-organization-definition/requestBody"
})
public record GitHubCustomPropertySetPayload(
        GitHubCustomPropertyValueType valueType,
        @Nullable Boolean required,
        @Nullable @CatalogSimpleXor("string|array") OptionalNull<List<String>> defaultValue,
        @Nullable OptionalNull<String> description,
        @Nullable OptionalNull<List<String>> allowedValues,
        @Nullable OptionalNull<GitHubCustomPropertyValuesEditableBy> valuesEditableBy,
        @Nullable Boolean requireExplicitValues) {

    private static final String PARAM_VALUE_TYPE = "valueType";

    public GitHubCustomPropertySetPayload {
        requireNonNull(valueType, PARAM_VALUE_TYPE);
        if (defaultValue != null && defaultValue.value() != null) {
            defaultValue = OptionalNull.of(List.copyOf(defaultValue.value()));
        }
        if (allowedValues != null && allowedValues.value() != null) {
            allowedValues = OptionalNull.of(List.copyOf(allowedValues.value()));
        }
    }
}
