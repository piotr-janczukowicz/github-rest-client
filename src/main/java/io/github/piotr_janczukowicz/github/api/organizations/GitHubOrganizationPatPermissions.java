package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Permissions requested or granted for an organization programmatic access grant, categorized by type.
 *
 * @param organization Organization permissions
 * @param repository Repository permissions
 * @param other Other permissions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-programmatic-access-grant/properties/permissions")
public record GitHubOrganizationPatPermissions(
        @Nullable Map<String, String> organization,
        @Nullable Map<String, String> repository,
        @Nullable Map<String, String> other) {}
