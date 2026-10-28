package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom repository roles available in an organization.
 *
 * @param totalCount The total number of custom repository roles
 * @param customRoles The custom repository roles
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/list-custom-repo-roles/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListCustomRepoRolesResponse(
        @Nullable Integer totalCount, @Nullable List<GitHubOrganizationCustomRepositoryRole> customRoles) {}
