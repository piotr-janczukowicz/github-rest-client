package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom property defined for an enterprise or an organization.
 *
 * @param propertyName The name of the property
 * @param url The URL of the property
 * @param sourceType The source of the property
 * @param valueType The type of value the property holds
 * @param required Whether the property is required
 * @param defaultValue The default value of the property
 * @param description A short description of the property
 * @param allowedValues The allowed values of a select property
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/custom-property-base")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCustomPropertyBase(
        @Nullable String propertyName,
        @Nullable String url,
        @Nullable GitHubCustomPropertyBaseSourceType sourceType,
        @Nullable GitHubCustomPropertyBaseValueType valueType,
        @Nullable Boolean required,
        @Nullable @CatalogSimpleXor("string|array") List<String> defaultValue,
        @Nullable String description,
        @Nullable List<String> allowedValues) {}
