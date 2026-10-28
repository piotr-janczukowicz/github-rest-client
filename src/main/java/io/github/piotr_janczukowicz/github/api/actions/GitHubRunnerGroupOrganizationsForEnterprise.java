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
 * Organizations with access to a self-hosted runner group of an enterprise.
 *
 * @param totalCount Total number of organizations
 * @param organizations The organizations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-org-access-to-self-hosted-runner-group-in-enterprise/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnerGroupOrganizationsForEnterprise(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubOrganizationSimple> organizations) {

    public GitHubRunnerGroupOrganizationsForEnterprise {
        requireNonNull(organizations, "organizations");
    }
}
