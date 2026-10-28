package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom property definition of an organization of an enterprise to create or update.
 *
 * @param valueType The type of value the property holds
 * @param required Whether the property is required
 * @param defaultValue The default value of the property
 * @param description A short description of the property
 * @param allowedValues The allowed values of a select property
 * @param valuesEditableBy Who can edit the values of the property
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-custom-property-payload")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubOrganizationCustomPropertyPayload(
        GitHubOrganizationCustomPropertyPayloadValueType valueType,
        @Nullable Boolean required,
        @Nullable @CatalogSimpleXor("string|array") List<String> defaultValue,
        @Nullable String description,
        @Nullable List<String> allowedValues,
        @Nullable GitHubOrganizationCustomPropertyPayloadValuesEditableBy valuesEditableBy) {

    public GitHubOrganizationCustomPropertyPayload {
        requireNonNull(valueType, "valueType");
        if (defaultValue != null) {
            defaultValue = List.copyOf(defaultValue);
        }
        if (allowedValues != null) {
            allowedValues = List.copyOf(allowedValues);
        }
    }
}
