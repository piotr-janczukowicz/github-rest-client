package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * List organization roles response.
 *
 * @param totalCount The total number of organization roles available to the organization
 * @param roles The list of organization roles available to the organization
 */
@CatalogSchema("operations/orgs/list-org-roles/200")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubOrganizationRolesPage(
        @Nullable Integer totalCount, @Nullable List<GitHubOrganizationRole> roles) {}
