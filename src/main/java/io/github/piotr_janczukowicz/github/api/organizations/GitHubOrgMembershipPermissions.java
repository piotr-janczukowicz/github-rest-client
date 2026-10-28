package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Organization membership permissions.
 *
 * @param canCreateRepository Whether the member can create repositories
 */
@RecordBuilder
@CatalogSchema("components/schemas/org-membership/properties/permissions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubOrgMembershipPermissions(boolean canCreateRepository) {}
