package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * Organizations selected to have GitHub Actions enabled in an enterprise.
 *
 * @param totalCount Total number of organizations
 * @param organizations The organizations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-selected-organizations-enabled-github-actions-enterprise/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSelectedOrganizationsEnabledActions(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubOrganizationSimple> organizations) {

    public GitHubSelectedOrganizationsEnabledActions {
        requireNonNull(organizations, "organizations");
    }
}
